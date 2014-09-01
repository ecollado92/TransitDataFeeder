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

import java.util.Date;
import java.util.List;

import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityquery.CalendarDateList;
import org.ideaproject.action.entityquery.FareList;
import org.ideaproject.action.entityquery.RouteList;
import org.ideaproject.action.entityquery.StopList;
import org.ideaproject.action.entityquery.TripList;
import org.ideaproject.model.CalendarDate;
import org.ideaproject.model.ServiceScheduleGroup;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * @author dirk
 *
 */
@Name("overviewBean")
public class OverviewBean {
	@In
	private AgencyHome agencyHome;

	@In(create = true)
	private StopList stopList;

	@In(create = true)
	private TripList tripList;

	@In(create = true)
	private RouteList routeList;

	@In(create = true)
	private FareList fareList;

	@In(create = true)
	private CalendarDateList calendarDateList;

	private Long allStopsCount = null;
	private Long nonLocatedStopsCount = null;
	private Long unscheduledStopsCount = null;

	private Long allTripsCount = null;
	private Long noStopTimesTripsCount = null;

	private Long allRoutesCount = null;
	private Long noTripsRoutesCount = null;

	private Long allFaresCount = null;
	private Long unusedFaresCount = null;

	private List<CalendarDate> upcomingHolidays = null;

	public List<ServiceScheduleGroup> getScheduleGroups() {
		return agencyHome.getInstance().getScheduleGroups();
	}

	public Long getAllStopsCount() {
		if (allStopsCount == null) {
			allStopsCount = stopList.getCurrentAgencyStopCount();
		}
		return allStopsCount;
	}

	/**
	 * @return the nonLocatedStopsCount
	 */
	public Long getNonLocatedStopsCount() {
		if (nonLocatedStopsCount == null) {
			nonLocatedStopsCount = stopList.getCurrentAgencyNonLocatedStopCount();
		}
		return nonLocatedStopsCount;
	}

	/**
	 * @return the unscheduledStopsCount
	 */
	public Long getUnscheduledStopsCount() {
		if (unscheduledStopsCount == null) {
			unscheduledStopsCount = stopList.getCurrentAgencyUncheduledStopCount();
		}
		return unscheduledStopsCount;
	}

	/**
	 * @return the allTripsCount
	 */
	public Long getAllTripsCount() {
		if (allTripsCount == null) {
			allTripsCount = tripList.getCurrentAgencyTripCount();
		}
		return allTripsCount;
	}

	/**
	 * @return the noStopTimesTripsCount
	 */
	public Long getNoStopTimesTripsCount() {
		if (noStopTimesTripsCount == null) {
			noStopTimesTripsCount = tripList.getCurrentAgencyNoStopTimeTripCount();
		}
		return noStopTimesTripsCount;
	}

	/**
	 * @return the allRoutesCount
	 */
	public Long getAllRoutesCount() {
		if (allRoutesCount == null) {
			allRoutesCount = routeList.getCurrentAgencyRouteCount();
		}
		return allRoutesCount;
	}

	/**
	 * @return the noTripsRoutesCount
	 */
	public Long getNoTripsRoutesCount() {
		if (noTripsRoutesCount == null) {
			noTripsRoutesCount = routeList.getCurrentAgencyNoTripsRouteCount();
		}
		return noTripsRoutesCount;
	}

	/**
	 * @return the allFaresCount
	 */
	public Long getAllFaresCount() {
		if (allFaresCount == null) {
			allFaresCount = fareList.getCurrentAgencyFareCount();
		}
		return allFaresCount;
	}

	/**
	 * @return the unusedFaresCount
	 */
	public Long getUnusedFaresCount() {
		if (unusedFaresCount == null) {
			unusedFaresCount = fareList.getCurrentAgencyUnusedFareCount();
		}
		return unusedFaresCount;
	}

	/**
	 * @return the upcomingHolidays
	 */
	public List<CalendarDate> getUpcomingHolidays() {
		if (upcomingHolidays == null) {
			Integer originalMaxResults = calendarDateList.getMaxResults();
			try {
				calendarDateList.setMaxResults(null);
				calendarDateList.setEarliest(new Date());
				upcomingHolidays = calendarDateList.getResultList();
			} finally {
				calendarDateList.setMaxResults(originalMaxResults);
			}
		}
		return upcomingHolidays;
	}
	
}
