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

import org.ideaproject.model.Agency;
import org.ideaproject.model.BaseLocation;
import org.ideaproject.model.Direction;
import org.ideaproject.model.User;
import org.ideaproject.model.Zone;
import org.jboss.seam.annotations.In;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.security.management.JpaIdentityStore;

/**
 * Seam EntityHome for Locations.  Incorporates logic for setting {@link org.ideaproject.model.Agency}
 * and last modified date/user information into the entity home, since {@link org.ideaproject.action.entityhome.AbstractLastModifiedEntityHome}
 * and {@link org.ideaproject.action.entityhome.AbstractAgencyBasedEntityHome} work only with entities
 * which are subclasses of {@link org.ideaproject.model.AbstractLastModifiedEntity} and
 * {@link org.ideaproject.model.AbstractAgencyBasedEntity}, respectively.  Please see
 * {@link org.ideaproject.model.BaseLocation} for details as to why Locations are not subclasses of
 * those.
 *  
 * @author dirk
 * @see org.ideaproject.action.entityhome.AbstractLastModifiedEntityHome
 * @see org.ideaproject.action.entityhome.AbstractAgencyBasedEntityHome
 * @see org.ideaproject.model.AbstractLastModifiedEntity
 * @see org.ideaproject.model.AbstractAgencyBasedEntity
 * @see org.ideaproject.model.BaseLocation
 * 
 */
public abstract class LocationHome<T extends BaseLocation> extends EntityHome<T> {
	@In(create = true)
	protected AgencyHome agencyHome;


	private static final long serialVersionUID = 1L;

	/**
	 * The entity home from which we can determine the current instance's
	 * direction association.
	 */
	@In(create = true)
	DirectionHome directionHome;

	/**
	 * The entity home from which we can determine the current instance's
	 * zone association.
	 */
	@In(create = true)
	ZoneHome zoneHome;

	/**
	 * Sets the current stop by ID; side effect updates the
	 * instance.
	 * @param id the bike option ID
	 */
	public void setStopStopId(Integer id) {
		setId(id);
	}

	/**
	 * @return the ID of the current stop.
	 */
	public Integer getStopStopId() {
		return (Integer) getId();
	}

	/**
	 * Loads the current stop.
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
		Agency agency = agencyHome.getDefinedInstance();
		if (agency != null) {
			getInstance().setAgency(agency);
		}

		Direction direction = directionHome.getDefinedInstance();
		if (direction != null) {
			getInstance().setDirection(direction);
		}

		Zone zone = zoneHome.getDefinedInstance();
		if (zone != null) {
			getInstance().setZone(zone);
		}
	}

	/**
	 * @return whether or not the current stop is "wired"
	 * @see #wire()
	 */
	public boolean isWired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ideaproject.action.entityhome.AbstractAgencyBasedEntityHome#persist()
	 */
	@Override
	public String persist() {
		getInstance().setAgency(agencyHome.getInstance());
		updateInstanceLastModifiedInfo();
		String result = super.persist();
		T instance = getInstance();
		if (instance.getStopLon() != null && instance.getStopLat() != null) {
			instance.setGeolocated(true);
		}
		return result;
	}

	@Override
	public String update() {
		getInstance().setAgency(agencyHome.getInstance());
		updateInstanceLastModifiedInfo();
		String result = super.update();
		T instance = getInstance();
		if (instance.getStopLon() != null && instance.getStopLat() != null) {
			instance.setGeolocated(true);
		}
		return result;
	}

	/**
	 * Gets the currently defined instance; may return null if the ID for this
	 * EntityHome is not supplied.
	 * @return the currently-defined instance, or null if none is defined.
	 */
	public T getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
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
