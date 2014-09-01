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
package org.ideaproject.action.entityquery;

import java.util.Arrays;

import org.ideaproject.QueryNames;
import org.ideaproject.action.entityhome.BlockHome;
import org.ideaproject.action.entityhome.CalendarHome;
import org.ideaproject.action.entityhome.DirectionHome;
import org.ideaproject.action.entityhome.RouteHome;
import org.ideaproject.model.Trip;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

@Name("tripList")
public class TripList extends AbstractCountableLastResultEntityQuery<Trip> {
	@In(required = false)
	private CalendarHome calendarHome;

	@In(required = false)
	private RouteHome routeHome;

	@In(required = false)
	private DirectionHome directionHome;

	@In(required = false)
	private BlockHome blockHome;

	private static final String EJBQL = "select trip from Trip trip";

	private static final String[] RESTRICTIONS = {
			"trip.route.agency = #{agencyHome.definedInstance}",
			"trip.route = #{routeHome.definedInstance}",
			"trip.calendar = #{calendarHome.definedInstance}",
			"trip.direction = #{directionHome.definedInstance}",
			"trip.block = #{blockHome.definedInstance}",
			"#{tripList.hasStopTimesForQuery} = (SELECT CASE COUNT(*) WHEN 0 THEN 'false' ELSE 'true' END FROM trip.stopTimes)",
			"lower(trip.tripHeadsign) like lower(concat(#{tripList.trip.tripHeadsign},'%'))",};

	private Trip trip = new Trip();
	private Boolean hasStopTimes = null;

	public TripList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public void clearListAssociations() {
		if (routeHome != null) {
			routeHome.clearInstance();
		}
		if (calendarHome != null) {
			calendarHome.clearInstance();
		}
		if (directionHome != null) {
			directionHome.clearInstance();
		}
		if (blockHome != null) {
			blockHome.clearInstance();
		}
	}

	public Trip getTrip() {
		return trip;
	}

	public String getHasStopTimesForQuery() {
		if (hasStopTimes != null) {
			return hasStopTimes.toString();
		}
		return null;
	}

	public Boolean getHasStopTimes() {
		return hasStopTimes;
	}

	public void setHasStopTimes(Boolean hasStopTimes) {
		this.hasStopTimes = hasStopTimes;
	}

	public Long getCurrentAgencyTripCount() {
		return getNamedCountQueryResult(QueryNames.COUNT_AGENCY_TRIPS_ALL, agencyHome.getInstance());
	}

	public Long getCurrentAgencyNoStopTimeTripCount() {
		return getNamedCountQueryResult(QueryNames.COUNT_AGENCY_TRIPS_NO_STOPTIME, agencyHome.getInstance());
	}

}
