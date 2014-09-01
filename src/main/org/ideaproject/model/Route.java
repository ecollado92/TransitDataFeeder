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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.ideaproject.Constants;
import org.ideaproject.Exportable;
import org.ideaproject.QueryNames;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "route")
@NamedQueries({
	@NamedQuery(name = QueryNames.COUNT_AGENCY_ROUTES_ALL,
			query = "SELECT COUNT(*) FROM Route route WHERE route.agency = ?1"),
	@NamedQuery(name = QueryNames.COUNT_AGENCY_ROUTES_NO_TRIPS,
			query = "SELECT COUNT(*) FROM Route route WHERE route.agency = ?1 " +
					"AND NOT EXISTS (SELECT 1 FROM route.trips)")
})
public class Route extends AbstractAgencyBasedEntity implements java.io.Serializable, Exportable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "route_id", unique = true, nullable = false)
	private int routeId;

	@Column(name = "route_short_name", nullable = false, length = 15)
	@NotNull
	@Length(max = 15)
	private String routeShortName = Constants.EMPTY;

	@Column(name = "route_long_name", nullable = false, length = 40)
	@NotNull
	@Length(max = 40)
	private String routeLongName = Constants.EMPTY;

	@Column(name = "route_desc")
	private String routeDesc;

	@Column(name = "route_color", length = 7)
	@Length(max = 7)
	private String routeColor;

	@Column(name = "route_text_color", length = 7)
	@Length(max = 7)
	private String routeTextColor;

	@Column(name = "route_url")
	private String routeUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "route_bikes_allowed", nullable = false)
	private BikeOption bikeOption;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "route_type", nullable = false)
	private RouteType routeType;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="route", cascade = {CascadeType.ALL})
	@OrderBy("tripStartTime")
	private List<Trip> trips = new ArrayList<Trip>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy="route")
	private List<FareRule> fareRules = new ArrayList<FareRule>();

	@ManyToMany(fetch = FetchType.LAZY, mappedBy="routes")
	private List<ShapeSegment> shapeSegments;

	public Route() {
	}

	public Route(int routeId, Agency agency, String routeShortName,
			String routeLongName, RouteType routeType, BikeOption bikeOption) {
		this.routeId = routeId;
		this.routeShortName = routeShortName;
		this.routeLongName = routeLongName;
		this.routeType = routeType;
		this.bikeOption = bikeOption;
		setAgency(agency);
	}

	public Route(int routeId, Agency agency, String routeShortName,
			String routeLongName, String routeDesc, RouteType routeType,
			String routeColor, String routeTextColor, String routeUrl,
			BikeOption bikeOption) {
		this.routeId = routeId;
		this.routeShortName = routeShortName;
		this.routeLongName = routeLongName;
		this.routeDesc = routeDesc;
		this.routeType = routeType;
		this.routeColor = routeColor;
		this.routeTextColor = routeTextColor;
		this.routeUrl = routeUrl;
		this.bikeOption = bikeOption;
		setAgency(agency);
	}

	public int getRouteId() {
		return this.routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public String getRouteShortName() {
		return this.routeShortName;
	}

	public void setRouteShortName(String routeShortName) {
		this.routeShortName = routeShortName;
	}

	public String getRouteLongName() {
		return this.routeLongName;
	}

	public void setRouteLongName(String routeLongName) {
		this.routeLongName = routeLongName;
	}

	@Transient
	public String getAbbreviatedRouteDesc() {
		String result = routeDesc;
		if (result != null && result.length() > 40) {
			result = result.substring(0, 40);
			int spaceIndex = result.lastIndexOf(' ');
			if (spaceIndex >= 0) {
				result = result.substring(0, spaceIndex);
			}
			result = result + "...";
		} 
		return result;
	}

	public String getRouteDesc() {
		return this.routeDesc;
	}

	public void setRouteDesc(String routeDesc) {
		this.routeDesc = routeDesc;
	}

	public RouteType getRouteType() {
		return this.routeType;
	}

	public void setRouteType(RouteType routeType) {
		this.routeType = routeType;
	}

	public String getRouteColor() {
		return this.routeColor;
	}

	public void setRouteColor(String routeColor) {
		this.routeColor = routeColor;
	}

	public String getRouteTextColor() {
		return this.routeTextColor;
	}

	public void setRouteTextColor(String routeTextColor) {
		this.routeTextColor = routeTextColor;
	}

	public String getRouteUrl() {
		return this.routeUrl;
	}

	public void setRouteUrl(String routeUrl) {
		this.routeUrl = routeUrl;
	}

	public BikeOption getBikeOption() {
		return this.bikeOption;
	}

	public void setBikeOption(BikeOption bikeOption) {
		this.bikeOption = bikeOption;
	}

	/**
	 * @return the trips
	 */
	public List<Trip> getTrips() {
		return trips;
	}

	/**
	 * @param trips the trips to set
	 */
	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}

	/**
	 * @return the shapeSegments
	 */
	public List<ShapeSegment> getShapeSegments() {
		return shapeSegments;
	}

	/**
	 * @param shapeSegments the shapeSegments to set
	 */
	public void setShapeSegments(List<ShapeSegment> shapeSegments) {
		this.shapeSegments = shapeSegments;
	}

	/**
	 * @return the fareRules
	 */
	public List<FareRule> getFareRules() {
		return fareRules;
	}

	/**
	 * @param fareRules the fareRules to set
	 */
	public void setFareRules(List<FareRule> fareRules) {
		this.fareRules = fareRules;
	}
}
