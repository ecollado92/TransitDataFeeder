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

import java.util.ArrayList;
import java.util.List;

import org.ideaproject.QueryNames;
import org.ideaproject.model.Stop;
import org.jboss.seam.annotations.Name;

/**
 * Provides the "R" from "CRUD" for {@link org.ideaproject.model.Stop} entities.
 * 
 * @author dirk
 * @see org.ideaproject.action.entityquery.AbstractCountableLastResultEntityQuery
 * @see org.jboss.seam.freamework.EntityQuery
 */
@Name("stopList")
public class StopList extends AbstractCountableLastResultEntityQuery<Stop> {
	private static final String TMP = "exists (select route from Route route inner join route.trips trip inner join trip.stopTimes stopTime inner join stopTime.stop s where s.stopId = stop.stopId and route.routeId = #{stopList.routeId})";

	private static final String EJBQL = "select stop from Stop stop";

	private static final List<String> RESTRICTIONS = new ArrayList<String>();
	static {
		RESTRICTIONS.add("lower(stop.stopCode) like lower(concat(#{stopList.stop.stopCode},'%'))");
		RESTRICTIONS.add("lower(stop.stopComments) like lower(concat('%',concat(#{stopList.stop.stopComments},'%')))");
		RESTRICTIONS.add("lower(stop.stopDesc) like lower(concat(#{stopList.stop.stopDesc},'%'))");
		RESTRICTIONS.add("lower(stop.stopName) like lower(concat(#{stopList.stop.stopName},'%'))");
		RESTRICTIONS.add("stop.agency = #{agencyHome.definedInstance}");
		RESTRICTIONS.add("stop.zone.zoneId = #{stopList.zoneId}");
		RESTRICTIONS.add("stop.geolocated = #{stopList.geolocated}");
		RESTRICTIONS.add("stop.inSchedules = #{stopList.inSchedules}");
		RESTRICTIONS.add("lower(stop.stopUrl) like lower(concat(#{stopList.stop.stopUrl},'%'))");
		RESTRICTIONS.add("stop.parentStation.stopId = #{stopList.parentStationId}");
		RESTRICTIONS.add(TMP);
	}
	
	private Stop stop = new Stop();
	private Integer zoneId;
	private Boolean geolocated = null;
	private Boolean inSchedules = null;
	private Integer routeId;
	private Integer parentStationId = null;

	public StopList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(RESTRICTIONS);
		setMaxResults(25);
	}

	public Long getCurrentAgencyStopCount() {
		return getNamedCountQueryResult(QueryNames.COUNT_AGENCY_STOPS_ALL, agencyHome.getInstance());
	}

	public Long getCurrentAgencyNonLocatedStopCount() {
		return getNamedCountQueryResult(QueryNames.COUNT_AGENCY_STOPS_NO_LOCATION, agencyHome.getInstance());
	}

	public Long getCurrentAgencyUncheduledStopCount() {
		return getNamedCountQueryResult(QueryNames.COUNT_AGENCY_STOPS_UNSCHEDULED, agencyHome.getInstance());
	}

	public Stop getStop() {
		return stop;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	/**
	 * @return the geolocated
	 */
	public Boolean getGeolocated() {
		return geolocated;
	}

	/**
	 * @param geolocated the geolocated to set
	 */
	public void setGeolocated(Boolean geolocated) {
		this.geolocated = geolocated;
	}

	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	/**
	 * @return the inSchedules
	 */
	public Boolean getInSchedules() {
		return inSchedules;
	}

	/**
	 * @param inSchedules the inSchedules to set
	 */
	public void setInSchedules(Boolean inSchedules) {
		this.inSchedules = inSchedules;
	}

	/**
	 * @return the parentStationId
	 */
	public Integer getParentStationId() {
		return parentStationId;
	}

	/**
	 * @param parentStationId the parentStationId to set
	 */
	public void setParentStationId(Integer parentStationId) {
		this.parentStationId = parentStationId;
	}

}
