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

import org.ideaproject.model.Station;
import org.jboss.seam.annotations.Name;

/**
 * Provides the "R" from "CRUD" for {@link org.ideaproject.model.Station} entities.
 * 
 * @author dirk
 * @see org.ideaproject.action.entityquery.AbstractLastResultEntityQuery
 * @see org.jboss.seam.freamework.EntityQuery
 */
@Name("stationList")
public class StationList extends AbstractLastResultEntityQuery<Station> {
	private static final String EJBQL = "select station from Station station";

	private static final List<String> RESTRICTIONS = new ArrayList<String>();
	static {
		RESTRICTIONS.add("lower(station.stopCode) like lower(concat(#{stationList.station.stopCode},'%'))");
		RESTRICTIONS.add("lower(station.stopComments) like lower(concat('%',concat(#{stationList.station.stopComments},'%')))");
		RESTRICTIONS.add("lower(station.stopDesc) like lower(concat(#{stationList.station.stopDesc},'%'))");
		RESTRICTIONS.add("lower(station.stopName) like lower(concat(#{stationList.station.stopName},'%'))");
		RESTRICTIONS.add("station.agency = #{agencyHome.definedInstance}");
		RESTRICTIONS.add("station.zone.zoneId = #{stationList.zoneId}");
		RESTRICTIONS.add("station.geolocated = #{stationList.geolocated}");
		RESTRICTIONS.add("lower(station.stopUrl) like lower(concat(#{stationList.station.stopUrl},'%'))");
	}
	
	private Station station = new Station();
	private Integer zoneId;
	private Boolean geolocated = null;

	public StationList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(RESTRICTIONS);
		setMaxResults(25);
	}

	public Station getStation() {
		return station;
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
}
