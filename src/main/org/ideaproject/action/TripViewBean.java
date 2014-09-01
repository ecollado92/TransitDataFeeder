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
package org.ideaproject.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ideaproject.action.entityhome.RouteHome;
import org.ideaproject.action.entityhome.TripHome;
import org.ideaproject.model.Direction;
import org.ideaproject.model.StopTime;
import org.ideaproject.model.Trip;
import org.ideaproject.util.TripUtil;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * @author dirk
 *
 */
@Name("tripViewBean")
public class TripViewBean {
	@In(create = true)
	private AgencyViewBean agencyViewBean;

	@In(create = true)
	private RouteHome routeHome;

	@In(create = true)
	private TripHome tripHome;

	private Map<Integer, List<StopTime>> inheritedStopTimes = new HashMap<Integer, List<StopTime>>();

	public List<Trip> getAvailableTripParents() {
		List<Trip> result = new ArrayList<Trip>();
		List<Trip> potentials = (routeHome.getDefinedInstance() != null)
			? routeHome.getInstance().getTrips()
			: agencyViewBean.getCurrentAgencyTrips();

		for (Trip potential : potentials) {
			if (potential.getBasedOn() == null) {
				result.add(potential);
			}
		}
		return result;
	}

	public List<Direction> getCurrentRouteDirections() {
		return agencyViewBean.getCurrentAgencyDirections();
//		Set<Direction> directionSet = new HashSet<Direction>();
//		if (routeHome.getDefinedInstance() != null) {
//			for (Trip trip : routeHome.getInstance().getTrips()) {
//				Direction dir = trip.getDirection();
//				if (dir != null) {
//					directionSet.add(dir);
//				}
//			}
//		}
//		else {
//			for (Direction dir : agencyViewBean.getCurrentAgencyDirections()) {
//				directionSet.add(dir);
//			}
//		}
//		return new ArrayList<Direction>(directionSet);
	}

	public List<StopTime> getCurrentTripInheritedStopTimes() {
		Trip current = tripHome.getInstance();
		if (inheritedStopTimes.containsKey(current.getTripId())) {
			return inheritedStopTimes.get(current.getTripId());
		}
		List<StopTime> stList = TripUtil.buildInheritedStopTimeList(tripHome.getInstance());
		inheritedStopTimes.put(current.getTripId(), stList);
		return stList;
	}
}
