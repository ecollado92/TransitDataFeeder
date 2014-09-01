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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.model.Agency;
import org.ideaproject.model.AgencyGroup;
import org.ideaproject.model.Block;
import org.ideaproject.model.Calendar;
import org.ideaproject.model.Direction;
import org.ideaproject.model.Fare;
import org.ideaproject.model.Location;
import org.ideaproject.model.Route;
import org.ideaproject.model.ServiceScheduleGroup;
import org.ideaproject.model.Stop;
import org.ideaproject.model.Trip;
import org.ideaproject.model.Zone;
import org.ideaproject.util.CollectionUtil;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * @author dirk
 *
 */
@Name("agencyViewBean")
public class AgencyViewBean {
	@In
	AgencyHome agencyHome;

	public List<Zone> getCurrentAgencyZones() {
		return agencyHome.getInstance().getZones();
	}

	public List<Block> getCurrentAgencyBlocks() {
		return agencyHome.getInstance().getBlocks();
	}

	public List<Direction> getCurrentAgencyDirections() {
		return agencyHome.getInstance().getDirections();
	}

	public List<Stop> getCurrentAgencyStops() {
		return agencyHome.getInstance().getStops();
	}

	public List<Location> getCurrentAgencyTransferableStops() {
		Set<Location> resultSet = new HashSet<Location>();
		if (!CollectionUtil.isCollectionEmpty(agencyHome.getInstance().getAgencyGroups())) {
			for (AgencyGroup group : agencyHome.getInstance().getAgencyGroups()) {
				for (Agency agency : group.getAgencies()) {
					resultSet.addAll(agency.getStops());
					resultSet.addAll(agency.getStations());
				}
			}
		} else {
			resultSet.addAll(agencyHome.getInstance().getStops());
			resultSet.addAll(agencyHome.getInstance().getStations());
		}
		return new ArrayList<Location>(resultSet);
	}

	public List<Calendar> getCurrentAgencyCalendars() {
		Set<Calendar> resultSet = new HashSet<Calendar>();
		for (ServiceScheduleGroup scheduleGroup : agencyHome.getInstance().getScheduleGroups()) {
			for (Calendar calendar : scheduleGroup.getCalendars()) {
				resultSet.add(calendar);
			}
		}
		return new ArrayList<Calendar>(resultSet);
	}

	public List<Route> getCurrentAgencyRoutes() {
		return agencyHome.getInstance().getRoutes();
	}

	public List<Trip> getCurrentAgencyTrips() {
		List<Trip> result = new ArrayList<Trip>();
		List<Route> routeList = getCurrentAgencyRoutes();
		for (Route route : routeList) {
			result.addAll(route.getTrips());
		}
		return result;
	}

	public List<Fare> getCurrentAgencyFares() {
		return agencyHome.getInstance().getFares();
	}
}
