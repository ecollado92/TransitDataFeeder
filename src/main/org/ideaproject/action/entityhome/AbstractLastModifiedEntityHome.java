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

import java.util.Date;

import org.ideaproject.model.AbstractLastModifiedEntity;
import org.ideaproject.model.User;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.security.management.JpaIdentityStore;

/**
 * Abstract wrapper for Seam EntityHome which will, upon save/update, update the "Last Modified" date
 * as well as the "Last Modified" user.
 * @author dirk
 * @see org.jboss.seam.framework.EntityHome
 */
public abstract class AbstractLastModifiedEntityHome<T extends AbstractLastModifiedEntity> extends EntityHome<T> {

	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.EntityHome#update()
	 */
	@Override
	public String update() {
		updateInstanceLastModifiedInfo();
		return super.update();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.EntityHome#persist()
	 */
	@Override
	public String persist() {
		updateInstanceLastModifiedInfo();
		return super.persist();
	}

	/**
	 * updates the embedded entity's "Last Modified" date and user
	 */
	private void updateInstanceLastModifiedInfo() {
		User currentUser = (User) Contexts.getSessionContext().get(JpaIdentityStore.AUTHENTICATED_USER);
		getInstance().setDateLastModified(new Date());
		getInstance().setUserLastModified(currentUser);
	}
}
