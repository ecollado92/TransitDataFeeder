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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.ideaproject.Constants;
import org.ideaproject.Exportable;
import org.ideaproject.util.HashCodeUtil;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "agency")
public class Agency extends AbstractLastModifiedEntity implements java.io.Serializable, Exportable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agency_id", unique = true, nullable = false)
	private int agencyId = -1;

	@Column(name = "agency_url", nullable = false, length = 100)
	@NotNull
	@Length(max = 100)
	private String agencyUrl;

	@Column(name = "agency_timezone", nullable = false, length = 45)
	@NotNull
	@Length(max = 45)
	private String agencyTimezone;

	@Column(name = "agency_name", nullable = false, length = 120)
	@NotNull
	@Length(max = 120)
	private String agencyName;

	@Column(name = "agency_short_name", nullable = false, length = 10)
	@NotNull
	@Length(max = 10)
	private String agencyShortName;

	@Column(name = "agency_lang", length = 2, nullable = false)
	@NotNull
	@Length(min = 2, max = 2)
	private String agencyLanguage = Constants.DEFAULT_AGENCY_LANGUAGE;

	@Column(name = "agency_phone", length = 25)
	@Length(max = 25)
	private String agencyPhone;

	@Column(name = "agency_fare_url")
	private String agencyFareUrl;

	@Column(name = "agency_lon", precision = 17, scale = 17)
	private Double agencyLongitude = -122.664242;

	@Column(name = "agency_lat", precision = 17, scale = 17)
	private Double agencyLatitude = 45.518557;

//	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//	@JoinTable(name = "agency_group_map", 
//			joinColumns = @JoinColumn(name="agency_id"), 
//			inverseJoinColumns = @JoinColumn(name="agency_group_id"))
	@ManyToMany(mappedBy = "agencies")
	private List<AgencyGroup> agencyGroups = new ArrayList<AgencyGroup>();

	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn(name = "address_id")
	private Address address;

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "agency")
	private List<Zone> zones = new ArrayList<Zone>();

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "agency")
	private List<Route> routes = new ArrayList<Route>();

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "agency")
	private List<Block> blocks = new ArrayList<Block>();

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "agency")
	private List<Stop> stops = new ArrayList<Stop>();

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "agency")
	private List<Station> stations = new ArrayList<Station>();;

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "agency")
	private List<Transfer> transfers = new ArrayList<Transfer>();

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "agency")
	private List<Fare> fares = new ArrayList<Fare>();

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "agency")
	private List<ServiceScheduleGroup> scheduleGroups = new ArrayList<ServiceScheduleGroup>();

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "agency")
	private List<CalendarDate> calendarDates = new ArrayList<CalendarDate>();

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "agency")
	private List<Direction> directions = new ArrayList<Direction>();

//	@OneToMany
//	@JoinTable(name = "user_agency_map", 
//			joinColumns = @JoinColumn(name="agency_id"), 
//			inverseJoinColumns = @JoinColumn(name="user_id"))
	@ManyToMany
	@JoinTable(name = "user_agency_map", 
			joinColumns = @JoinColumn(name="agency_id"), 
			inverseJoinColumns = @JoinColumn(name="user_id"))
	private List<User> agencyUsers = new ArrayList<User>();

	public Agency() {
	}

	public Agency(int agencyId, String agencyUrl, String agencyTimezone,
			String agencyName, String agencyShortName, String agencyFareUrl) {
		this.agencyId = agencyId;
		this.agencyUrl = agencyUrl;
		this.agencyTimezone = agencyTimezone;
		this.agencyName = agencyName;
		this.agencyShortName = agencyShortName;
		this.agencyFareUrl = agencyFareUrl;
	}

	public Agency(int agencyId, String agencyUrl, String agencyTimezone,
			String agencyName, String agencyShortName, String agencyPhone,
			String agencyFareUrl, List<AgencyGroup> agencyGroups) {
		this.agencyId = agencyId;
		this.agencyUrl = agencyUrl;
		this.agencyTimezone = agencyTimezone;
		this.agencyName = agencyName;
		this.agencyShortName = agencyShortName;
		this.agencyPhone = agencyPhone;
		this.agencyFareUrl = agencyFareUrl;
		this.agencyGroups = agencyGroups;
	}

	public int getAgencyId() {
		return this.agencyId;
	}

	public void setAgencyId(int agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyUrl() {
		return this.agencyUrl;
	}

	public void setAgencyUrl(String agencyUrl) {
		this.agencyUrl = agencyUrl;
	}

	public String getAgencyTimezone() {
		return this.agencyTimezone;
	}

	public void setAgencyTimezone(String agencyTimezone) {
		this.agencyTimezone = agencyTimezone;
	}

	public String getAgencyName() {
		return this.agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAgencyShortName() {
		return this.agencyShortName;
	}

	public void setAgencyShortName(String agencyShortName) {
		this.agencyShortName = agencyShortName;
	}

	public String getAgencyPhone() {
		return this.agencyPhone;
	}

	public void setAgencyPhone(String agencyPhone) {
		this.agencyPhone = agencyPhone;
	}

	public String getAgencyFareUrl() {
		return this.agencyFareUrl;
	}

	public void setAgencyFareUrl(String agencyFareUrl) {
		this.agencyFareUrl = agencyFareUrl;
	}

	/**
	 * @return the agencyGroups
	 */
	public List<AgencyGroup> getAgencyGroups() {
		return agencyGroups;
	}

	/**
	 * @param agencyGroups the agencyGroups to set
	 */
	public void setAgencyGroups(List<AgencyGroup> agencyGroups) {
		this.agencyGroups = agencyGroups;
	}

	/**
	 * @return the zones
	 */
	public List<Zone> getZones() {
		return zones;
	}

	/**
	 * @param zones the zones to set
	 */
	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}

	/**
	 * @return the routes
	 */
	public List<Route> getRoutes() {
		return routes;
	}

	/**
	 * @param routes the routes to set
	 */
	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	/**
	 * @return the blocks
	 */
	public List<Block> getBlocks() {
		return blocks;
	}

	/**
	 * @param blocks the blocks to set
	 */
	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
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

	/**
	 * @return the transfers
	 */
	public List<Transfer> getTransfers() {
		return transfers;
	}

	/**
	 * @param transfers the transfers to set
	 */
	public void setTransfers(List<Transfer> transfers) {
		this.transfers = transfers;
	}

	/**
	 * @return the fares
	 */
	public List<Fare> getFares() {
		return fares;
	}

	/**
	 * @param fares the fares to set
	 */
	public void setFares(List<Fare> fares) {
		this.fares = fares;
	}

	/**
	 * @return the scheduleGroups
	 */
	public List<ServiceScheduleGroup> getScheduleGroups() {
		return scheduleGroups;
	}

	/**
	 * @param scheduleGroups the scheduleGroups to set
	 */
	public void setScheduleGroups(List<ServiceScheduleGroup> scheduleGroups) {
		this.scheduleGroups = scheduleGroups;
	}

	/**
	 * @return the calendarDates
	 */
	public List<CalendarDate> getCalendarDates() {
		return calendarDates;
	}

	/**
	 * @param calendarDates the calendarDates to set
	 */
	public void setCalendarDates(List<CalendarDate> calendarDates) {
		this.calendarDates = calendarDates;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the agencyLongitude
	 */
	public Double getAgencyLongitude() {
		return agencyLongitude;
	}

	/**
	 * @param agencyLongitude the agencyLongitude to set
	 */
	public void setAgencyLongitude(Double agencyLongitude) {
		this.agencyLongitude = agencyLongitude;
	}

	/**
	 * @return the agencyLatitude
	 */
	public Double getAgencyLatitude() {
		return agencyLatitude;
	}

	/**
	 * @param agencyLatitude the agencyLatitude to set
	 */
	public void setAgencyLatitude(Double agencyLatitude) {
		this.agencyLatitude = agencyLatitude;
	}

	/**
	 * @return the directions
	 */
	public List<Direction> getDirections() {
		return directions;
	}

	/**
	 * @param directions the directions to set
	 */
	public void setDirections(List<Direction> directions) {
		this.directions = directions;
	}

	@Override
	public int hashCode() {
		if (agencyId == -1) {
			return super.hashCode();
		}
		return HashCodeUtil.hash(HashCodeUtil.SEED, agencyId);
	}

	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof Agency) {
			result = (hashCode() == other.hashCode());
		}
		return result;
	}

	/**
	 * @return the agencyUsers
	 */
	public List<User> getAgencyUsers() {
		return agencyUsers;
	}

	/**
	 * @param agencyUsers the agencyUsers to set
	 */
	public void setAgencyUsers(List<User> agencyUsers) {
		this.agencyUsers = agencyUsers;
	}

	/**
	 * @return the agencyLanguage
	 */
	public String getAgencyLanguage() {
		return agencyLanguage;
	}

	/**
	 * @param agencyLanguage the agencyLanguage to set
	 */
	public void setAgencyLanguage(String agencyLanguage) {
		this.agencyLanguage = agencyLanguage;
	}
}
