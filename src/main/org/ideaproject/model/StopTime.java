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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.ideaproject.Exportable;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "stop_time")
public class StopTime extends AbstractAgencyBasedEntity implements java.io.Serializable, Exportable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stop_time_id", unique = true, nullable = false)
	private int stopTimeId;

	@Temporal(TemporalType.TIME)
	@Column(name = "arrival_time", length = 15)
	private Date arrivalTime = new Date(0);

	@Column(name = "arrival_time_carryover")
	private boolean arrivalTimeCarryover;

	@Temporal(TemporalType.TIME)
	@Column(name = "departure_time", length = 15)
	private Date departureTime = new Date(0);

	@Column(name = "departure_time_carryover")
	private boolean departureTimeCarryover;

	@Column(name = "stop_sequence", nullable = false)
	private short stopSequence;

	@Column(name = "shape_dist_traveled", precision = 17, scale = 17)
	private Double shapeDistTraveled;

	@Column(name = "timing_point")
	private Boolean timingPoint;

	@ManyToOne
	@JoinColumn(name = "pickup_type_id")
	private PickupType pickupType;

	@ManyToOne
	@JoinColumn(name = "drop_off_type_id")
	private PickupType dropOffType;

	@ManyToOne
	@JoinColumn(name = "trip_id")
	private Trip trip;

	@ManyToOne
	@JoinColumn(name = "stop_id")
	private Stop stop;

	@Transient
	private int arrivalTimeHour;

	@Transient
	private int arrivalTimeMinute;

	@Transient
	private int departureTimeHour;

	@Transient
	private int departureTimeMinute;

	public StopTime() {
	}

	public StopTime(int stopTimeId, Agency agency, Trip trip,
			Stop stop, short stopSequence) {
		this.stopTimeId = stopTimeId;
		this.trip = trip;
		this.stop = stop;
		this.stopSequence = stopSequence;
		setAgency(agency);
	}

	public StopTime(int stopTimeId, Agency agency, Trip trip,
			Date arrivalTime, Date departureTime, Stop stop,
			short stopSequence, PickupType pickupType,
			PickupType dropOffType, Double shapeDistTraveled, Boolean timingPoint) {
		this.stopTimeId = stopTimeId;
		this.trip = trip;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.stop = stop;
		this.stopSequence = stopSequence;
		this.pickupType = pickupType;
		this.dropOffType = dropOffType;
		this.shapeDistTraveled = shapeDistTraveled;
		this.timingPoint = timingPoint;
		setAgency(agency);
	}

	public int getStopTimeId() {
		return this.stopTimeId;
	}

	public void setStopTimeId(int stopTimeId) {
		this.stopTimeId = stopTimeId;
	}

	public Trip getTrip() {
		return this.trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public Date getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Date getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Stop getStop() {
		return this.stop;
	}

	public void setStop(Stop stop) {
		this.stop = stop;
	}

	public short getStopSequence() {
		return this.stopSequence;
	}

	public void setStopSequence(short stopSequence) {
		this.stopSequence = stopSequence;
	}

	public PickupType getPickupType() {
		return this.pickupType;
	}

	public void setPickupType(PickupType pickupType) {
		this.pickupType = pickupType;
	}

	public PickupType getDropOffType() {
		return this.dropOffType;
	}

	public void setDropOffType(PickupType dropOffType) {
		this.dropOffType = dropOffType;
	}

	public Double getShapeDistTraveled() {
		return this.shapeDistTraveled;
	}

	public void setShapeDistTraveled(Double shapeDistTraveled) {
		this.shapeDistTraveled = shapeDistTraveled;
	}

	public Boolean getTimingPoint() {
		return this.timingPoint;
	}

	public void setTimingPoint(Boolean timingPoint) {
		this.timingPoint = timingPoint;
	}

	/**
	 * @return the arrivalTimeCarryover
	 */
	public boolean isArrivalTimeCarryover() {
		return arrivalTimeCarryover;
	}

	/**
	 * @param arrivalTimeCarryover the arrivalTimeCarryover to set
	 */
	public void setArrivalTimeCarryover(boolean arrivalTimeCarryover) {
		this.arrivalTimeCarryover = arrivalTimeCarryover;
	}

	/**
	 * @return the departureTimeCarryover
	 */
	public boolean isDepartureTimeCarryover() {
		return departureTimeCarryover;
	}

	/**
	 * @param departureTimeCarryover the departureTimeCarryover to set
	 */
	public void setDepartureTimeCarryover(boolean departureTimeCarryover) {
		this.departureTimeCarryover = departureTimeCarryover;
	}

	/**
	 * @return the arrivalTimeHour
	 */
	public int getArrivalTimeHour() {
		return arrivalTimeHour;
	}

	/**
	 * @param arrivalTimeHour the arrivalTimeHour to set
	 */
	public void setArrivalTimeHour(int arrivalTimeHour) {
		if (this.arrivalTimeHour != arrivalTimeHour) {
			java.util.Calendar cal = getArrivalTimeCalendar();
			cal.set(java.util.Calendar.HOUR_OF_DAY, arrivalTimeHour);
			arrivalTime = cal.getTime();
		}
		this.arrivalTimeHour = arrivalTimeHour;
	}

	/**
	 * @return the arrivalTimeMinute
	 */
	public int getArrivalTimeMinute() {
		return arrivalTimeMinute;
	}

	/**
	 * @param arrivalTimeMinute the arrivalTimeMinute to set
	 */
	public void setArrivalTimeMinute(int arrivalTimeMinute) {
		if (this.arrivalTimeMinute != arrivalTimeMinute) {
			java.util.Calendar cal = getArrivalTimeCalendar();
			cal.set(java.util.Calendar.MINUTE, arrivalTimeMinute);
			arrivalTime = cal.getTime();
		}
		this.arrivalTimeMinute = arrivalTimeMinute;
	}

	/**
	 * @return the departureTimeHour
	 */
	public int getDepartureTimeHour() {
		return departureTimeHour;
	}

	/**
	 * @param departureTimeHour the departureTimeHour to set
	 */
	public void setDepartureTimeHour(int departureTimeHour) {
		if (this.departureTimeHour != departureTimeHour) {
			java.util.Calendar cal = getDepartureTimeCalendar();
			cal.set(java.util.Calendar.HOUR_OF_DAY, departureTimeHour);
			departureTime = cal.getTime();
		}
		this.departureTimeHour = departureTimeHour;
	}

	/**
	 * @return the departureTimeMinute
	 */
	public int getDepartureTimeMinute() {
		return departureTimeMinute;
	}

	/**
	 * @param departureTimeMinute the departureTimeMinute to set
	 */
	public void setDepartureTimeMinute(int departureTimeMinute) {
		if (this.departureTimeMinute != departureTimeMinute) {
			java.util.Calendar cal = getDepartureTimeCalendar();
			cal.set(java.util.Calendar.MINUTE, departureTimeMinute);
			departureTime = cal.getTime();
		}
		this.departureTimeMinute = departureTimeMinute;
	}

	@PostLoad
	public void splitArrivalAndDepartureTimes() {
		if (arrivalTime != null) {
			java.util.Calendar arrivalTimeCal = java.util.Calendar.getInstance();
			arrivalTimeCal.setTime(arrivalTime);
			arrivalTimeHour = arrivalTimeCal.get(java.util.Calendar.HOUR_OF_DAY);
			arrivalTimeMinute = arrivalTimeCal.get(java.util.Calendar.MINUTE);
		}
		if (departureTime != null) {
			java.util.Calendar departureTimeCal = java.util.Calendar.getInstance();
			departureTimeCal.setTime(departureTime);
			departureTimeHour = departureTimeCal.get(java.util.Calendar.HOUR_OF_DAY);
			departureTimeMinute = departureTimeCal.get(java.util.Calendar.MINUTE);
		}
	}

	private java.util.Calendar getArrivalTimeCalendar() {
		java.util.Calendar result = java.util.Calendar.getInstance();
		if (arrivalTime != null) {
			result.setTime(arrivalTime);
		}
		return result;
	}


	private java.util.Calendar getDepartureTimeCalendar() {
		java.util.Calendar result = java.util.Calendar.getInstance();
		if (departureTime != null) {
			result.setTime(departureTime);
		}
		return result;
	}

}
