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

import org.ideaproject.model.AbstractAgencyBasedEntity;
import org.ideaproject.model.Agency;
import org.jboss.seam.annotations.In;

/**
 * Abstract superclass for Seam EntityHomes that are associated with entities
 * which are inherently tied to a particular agency; will set the agency to 
 * the currently-selected agency upon save/update.
 * @author dirk
 * @see org.ideaproject.action.entityhome.AbstractLastModifiedEntityHome
 * @see org.jboss.seam.framework.EntityHome
 */
public abstract class AbstractAgencyBasedEntityHome<T extends AbstractAgencyBasedEntity> extends
		AbstractLastModifiedEntityHome<T> {

	private static final long serialVersionUID = 1L;

	@In(create = true)
	protected AgencyHome agencyHome;

	/**
	 * "Wires" the EntityHome's instance such that the current agency (stored within AgencyHome)
	 * is associated with the instance.
	 */
	public void wire() {
		getInstance();
		Agency agency = agencyHome.getDefinedInstance();
		if (agency != null) {
			getInstance().setAgency(agency);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.ideaproject.action.entityhome.AbstractLastModifiedEntityHome#update()
	 */
	@Override
	public String update() {
		getInstance().setAgency(agencyHome.getInstance());
		return super.update();
	}

	/*
	 * (non-Javadoc)
	 * @see org.ideaproject.action.entityhome.AbstractLastModifiedEntityHome#persist()
	 */
	@Override
	public String persist() {
		getInstance().setAgency(agencyHome.getInstance());
		return super.persist();
	}

}
