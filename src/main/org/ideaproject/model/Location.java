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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.ideaproject.Exportable;

/**
 * Base entity for (currently) {@link org.ideaproject.model.Station} and {@link org.ideaproject.model.Stop}.
 * Defines storage for common primitives, but leaves relationships to the mapped superclass
 * {@link org.ideaproject.model.BaseLocation} which exposes them for bidirectionality.
 * 
 * @author dirk
 * @see org.ideaproject.model.BaseLocation
 * @see org.ideaproject.model.Station
 * @see org.ideaproject.model.Stop
 *
 */
@Entity
@Table(name = "stop")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "location_type", discriminatorType = DiscriminatorType.INTEGER)
@org.hibernate.annotations.ForceDiscriminator
public abstract class Location implements java.io.Serializable, Exportable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "stop_id", unique = true, nullable = false)
	private int stopId;

	@Column(name = "stop_name", nullable = false, length = 80)
	@NotNull
	@Length(max = 80)
	private String stopName;

	@Column(name = "stop_code", length = 18)
	@Length(max = 18)
	private String stopCode;

	@Column(name = "stop_desc")
	private String stopDesc;

	@Column(name = "stop_comments", length = 200)
	@Length(max = 200)
	private String stopComments;

	@Column(name = "stop_lat", precision = 17, scale = 17)
	private Double stopLat;

	@Column(name = "stop_lon", precision = 17, scale = 17)
	private Double stopLon;

	@Column(name = "stop_url")
	private String stopUrl;

	@org.hibernate.annotations.Formula("(stop_lat is not null and stop_lon is not null)")
	private boolean geolocated;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="toLocation", cascade = {CascadeType.REMOVE})
	private List<Transfer> transfersToLocation;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="fromLocation", cascade = {CascadeType.REMOVE})
	private List<Transfer> transfersFromLocation;

	public Location() {
	}

	public Location(int stopId, String stopName, String stopDesc) {
		this.stopId = stopId;
		this.stopName = stopName;
		this.stopDesc = stopDesc;
	}

	public Location(int stopId, String stopName, String stopDesc, 
			String stopCode, String stopComments, Double stopLat,
			Double stopLon, String stopUrl) {
		this.stopId = stopId;
		this.stopName = stopName;
		this.stopCode = stopCode;
		this.stopDesc = stopDesc;
		this.stopComments = stopComments;
		this.stopLat = stopLat;
		this.stopLon = stopLon;
		this.stopUrl = stopUrl;
	}

	public int getStopId() {
		return this.stopId;
	}

	public void setStopId(int stopId) {
		this.stopId = stopId;
	}

	public String getStopName() {
		return this.stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public String getStopCode() {
		return this.stopCode;
	}

	public void setStopCode(String stopCode) {
		this.stopCode = stopCode;
	}

	public String getStopDesc() {
		return this.stopDesc;
	}

	public void setStopDesc(String stopDesc) {
		this.stopDesc = stopDesc;
	}

	public String getStopComments() {
		return this.stopComments;
	}

	public void setStopComments(String stopComments) {
		this.stopComments = stopComments;
	}

	public Double getStopLat() {
		return this.stopLat;
	}

	public void setStopLat(Double stopLat) {
		this.stopLat = stopLat;
	}

	public Double getStopLon() {
		return this.stopLon;
	}

	public void setStopLon(Double stopLon) {
		this.stopLon = stopLon;
	}

	public String getStopUrl() {
		return this.stopUrl;
	}

	public void setStopUrl(String stopUrl) {
		this.stopUrl = stopUrl;
	}

	/**
	 * @return the geolocated
	 */
	public boolean isGeolocated() {
		return geolocated;
	}

	/**
	 * @param geolocated the geolocated to set
	 */
	public void setGeolocated(boolean geolocated) {
		this.geolocated = geolocated;
	}

	/**
	 * @return the transfersToLocation
	 */
	public List<Transfer> getTransfersToLocation() {
		return transfersToLocation;
	}

	/**
	 * @param transfersToLocation the transfersToLocation to set
	 */
	public void setTransfersToLocation(List<Transfer> transfersToLocation) {
		this.transfersToLocation = transfersToLocation;
	}

	/**
	 * @return the transfersFromStop
	 */
	public List<Transfer> getTransfersFromLocation() {
		return transfersFromLocation;
	}

	/**
	 * @param transfersFromLocation the transfersFromLocation to set
	 */
	public void setTransfersFromLocation(List<Transfer> transfersFromLocation) {
		this.transfersFromLocation = transfersFromLocation;
	}
}
