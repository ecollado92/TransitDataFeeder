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
package org.ideaproject.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "zone")
public class Zone extends AbstractAgencyBasedEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "zone_id", unique = true, nullable = false)
	private int zoneId;

	@Column(name = "zone_name", length = 30, nullable = false)
	@Length(max = 30)
	private String zoneName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "origin")
	private List<FareRule> originFareRules = new ArrayList<FareRule>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "destination")
	private List<FareRule> destinationFareRules = new ArrayList<FareRule>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contains")
	private List<FareRule> containsFareRules = new ArrayList<FareRule>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "zone")
	private List<Stop> stops = new ArrayList<Stop>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "zone")
	private List<Station> stations = new ArrayList<Station>();

	public Zone() {
	}

	public Zone(int zoneId, Agency agency) {
		this.zoneId = zoneId;
		setAgency(agency);
	}

	public Zone(int zoneId, String zoneName, Agency agency) {
		this.zoneId = zoneId;
		this.zoneName = zoneName;
		setAgency(agency);
	}

	public int getZoneId() {
		return this.zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneName() {
		return this.zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	/**
	 * @return the originFareRules
	 */
	public List<FareRule> getOriginFareRules() {
		return originFareRules;
	}

	/**
	 * @param originFareRules the originFareRules to set
	 */
	public void setOriginFareRules(List<FareRule> originFareRules) {
		this.originFareRules = originFareRules;
	}

	/**
	 * @return the destinationFareRules
	 */
	public List<FareRule> getDestinationFareRules() {
		return destinationFareRules;
	}

	/**
	 * @param destinationFareRules the destinationFareRules to set
	 */
	public void setDestinationFareRules(List<FareRule> destinationFareRules) {
		this.destinationFareRules = destinationFareRules;
	}

	/**
	 * @return the containsFareRules
	 */
	public List<FareRule> getContainsFareRules() {
		return containsFareRules;
	}

	/**
	 * @param containsFareRules the containsFareRules to set
	 */
	public void setContainsFareRules(List<FareRule> containsFareRules) {
		this.containsFareRules = containsFareRules;
	}

	/**
	 * @return the stops
	 */
	public List<Stop> getStops() {
		return stops;
	}

	/**
	 * @param stops the stops to set
	 */
	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}

	/**
	 * @return the stations
	 */
	public List<Station> getStations() {
		return stations;
	}

	/**
	 * @param stations the stations to set
	 */
	public void setStations(List<Station> stations) {
		this.stations = stations;
	}

}
