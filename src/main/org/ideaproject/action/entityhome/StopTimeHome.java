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

import org.ideaproject.model.Stop;
import org.ideaproject.model.StopTime;
import org.ideaproject.model.Trip;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Seam EntityHome for StopTimes
 * @author dirk
 * @see org.ideaproject.action.entityhome.AbstractAgencyBasedEntityHome
 */
@Name("stopTimeHome")
public class StopTimeHome extends AbstractAgencyBasedEntityHome<StopTime> {

	private static final long serialVersionUID = 1L;

	/**
	 * The entity home from which we can determine the current instance's
	 * trip association.
	 */
	@In(create = true)
	private TripHome tripHome;

	/**
	 * The entity home from which we can determine the current instance's
	 * stop association.
	 */
	@In(create = true)
	private StopHome stopHome;

	/**
	 * Sets the current stop time by ID; side effect updates the
	 * instance.
	 * @param id the bike option ID
	 */
	public void setStopTimeStopTimeId(Integer id) {
		setId(id);
	}

	/**
	 * @return the ID of the current stop time.
	 */
	public Integer getStopTimeStopTimeId() {
		return (Integer) getId();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected StopTime createInstance() {
		StopTime stopTime = new StopTime();
		return stopTime;
	}

	/**
	 * Loads the current stop time.
	 */
	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.ideaproject.action.entityhome.AbstractAgencyBasedEntityHome#wire()
	 */
	@Override
	public void wire() {
		super.wire();
		Trip trip = tripHome.getDefinedInstance();
		if (trip != null) {
			getInstance().setTrip(trip);
		}
		Stop stop = stopHome.getDefinedInstance();
		if (stop != null) {
			getInstance().setStop(stop);
		}
	}

	/**
	 * @return whether or not the current stop time is "wired"
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
	public StopTime getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
