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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.ideaproject.Exportable;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "calendar")
public class Calendar extends AbstractLastModifiedEntity implements java.io.Serializable, Exportable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "calendar_id", unique = true, nullable = false)
	private int calendarId;

	@Column(name = "service_label", nullable = false, length = 50)
	@NotNull
	@Length(max = 50)
	private String serviceLabel;

	@Column(name = "monday", nullable = false)
	private boolean monday;
	
	@Column(name = "tuesday", nullable = false)
	private boolean tuesday;

	@Column(name = "wednesday", nullable = false)
	private boolean wednesday;

	@Column(name = "thursday", nullable = false)
	private boolean thursday;

	@Column(name = "friday", nullable = false)
	private boolean friday;

	@Column(name = "saturday", nullable = false)
	private boolean saturday;

	@Column(name = "sunday", nullable = false)
	private boolean sunday;

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date", length = 13)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "end_date", length = 13)
	private Date endDate;

	@OneToMany(mappedBy = "calendar")
	private List<Trip> trips = new ArrayList<Trip>();

	@OneToMany(mappedBy = "serviceRemoved")
	private List<CalendarDate> calendarDatesRemoved = new ArrayList<CalendarDate>();

	@OneToMany(mappedBy = "serviceAdded")
	private List<CalendarDate> calendarDatesAdded = new ArrayList<CalendarDate>();

	@OneToMany(mappedBy = "serviceException")
	private List<CalendarDateException> calendarDateExceptions = new ArrayList<CalendarDateException>();

	@ManyToOne
	@JoinColumn(name = "service_schedule_group_id")
	private ServiceScheduleGroup serviceScheduleGroup;

	@Column(name = "service_id")
	private Integer serviceId;

	public Calendar() {
	}

	public Calendar(int calendarId, String serviceLabel, Integer serviceId,
			boolean monday, boolean tuesday, boolean wednesday,
			boolean thursday, boolean friday, boolean saturday, boolean sunday,
			Date startDate, Date endDate,
			ServiceScheduleGroup serviceScheduleGroup) {
		this.calendarId = calendarId;
		this.serviceLabel = serviceLabel;
		this.serviceId = serviceId;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.startDate = startDate;
		this.endDate = endDate;
		this.serviceScheduleGroup = serviceScheduleGroup;
	}

	public int getCalendarId() {
		return this.calendarId;
	}

	public void setCalendarId(int calendarId) {
		this.calendarId = calendarId;
	}

	public String getServiceLabel() {
		return this.serviceLabel;
	}

	public void setServiceLabel(String serviceLabel) {
		this.serviceLabel = serviceLabel;
	}

	public Integer getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public boolean isMonday() {
		return this.monday;
	}

	public void setMonday(boolean monday) {
		this.monday = monday;
	}

	public boolean isTuesday() {
		return this.tuesday;
	}

	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}

	public boolean isWednesday() {
		return this.wednesday;
	}

	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}

	public boolean isThursday() {
		return this.thursday;
	}

	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}

	public boolean isFriday() {
		return this.friday;
	}

	public void setFriday(boolean friday) {
		this.friday = friday;
	}

	public boolean isSaturday() {
		return this.saturday;
	}

	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}

	public boolean isSunday() {
		return this.sunday;
	}

	public void setSunday(boolean sunday) {
		this.sunday = sunday;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ServiceScheduleGroup getServiceScheduleGroup() {
		return this.serviceScheduleGroup;
	}

	public void setServiceScheduleGroup(ServiceScheduleGroup serviceScheduleGroup) {
		this.serviceScheduleGroup = serviceScheduleGroup;
	}

	/**
	 * @return the applicable
	 */
	public boolean isApplicable() {
		switch (java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK)) {
		case java.util.Calendar.MONDAY:
			return monday;
		case java.util.Calendar.TUESDAY:
			return tuesday;
		case java.util.Calendar.WEDNESDAY:
			return wednesday;
		case java.util.Calendar.THURSDAY:
			return thursday;
		case java.util.Calendar.FRIDAY:
			return friday;
		case java.util.Calendar.SATURDAY:
			return saturday;
		case java.util.Calendar.SUNDAY:
			return sunday;
		default:
			return false;
		}
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

	@Transient
	public int getTripCount() {
		int result = 0;
		List<Trip> _trips = getTrips();
		if (_trips != null && !_trips.isEmpty()) {
			result = _trips.size();
		}
		return result;
	}
	
	public void setTripCount(int tripCount) {
		/* read-only, calculated field; this method is to support JSF only */
	}

	/**
	 * @return the calendarDatesRemoved
	 */
	public List<CalendarDate> getCalendarDatesRemoved() {
		return calendarDatesRemoved;
	}

	/**
	 * @param calendarDatesRemoved the calendarDatesRemoved to set
	 */
	public void setCalendarDatesRemoved(List<CalendarDate> calendarDatesRemoved) {
		this.calendarDatesRemoved = calendarDatesRemoved;
	}

	/**
	 * @return the calendarDatesAdded
	 */
	public List<CalendarDate> getCalendarDatesAdded() {
		return calendarDatesAdded;
	}

	/**
	 * @param calendarDatesAdded the calendarDatesAdded to set
	 */
	public void setCalendarDatesAdded(List<CalendarDate> calendarDatesAdded) {
		this.calendarDatesAdded = calendarDatesAdded;
	}

	/**
	 * @return the calendarDateExceptions
	 */
	public List<CalendarDateException> getCalendarDateExceptions() {
		return calendarDateExceptions;
	}

	/**
	 * @param calendarDateExceptions the calendarDateExceptions to set
	 */
	public void setCalendarDateExceptions(
			List<CalendarDateException> calendarDateExceptions) {
		this.calendarDateExceptions = calendarDateExceptions;
	}

}
