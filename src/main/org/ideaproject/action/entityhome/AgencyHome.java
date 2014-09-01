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

import org.ideaproject.Constants;
import org.ideaproject.model.Agency;
import org.ideaproject.model.AgencyGroup;
import org.ideaproject.model.User;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Seam EntityHome for Agencies
 * @author dirk
 * @see org.ideaproject.action.entityhome.AbstractLastModifiedEntityHome
 */
@Name("agencyHome")
public class AgencyHome extends AbstractLastModifiedEntityHome<Agency> {
	private static final long serialVersionUID = 1L;

	@In(required=false)
	private User authenticatedUser;

	@In(create = true)
	private AgencyGroupHome agencyGroupHome;

	/**
	 * Sets the current agency  by ID; side effect updates the
	 * instance.
	 * @param id the current agency ID
	 */
	public void setAgencyAgencyId(Integer id) {
		setId(id);
	}

	/**
	 * @return the ID of the current agency.
	 */
	public Integer getAgencyAgencyId() {
		return (Integer) getId();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Agency createInstance() {
		Agency agency = new Agency();
		return agency;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.Home#getInstance()
	 */
	@Override
	public Agency getInstance() {
		Agency result = super.getInstance();
		if (result.getAgencyLatitude() == null) {
			result.setAgencyLatitude(Constants.DEFAULT_AGENCY_LATITUDE);
		}
		if (result.getAgencyLongitude() == null) {
			result.setAgencyLongitude(Constants.DEFAULT_AGENCY_LONGITUDE);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ideaproject.action.entityhome.AbstractLastModifiedEntityHome#persist()
	 */
	@Override
	public String persist() {
		getInstance().getAgencyUsers().add(authenticatedUser);
		String result = super.persist();
		updateAgencyGroups();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ideaproject.action.entityhome.AbstractLastModifiedEntityHome#update()
	 */
	@Override
	public String update() {
		String result = super.update();
		updateAgencyGroups();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.EntityHome#remove()
	 */
	@Override
	public String remove() {
		Integer originalAgencyGroupId = agencyGroupHome.getAgencyGroupAgencyGroupId();
		try {
			for (AgencyGroup agencyGroup : getInstance().getAgencyGroups()) {
				agencyGroupHome.setAgencyGroupAgencyGroupId(agencyGroup.getAgencyGroupId());
				agencyGroupHome.getInstance().getAgencies().remove(getInstance());
				agencyGroupHome.update();
				getEntityManager().refresh(agencyGroup);
			}
			return super.remove();
		} finally {
			agencyGroupHome.setAgencyGroupAgencyGroupId(originalAgencyGroupId);
		}
//		String result = super.remove();
//		try {
//			for (AgencyGroup agencyGroup : getInstance().getAgencyGroups()) {
//				if (!agencyGroup.getAgencies().contains(getInstance())) {
//					agencyGroupHome.setAgencyGroupAgencyGroupId(agencyGroup.getAgencyGroupId());
//					agencyGroupHome.getInstance().getAgencies().remove(getInstance());
//					agencyGroupHome.update();
//				}
//			}
//		} finally {
//			agencyGroupHome.setAgencyGroupAgencyGroupId(originalAgencyGroupId);
//		}
//		return result;
	}

	/**
	 * Loads the current agency group.
	 */
	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	/**
	 * "Wires" the current instance
	 */
	public void wire() {
		getInstance();
	}

	/**
	 * @return whether or not the current agency is "wired"
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
	public Agency getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	private void updateAgencyGroups() {
		Integer originalAgencyGroupId = agencyGroupHome.getAgencyGroupAgencyGroupId();
		try {
			for (AgencyGroup agencyGroup : getInstance().getAgencyGroups()) {
				if (!agencyGroup.getAgencies().contains(getInstance())) {
					agencyGroupHome.setAgencyGroupAgencyGroupId(agencyGroup.getAgencyGroupId());
					agencyGroupHome.getInstance().getAgencies().add(getInstance());
					agencyGroupHome.update();
				}
			}
		} finally {
			agencyGroupHome.setAgencyGroupAgencyGroupId(originalAgencyGroupId);
		}
	}
	

}
