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

import org.ideaproject.model.BikeOption;
import org.ideaproject.model.FareRule;
import org.ideaproject.model.Route;
import org.ideaproject.model.RouteType;
import org.ideaproject.model.StopTime;
import org.ideaproject.model.Trip;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Seam EntityHome for Routes
 * @author dirk
 * @see org.ideaproject.action.entityhome.AbstractAgencyBasedEntityHome
 */
@Name("routeHome")
public class RouteHome extends AbstractAgencyBasedEntityHome<Route> {
	private static final long serialVersionUID = 1L;

	@In(create = true)
	private FareRuleHome fareRuleHome;

	@In(create = true)
	private StopTimeHome stopTimeHome;

	/**
	 * The entity home from which we can determine the current instance's
	 * route type association.
	 */
	@In(create = true)
	RouteTypeHome routeTypeHome;

	/**
	 * The entity home from which we can determine the current instance's
	 * bile option association.
	 */
	@In(create = true)
	BikeOptionHome bikeOptionHome;

	/**
	 * Sets the current route by ID; side effect updates the
	 * instance.
	 * @param id the bike option ID
	 */
	public void setRouteRouteId(Integer id) {
		setId(id);
	}

	/**
	 * @return the ID of the current route.
	 */
	public Integer getRouteRouteId() {
		return (Integer) getId();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Route createInstance() {
		Route route = new Route();
		route.setAgency(agencyHome.getInstance());
		return route;
	}

	/**
	 * Loads the current route.
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
		RouteType routeType = routeTypeHome.getDefinedInstance();
		if (routeType != null) {
			getInstance().setRouteType(routeType);
		}
		BikeOption bikeOption = bikeOptionHome.getDefinedInstance();
		if (bikeOption != null) {
			getInstance().setBikeOption(bikeOption);
		}
	}

	/**
	 * @return whether or not the current route is "wired"
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
	public Route getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.EntityHome#remove()
	 */
	@Override
	public String remove() {
		Integer originalFareRuleId = fareRuleHome.getFareRuleFareRuleId();
		Integer originalStopTimeId = stopTimeHome.getStopTimeStopTimeId();
		try {
			for (FareRule fareRule : getInstance().getFareRules()) {
				fareRuleHome.setInstance(fareRule);
				fareRuleHome.getInstance().setRoute(null);
				fareRuleHome.update();
			}
			// Cascade rules remove trips and leave FK constraint error for stop time to trip;
			// unhook stop times from trips
			for (Trip trip : getInstance().getTrips()) {
				for (StopTime stopTime : trip.getStopTimes()) {
					stopTimeHome.setInstance(stopTime);
					stopTimeHome.getInstance().setTrip(null);
					stopTimeHome.update();
				}
			}
		} finally {
			fareRuleHome.setFareRuleFareRuleId(originalFareRuleId);
			stopTimeHome.setStopTimeStopTimeId(originalStopTimeId);
		}
		return super.remove();
	}

}
