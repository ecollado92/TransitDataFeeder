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
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.Length;
import org.ideaproject.Exportable;
import org.ideaproject.QueryNames;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "trip")
@NamedQueries({
	@NamedQuery(name = QueryNames.COUNT_AGENCY_TRIPS_ALL,
			query = "SELECT COUNT(*) FROM Trip trip INNER JOIN trip.route route WHERE route.agency = ?1"),
	@NamedQuery(name = QueryNames.COUNT_AGENCY_TRIPS_NO_STOPTIME,
			query = "SELECT COUNT(*) FROM Trip trip INNER JOIN trip.route route WHERE route.agency = ?1 " +
					"AND NOT EXISTS (SELECT 1 FROM trip.stopTimes) " +
					"AND NOT EXISTS (SELECT 1 FROM trip.basedOn.stopTimes)")
})
public class Trip extends AbstractLastModifiedEntity implements java.io.Serializable, Exportable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trip_id", unique = true, nullable = false)
	private int tripId;

	@Column(name = "trip_headsign", length = 35)
	@Length(max = 35)
	private String tripHeadsign;

	@Temporal(TemporalType.TIME)
	@Column(name = "trip_start_time", length = 15)
	private Date tripStartTime;

	@Column(name = "start_time_carryover")
	private boolean startTimeCarryover;

	@ManyToOne
	@JoinColumn(name = "trip_bikes_allowed", nullable = false)
	private BikeOption bikeOption;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "direction_id")
	private Direction direction;

	@ManyToOne
	@JoinColumn(name = "block_id")
	private Block block;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agency_id")
	private Agency agency;

	@ManyToOne
	@JoinColumn(name = "route_id", nullable = false)
	private Route route;

	@ManyToOne
	@JoinColumn(name = "service_id")
	private Calendar calendar;

	@ManyToOne
	@JoinColumn(name = "based_on")
	private Trip basedOn;

	@OneToMany(mappedBy="basedOn", cascade={CascadeType.REMOVE})
	private List<Trip> tripChildren = new ArrayList<Trip>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trip")
	@OrderBy("stopSequence")
	private List<StopTime> stopTimes = new ArrayList<StopTime>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trip", cascade = {CascadeType.REMOVE})
	private List<Frequency> frequencies = new ArrayList<Frequency>();

	@Transient
	private Integer startTimeHour;

	@Transient
	private Integer startTimeMinute;

	public Trip() {
	}

	public Trip(int tripId, Agency agency, Route route, Calendar calendar,
			Direction direction, BikeOption bikeOption) {
		this.tripId = tripId;
		this.agency = agency;
		this.route = route;
		this.calendar = calendar;
		this.direction = direction;
		this.bikeOption = bikeOption;
	}

	public Trip(int tripId, Agency agency, Route route, Calendar calendar,
			String tripHeadsign, Block block, Direction direction,
			Trip basedOn, Date tripStartTime, BikeOption bikeOption) {
		this.tripId = tripId;
		this.agency = agency;
		this.route = route;
		this.calendar = calendar;
		this.tripHeadsign = tripHeadsign;
		this.block = block;
		this.direction = direction;
		this.basedOn = basedOn;
		this.tripStartTime = tripStartTime;
		this.bikeOption = bikeOption;
	}

	public int getTripId() {
		return this.tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public Agency getAgency() {
		return this.agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public Route getRoute() {
		return this.route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Calendar getCalendar() {
		return this.calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public String getTripHeadsign() {
		return this.tripHeadsign;
	}

	public void setTripHeadsign(String tripHeadsign) {
		this.tripHeadsign = tripHeadsign;
	}

	public Block getBlock() {
		return this.block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Direction getDirection() {
		return this.direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Trip getBasedOn() {
		return this.basedOn;
	}

	public void setBasedOn(Trip basedOn) {
		this.basedOn = basedOn;
	}

	/**
	 * @return the tripChildren
	 */
	public List<Trip> getTripChildren() {
		return tripChildren;
	}

	/**
	 * @param tripChildren the tripChildren to set
	 */
	public void setTripChildren(List<Trip> tripChildren) {
		this.tripChildren = tripChildren;
	}

	public Date getTripStartTime() {
		return this.tripStartTime;
	}

	public void setTripStartTime(Date tripStartTime) {
		this.tripStartTime = tripStartTime;
	}

	public BikeOption getBikeOption() {
		return this.bikeOption;
	}

	public void setBikeOption(BikeOption bikeOption) {
		this.bikeOption = bikeOption;
	}

	/**
	 * @return the stopTimes
	 */
	public List<StopTime> getStopTimes() {
		return stopTimes;
	}

	/**
	 * @param stopTimes the stopTimes to set
	 */
	public void setStopTimes(List<StopTime> stopTimes) {
		this.stopTimes = stopTimes;
	}

	@Transient
	public StopTime getFirstStop() {
		return (stopTimes != null && !stopTimes.isEmpty()) ? stopTimes.get(0) : null;
	}

	public void setFirstStop(StopTime firstStop) {
		/* Modification to stopTimes list should NOT be performed here */
	}

	@Transient
	public StopTime getLastStop() {
		return (stopTimes != null && !stopTimes.isEmpty()) ? stopTimes.get(stopTimes.size() - 1) : null;
	}

	public void setLastStop(StopTime lastStop) {
		/* Modification to stopTimes list should NOT be performed here */
	}

	/**
	 * @return the frequencies
	 */
	public List<Frequency> getFrequencies() {
		return frequencies;
	}

	/**
	 * @param frequencies the frequencies to set
	 */
	public void setFrequencies(List<Frequency> frequencies) {
		this.frequencies = frequencies;
	}

	/**
	 * @return the firstStopTime
	 */
	public Date getFirstStopTime() {
		if (basedOn != null) {
			return tripStartTime;
		} else if (stopTimes != null && !stopTimes.isEmpty()){
			return stopTimes.get(0).getArrivalTime();
		}
		return new Date(0);
	}

	/**
	 * @return the lastStopTime
	 */
	public Date getLastStopTime() {
		StopTime lastSt;
		if (basedOn != null && !basedOn.getStopTimes().isEmpty()) {
			StopTime firstSt = basedOn.getStopTimes().get(0);
			lastSt = basedOn.getStopTimes().get(basedOn.getStopTimes().size() - 1);
			if (tripStartTime == null) {
				return lastSt.getArrivalTime();
			} else {
				long stopTimeInterval = lastSt.getArrivalTime().getTime() - firstSt.getArrivalTime().getTime();
				return new Date(tripStartTime.getTime() + stopTimeInterval);
			}
		} else if (!stopTimes.isEmpty()) {
			lastSt = stopTimes.get(stopTimes.size() - 1);
			return lastSt.getArrivalTime();
		}
		return new Date(0);
	}

	/**
	 * @return the startTimeCarryover
	 */
	public boolean isStartTimeCarryover() {
		return startTimeCarryover;
	}

	/**
	 * @param startTimeCarryover the startTimeCarryover to set
	 */
	public void setStartTimeCarryover(boolean startTimeCarryover) {
		this.startTimeCarryover = startTimeCarryover;
	}
	/**
	 * @return the startTimeHour
	 */
	public Integer getStartTimeHour() {
		return startTimeHour;
	}

	/**
	 * @param startTimeHour the startTimeHour to set
	 */
	public void setStartTimeHour(Integer startTimeHour) {
		if (this.startTimeHour != startTimeHour) {
			java.util.Calendar cal = getStartTimeCalendar();
			cal.set(java.util.Calendar.HOUR_OF_DAY, startTimeHour);
			tripStartTime = cal.getTime();
		}
		this.startTimeHour = startTimeHour;
	}

	/**
	 * @return the startTimeMinute
	 */
	public Integer getStartTimeMinute() {
		return startTimeMinute;
	}

	/**
	 * @param startTimeMinute the startTimeMinute to set
	 */
	public void setStartTimeMinute(Integer startTimeMinute) {
		if (this.startTimeMinute != startTimeMinute) {
			java.util.Calendar cal = getStartTimeCalendar();
			cal.set(java.util.Calendar.MINUTE, startTimeMinute);
			tripStartTime = cal.getTime();
		}
		this.startTimeMinute = startTimeMinute;
	}


	@PostLoad
	public void splitTripStartTime() {
		if (tripStartTime != null) {
			java.util.Calendar startTimeCal = getStartTimeCalendar();
			startTimeHour = startTimeCal.get(java.util.Calendar.HOUR_OF_DAY);
			startTimeMinute = startTimeCal.get(java.util.Calendar.MINUTE);
		}
	}

	private java.util.Calendar getStartTimeCalendar() {
		java.util.Calendar result = java.util.Calendar.getInstance();
		if (tripStartTime != null) {
			result.setTime(tripStartTime);
		}
		return result;
	}

}
