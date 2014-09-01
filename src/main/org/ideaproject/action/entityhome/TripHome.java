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

import org.ideaproject.model.Block;
import org.ideaproject.model.Calendar;
import org.ideaproject.model.Route;
import org.ideaproject.model.StopTime;
import org.ideaproject.model.Trip;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Seam EntityHome for Trips
 * @author dirk
 * @see org.ideaproject.action.entityhome.AbstractLastModifiedEntityHome
 */
@Name("tripHome")
public class TripHome extends AbstractLastModifiedEntityHome<Trip> {

	private static final long serialVersionUID = 1L;

	/**
	 * The entity home from which we can determine the current instance's
	 * route association.
	 */
	@In(create = true)
	private RouteHome routeHome;

	/**
	 * The entity home from which we can determine the current instance's
	 * calendar association.
	 */
	@In(create = true)
	private CalendarHome calendarHome;

	/**
	 * The entity home from which we can determine the current instance's
	 * block association.
	 */
	@In(create = true)
	private BlockHome blockHome;

	/**
	 * The entity home from which we can determine the current instance's
	 * stop time associations.
	 */
	@In(create = true)
	private StopTimeHome stopTimeHome;

	/**
	 * Sets the current trip by ID; side effect updates the
	 * instance.
	 * @param id the bike option ID
	 */
	public void setTripTripId(Integer id) {
		setId(id);
	}

	/**
	 * @return the ID of the current trip.
	 */
	public Integer getTripTripId() {
		return (Integer) getId();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Trip createInstance() {
		Trip trip = new Trip();
		return trip;
	}

	/**
	 * Loads the current trip.
	 */
	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	@Override
	public String remove() {
		Trip instance = getInstance();
		Route owning = instance.getRoute();
		owning.getTrips().remove(instance);
		List<Trip> children = instance.getTripChildren();
		/** Must pre-process children too since the deletion cascades to them */
		if (children != null && !children.isEmpty()) {
			for (Trip child : instance.getTripChildren()) {
				child.getRoute().getTrips().remove(child);
			}
		}
		Integer stopTimeId = stopTimeHome.getStopTimeStopTimeId();
		try {
			for (StopTime stopTime : getInstance().getStopTimes()) {
				stopTimeHome.setInstance(stopTime);
				stopTimeHome.getInstance().setTrip(null);
				stopTimeHome.update();
			}
		} finally {
			stopTimeHome.setStopTimeStopTimeId(stopTimeId);
		}
		return super.remove();
	}

	/**
	 * "Wires" the current instance
	 */
	public void wire() {
		getInstance();
		Route route = routeHome.getDefinedInstance();
		if (route != null) {
			getInstance().setRoute(route);
		}
		Calendar calendar = calendarHome.getDefinedInstance();
		if (calendar != null) {
			getInstance().setCalendar(calendar);
		}
		Block block = blockHome.getDefinedInstance();
		if (block != null) {
			getInstance().setBlock(block);
		}
	}

	/**
	 * @return whether or not the current trip is "wired"
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
	public Trip getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
