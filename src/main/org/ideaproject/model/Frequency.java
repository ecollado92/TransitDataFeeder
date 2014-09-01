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

import org.hibernate.validator.NotNull;
import org.ideaproject.Exportable;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "frequency")
public class Frequency extends AbstractLastModifiedEntity implements java.io.Serializable, Exportable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "frequency_id", unique = true, nullable = false)
	private int frequencyId;

	@Temporal(TemporalType.TIME)
	@Column(name = "start_time", nullable = false, length = 15)
	@NotNull
	private Date startTime = new Date(0);

	@Column(name = "start_time_carryover")
	private boolean startTimeCarryover;

	@Temporal(TemporalType.TIME)
	@Column(name = "end_time", nullable = false, length = 15)
	@NotNull
	private Date endTime = new Date(0);

	@Column(name = "end_time_carryover")
	private boolean endTimeCarryover;

	@Column(name = "headway_secs", nullable = false)
	private int headwaySecs;

	@Column(name = "exact_times", nullable = false)
	private boolean exactTimes;

	@ManyToOne
	@JoinColumn(name = "trip_id")
	private Trip trip;

	@Transient
	private int startTimeHour;

	@Transient
	private int startTimeMinute;

	@Transient
	private int endTimeHour;

	@Transient
	private int endTimeMinute;

	public Frequency() {
	}

	public Frequency(int frequencyId, Trip trip, Date startTime,
			Date endTime, int headwaySecs, boolean exactTimes) {
		this.frequencyId = frequencyId;
		this.trip = trip;
		this.startTime = startTime;
		this.endTime = endTime;
		this.headwaySecs = headwaySecs;
		this.exactTimes = exactTimes;
	}

	public int getFrequencyId() {
		return this.frequencyId;
	}

	public void setFrequencyId(int frequencyId) {
		this.frequencyId = frequencyId;
	}

	public Trip getTrip() {
		return this.trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getHeadwaySecs() {
		return this.headwaySecs;
	}

	public void setHeadwaySecs(int headwaySecs) {
		this.headwaySecs = headwaySecs;
	}

	public boolean isExactTimes() {
		return this.exactTimes;
	}

	public void setExactTimes(boolean exactTimes) {
		this.exactTimes = exactTimes;
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
	 * @return the endTimeCarryover
	 */
	public boolean isEndTimeCarryover() {
		return endTimeCarryover;
	}

	/**
	 * @param endTimeCarryover the endTimeCarryover to set
	 */
	public void setEndTimeCarryover(boolean endTimeCarryover) {
		this.endTimeCarryover = endTimeCarryover;
	}

	/**
	 * @return the startTimeHour
	 */
	public int getStartTimeHour() {
		return startTimeHour;
	}

	/**
	 * @param startTimeHour the startTimeHour to set
	 */
	public void setStartTimeHour(int startTimeHour) {
		if (this.startTimeHour != startTimeHour) {
			java.util.Calendar cal = getStartTimeCalendar();
			cal.set(java.util.Calendar.HOUR_OF_DAY, startTimeHour);
			startTime = cal.getTime();
		}
		this.startTimeHour = startTimeHour;
	}

	/**
	 * @return the startTimeMinute
	 */
	public int getStartTimeMinute() {
		return startTimeMinute;
	}

	/**
	 * @param startTimeMinute the startTimeMinute to set
	 */
	public void setStartTimeMinute(int startTimeMinute) {
		if (this.startTimeMinute != startTimeMinute) {
			java.util.Calendar cal = getStartTimeCalendar();
			cal.set(java.util.Calendar.MINUTE, startTimeMinute);
			endTime = cal.getTime();
		}
		this.startTimeMinute = startTimeMinute;
	}

	/**
	 * @return the endTimeHour
	 */
	public int getEndTimeHour() {
		return endTimeHour;
	}

	/**
	 * @param endTimeHour the endTimeHour to set
	 */
	public void setEndTimeHour(int endTimeHour) {
		if (this.endTimeHour != endTimeHour) {
			java.util.Calendar cal = getEndTimeCalendar();
			cal.set(java.util.Calendar.HOUR_OF_DAY, endTimeHour);
			endTime = cal.getTime();
		}
		this.endTimeHour = endTimeHour;
	}

	/**
	 * @return the endTimeMinute
	 */
	public int getEndTimeMinute() {
		return endTimeMinute;
	}

	/**
	 * @param endTimeMinute the endTimeMinute to set
	 */
	public void setEndTimeMinute(int endTimeMinute) {
		if (this.endTimeMinute != endTimeMinute) {
			java.util.Calendar cal = getEndTimeCalendar();
			cal.set(java.util.Calendar.MINUTE, endTimeMinute);
			endTime = cal.getTime();
		}
		this.endTimeMinute = endTimeMinute;
	}

	@PostLoad
	public void splitStartAndEndTimes() {
		if (startTime != null) {
			java.util.Calendar startTimeCal = getStartTimeCalendar();
			startTimeHour = startTimeCal.get(java.util.Calendar.HOUR_OF_DAY);
			startTimeMinute = startTimeCal.get(java.util.Calendar.MINUTE);
		}
		if (endTime != null) {
			java.util.Calendar endTimeCal = getEndTimeCalendar();
			endTimeHour = endTimeCal.get(java.util.Calendar.HOUR_OF_DAY);
			endTimeMinute = endTimeCal.get(java.util.Calendar.MINUTE);
		}
	}

	private java.util.Calendar getStartTimeCalendar() {
		java.util.Calendar result = java.util.Calendar.getInstance();
		if (startTime != null) {
			result.setTime(startTime);
		}
		return result;
	}


	private java.util.Calendar getEndTimeCalendar() {
		java.util.Calendar result = java.util.Calendar.getInstance();
		if (endTime != null) {
			result.setTime(endTime);
		}
		return result;
	}

}
