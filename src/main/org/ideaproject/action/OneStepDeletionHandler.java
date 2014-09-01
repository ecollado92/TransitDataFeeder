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

import org.ideaproject.action.entityhome.AgencyGroupHome;
import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityhome.CalendarHome;
import org.ideaproject.action.entityhome.FareHome;
import org.ideaproject.action.entityhome.FareRuleHome;
import org.ideaproject.action.entityhome.RouteHome;
import org.ideaproject.action.entityhome.ServiceScheduleBoundHome;
import org.ideaproject.action.entityhome.ServiceScheduleGroupHome;
import org.ideaproject.action.entityhome.StationHome;
import org.ideaproject.action.entityhome.StopHome;
import org.ideaproject.action.entityhome.StopTimeHome;
import org.ideaproject.action.entityhome.ZoneHome;
import org.ideaproject.action.entityquery.AgencyGroupList;
import org.ideaproject.action.entityquery.AgencyList;
import org.ideaproject.action.entityquery.CalendarList;
import org.ideaproject.action.entityquery.FareList;
import org.ideaproject.action.entityquery.FareRuleList;
import org.ideaproject.action.entityquery.RouteList;
import org.ideaproject.action.entityquery.ServiceScheduleBoundList;
import org.ideaproject.action.entityquery.ServiceScheduleGroupList;
import org.ideaproject.action.entityquery.StationList;
import org.ideaproject.action.entityquery.StopList;
import org.ideaproject.action.entityquery.StopTimeList;
import org.ideaproject.action.entityquery.ZoneList;
import org.ideaproject.model.Fare;
import org.ideaproject.model.FareRule;
import org.ideaproject.model.ServiceScheduleGroup;
import org.ideaproject.model.Stop;
import org.ideaproject.model.Trip;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.security.Restrict;

/**
 * @author dirk
 *
 */
@Name("oneStepDeletionHandler")
public class OneStepDeletionHandler {
	@In(create = true)
	private CalendarHome calendarHome;

	@In(create = true)
	private CalendarList calendarList;

	@In(create = true)
	private ServiceScheduleBoundHome serviceScheduleBoundHome;

	@In(create = true)
	private ServiceScheduleBoundList serviceScheduleBoundList;

	@In(create = true)
	private ServiceScheduleGroupHome serviceScheduleGroupHome;

	@In(create = true)
	private ServiceScheduleGroupList serviceScheduleGroupList;

	@In(create = true)
	private StopHome stopHome;

	@In(create = true)
	private StopList stopList;

	@In(create = true)
	private StationHome stationHome;

	@In(create = true)
	private StationList stationList;

	@In(create = true)
	private StopTimeHome stopTimeHome;

	@In(create = true)
	private StopTimeList stopTimeList;
	
	@In(create = true)
	private RouteHome routeHome;

	@In(create = true)
	private RouteList routeList;

	@In(create = true)
	private ZoneHome zoneHome;

	@In(create = true)
	private ZoneList zoneList;

	@In(create = true)
	private FareHome fareHome;

	@In(create = true)
	private FareList fareList;

	@In(create = true)
	private FareRuleHome fareRuleHome;

	@In(create = true)
	private FareRuleList fareRuleList;

	@In(create = true)
	private AgencyHome agencyHome;

	@In(create = true)
	private AgencyList agencyList;

	@In(create = true)
	private AgencyGroupHome agencyGroupHome;

	@In(create = true)
	private AgencyGroupList agencyGroupList;

	public String deleteStop(Integer stopId) {
		String result = "failure";
		Integer originalStopId = stopHome.getStopStopId();
		try {
			stopHome.setStopStopId(stopId);
			result = stopHome.remove();
			return result;
		} finally {
			stopHome.setStopStopId(originalStopId);
			stopList.refresh();
		}
	}

	public String deleteStation(Integer stationId) {
		String result = "failure";
		Integer originalStationId = stationHome.getStopStopId();
		try {
			stationHome.setStopStopId(stationId);
			result = stationHome.remove();
			return result;
		} finally {
			stationHome.setStopStopId(originalStationId);
			stationList.refresh();
		}
	}

	public String deleteRoute(Integer routeId) {
		String result = "failure";
		Integer originalRouteId = routeHome.getRouteRouteId();
		try {
			routeHome.setRouteRouteId(routeId);
			result = routeHome.remove();
			return result;
		} finally {
			routeHome.setRouteRouteId(originalRouteId);
			routeList.refresh();
		}
	}

	public String deleteZone(Integer zoneId) {
		String result = "failure";
		Integer originalZoneId = zoneHome.getZoneZoneId();
		try {
			zoneHome.setZoneZoneId(zoneId);
			result = zoneHome.remove();
			return result;
		} finally {
			zoneHome.setZoneZoneId(originalZoneId);
			zoneList.refresh();
		}
	}

	public String deleteFare(Integer fareId) {
		String result = "failure";
		Integer originalFareId = fareHome.getFareFareId();
		try {
			fareHome.setFareFareId(fareId);
			result = fareHome.remove();
			return result;
		} finally {
			fareHome.setFareFareId(originalFareId);
			fareList.refresh();
			fareRuleList.refresh();
		}
	}

	public String deleteFareRule(Integer fareRuleId) {
		String result = "failure";
		Integer originalFareRuleId = fareRuleHome.getFareRuleFareRuleId();
		try {
			fareRuleHome.setFareRuleFareRuleId(fareRuleId);
			FareRule fareRule = fareRuleHome.getInstance();
			Fare fare = fareRule.getFare();
			fare.getFareRules().remove(fareRule);
			result = fareRuleHome.remove();
			fareHome.getEntityManager().refresh(fare);
			return result;
		} finally {
			fareRuleHome.setFareRuleFareRuleId(originalFareRuleId);
//			fareList.refresh();
			fareRuleList.refresh();
		}
	}

	public String deleteCalendar(Integer calendarId) {
		String result = "failure";
		Integer originalCalendarId = calendarHome.getCalendarCalendarId();
		ServiceScheduleGroup group = null;
		try {
			calendarHome.setCalendarCalendarId(calendarId);
			group = calendarHome.getInstance().getServiceScheduleGroup();
			group.getCalendars().remove(calendarHome.getInstance());
			calendarHome.getEntityManager().refresh(calendarHome.getInstance());
			result = calendarHome.remove();
			return result;
		} finally {
			calendarHome.setCalendarCalendarId(originalCalendarId);
			calendarList.refresh();
		}
	}

	public String deleteServiceScheduleBound(Integer boundsId) {
		String result = "failure";
		Integer originalBoundsId = serviceScheduleBoundHome.getServiceScheduleBoundServiceScheduleBoundsId();
		ServiceScheduleGroup group = null;
		try {
			serviceScheduleBoundHome.setServiceScheduleBoundServiceScheduleBoundsId(boundsId);
			group = serviceScheduleBoundHome.getInstance().getServiceScheduleGroup();
			group.getServiceScheduleBounds().remove(serviceScheduleBoundHome.getInstance());
			result = serviceScheduleBoundHome.remove();
			return result;
		} finally {
			serviceScheduleBoundHome.setServiceScheduleBoundServiceScheduleBoundsId(originalBoundsId);
			serviceScheduleBoundList.refresh();
		}
	}


	public String deleteStopTime(Integer stopTimeId) {
		String result = "failure";
		Integer originalStopTimeId = stopTimeHome.getStopTimeStopTimeId();
		Trip trip = null;
		Stop stop = null;
		try {
			stopTimeHome.setStopTimeStopTimeId(stopTimeId);
			trip = stopTimeHome.getInstance().getTrip();
			trip.getStopTimes().remove(stopTimeHome.getInstance());
			stop = stopTimeHome.getInstance().getStop();
			if (stop != null) {
				stop.getStopTimes().remove(stopTimeHome.getInstance());
			}
			result = stopTimeHome.remove();
			return result;
		} finally {
			stopTimeHome.setStopTimeStopTimeId(originalStopTimeId);
			stopTimeList.refresh();
		}
	}

	@Restrict("#{s:hasRole('admin')}")
	public String deleteAgencyGroup(Integer agencyGroupId) {
		String result = "failure";
		Integer originalAgencyGroupId = agencyGroupHome.getAgencyGroupAgencyGroupId();
		try {
			agencyGroupHome.setAgencyGroupAgencyGroupId(agencyGroupId);
			result = agencyGroupHome.remove();
			return result;
		} finally {
			agencyGroupHome.setAgencyGroupAgencyGroupId(originalAgencyGroupId);
			agencyGroupList.refresh();
		}
	}

	@Restrict("#{s:hasRole('admin')}")
	public String deleteAgency(Integer agencyId) {
		String result = "failure";
		Integer originalAgencyId = agencyHome.getAgencyAgencyId();
		try {
			agencyHome.setAgencyAgencyId(agencyId);
			result = agencyHome.remove();
			return result;
		} finally {
			agencyHome.setAgencyAgencyId(originalAgencyId);
			agencyList.refresh();
		}
	}

}
