/**
 *  Copyright 2010 SingleMind Consulting, Inc. (http://singlemindconsulting.com)
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. 
 */
package org.ideaproject.action.entityhome;

import java.util.List;

import org.ideaproject.Constants;
import org.ideaproject.action.entityquery.UserList;
import org.ideaproject.model.Agency;
import org.ideaproject.model.User;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.security.RunAsOperation;
import org.jboss.seam.security.management.action.UserAction;

/**
 * Seam EntityHome for Users
 * @author dirk
 * @see org.jboss.seam.framework.EntityHome
 */
@Name("userHome")
public class UserHome extends EntityHome<User> {

	private static final long serialVersionUID = 1L;

	/**
	 * The result to return when a persist/update operation is successful.
	 */
	private static final String SAVE_SUCCESS = "success";

	/**
	 * The result to return when a persist/update operation fails.
	 */
	private static final String SAVE_FAILURE = "failure";

	/**
	 * The Seam-provided UserAction by which we can interact with JPA-based
	 * security mechanisms.
	 */
	@In(create = true)
	private UserAction userAction;

	@In(create = true)
	private AgencyHome agencyHome;

	@In(create = true)
	private UserList userList;

	private boolean userActionEditCalled = false;

	/**
	 * Sets the current user by ID; side effect updates the
	 * instance.
	 * @param id the bike option ID
	 */
	public void setUserUserId(Integer id) {
		setId(id);
	}

	/**
	 * @return the ID of the current user.
	 */
	public Integer getUserUserId() {
		return (Integer) getId();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected User createInstance() {
		User user = new User();
		userAction.createUser();
		return user;
	}

	/**
	 * Loads the current user.
	 */
	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	/**
	 * "Wires" the current instance
	 */
	@Restrict("#{authenticatedUser.userId == userHome.userUserId or s:hasRole('admin')}")
	public void wire() {
		User user = getInstance();
		if (!userActionEditCalled) {
			new EditUserRunAsOperation(user.getEmail()).addRole(Constants.ROLE_ADMIN).run();
			userActionEditCalled = true;
		}
	}

	/**
	 * @return whether or not the current user is "wired"
	 * @see #wire()
	 */
	public boolean isWired() {
		return true;
	}

	/**
	 * Gets the currently defined instance; may return null if the ID for this
	 * EntityHome is not supplied.
	 * @return the currently-defined instance, or null if none is defined.
	 */
	public User getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.EntityHome#persist()
	 */
	@Override
	@Restrict("#{authenticatedUser.userId == userHome.userUserId or s:hasRole('admin')}")
	public String persist() {
		String result = "persisted";
//		String result = super.persist();
		userAction.setFirstname(getInstance().getFirstName());
		userAction.setLastname(getInstance().getLastName());
		userAction.setUsername(getInstance().getEmail());
		userAction.setRoles(getCurrentUserRoles());
		userAction.setEnabled(getInstance().isEnabled());

		SaveUserRunAsOperation op = new SaveUserRunAsOperation();
		op.addRole(Constants.ROLE_ADMIN).run();
		
		if (!SAVE_SUCCESS.equals(op.result)) {
			result = SAVE_FAILURE;
		} else {
			String username = getInstance().getEmail();
			clearInstance();
			setUserUserId(userList.getUserIdForUsername(username));
			createdMessage();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.EntityHome#update()
	 */
	@Override
	@Restrict("#{authenticatedUser.userId == userHome.userUserId or s:hasRole('admin')}")
	public String update() {
		updateAgencyAffiliations();
		String result = "updated";
//		String result = super.update();
		userAction.setFirstname(getInstance().getFirstName());
		userAction.setLastname(getInstance().getLastName());
		userAction.setUsername(getInstance().getEmail());
		userAction.setRoles(getCurrentUserRoles());
		userAction.setEnabled(getInstance().isEnabled());

		SaveUserRunAsOperation op = new SaveUserRunAsOperation();
		op.addRole(Constants.ROLE_ADMIN).run();

		if (!SAVE_SUCCESS.equals(op.result)) {
			result = SAVE_FAILURE;
		} else {
			updatedMessage();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.EntityHome#remove()
	 */
	@Override
	public String remove() {
		for (Agency agency : instance.getAgencyAffiliations()) {
			agency.getAgencyUsers().remove(instance);
			getEntityManager().persist(agency);
		}
		return super.remove();
	}

	/**
	 * @return the un-hashed user password from JPA
	 */
	public String getPassword() {
		return userAction.getPassword();
	}

	/**
	 * Sets the user password; provided un-hashed, and Seam's JPA support
	 * handles the hashing of it prior to insertion into the database.
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		userAction.setPassword(password);
	}

	/**
	 * @return the confirmation of the password; since the password is
	 * hashed in the database, we need this to prevent against typos
	 */
	public String getConfirm() {
		return userAction.getConfirm();
	}

	/**
	 * Sets the password confirmation.
	 * @param confirm the password confirmation to set.
	 */
	public void setConfirm(String confirm) {
		userAction.setConfirm(confirm);
	}

	/**
	 * @return the set of JPA user role titles associated
	 * with the current user.
	 */
	public List<String> getCurrentUserRoles() {
		return userAction.getRoles();
	}

	/**
	 * Set the JPA user roles associated with the current user.
	 * @param currentUserRoles the user roles to set.
	 */
	public void setCurrentUserRoles(List<String> currentUserRoles) {
		userAction.setRoles(currentUserRoles);
	}

	private void updateAgencyAffiliations() {
		Integer originalAgencyId = agencyHome.getAgencyAgencyId();
		try {
			for (Agency agency : getInstance().getAgencyAffiliations()) {
				if (!agency.getAgencyUsers().contains(getInstance())) {
					agencyHome.setAgencyAgencyId(agency.getAgencyId());
					agencyHome.getInstance().getAgencyUsers().add(getInstance());
					agencyHome.update();
				}
			}
		} finally {
			agencyHome.setAgencyAgencyId(originalAgencyId);
		}
	}
	
	/**
	 * @author dirk
	 * 
	 * Because Seam limits save operations for the user editing to only admins,
	 * this class provides access for users to save their own account attributes.
	 */
	private final class SaveUserRunAsOperation extends RunAsOperation {
		private String result;

		/*
		 * (non-Javadoc)
		 * @see org.jboss.seam.security.RunAsOperation#execute()
		 */
		@Override
		public void execute() {
			result = userAction.save();
		}
	}

	/**
	 * @author dirk
	 * 
	 * Because Seam limits edit operations for the user editing to only admins,
	 * this class provides access for users to edit their own account attributes.
	 */
	private final class EditUserRunAsOperation extends RunAsOperation {
		private final String userName;

		/**
		 * Constructor; takes the user name as a parameter.
		 * @param userName
		 */
		private EditUserRunAsOperation(String userName) {
			this.userName = userName;
		}

		/*
		 * (non-Javadoc)
		 * @see org.jboss.seam.security.RunAsOperation#execute()
		 */
		@Override
		public void execute() {
			userAction.editUser(userName);
		}
	}

}
