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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.NotNull;
import org.ideaproject.Exportable;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "calendar_date")
public class CalendarDate extends AbstractAgencyBasedEntity implements java.io.Serializable, Exportable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "calendar_date_id", unique = true, nullable = false)
	private int calendarDateId;

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false, length = 13)
	@NotNull
	private Date date;

	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "service_added")
	private Calendar serviceAdded;

	@ManyToOne
	@JoinColumn(name = "service_removed")
	private Calendar serviceRemoved;

	@ManyToOne
	@JoinColumn(name = "exception_type")
	private CalendarExceptionType exceptionType;

	@OneToMany(mappedBy = "calendarDate", cascade = {CascadeType.REMOVE})
	private List<CalendarDateException> calendarDateExceptions = new ArrayList<CalendarDateException>();

	public CalendarDate() {
	}

	public CalendarDate(int calendarDateId, Date date, CalendarExceptionType exceptionType,
			Agency agency) {
		this.calendarDateId = calendarDateId;
		this.date = date;
		this.exceptionType = exceptionType;
		setAgency(agency);
	}

	public CalendarDate(int calendarDateId, Date date, Calendar serviceAdded,
			Calendar serviceRemoved, CalendarExceptionType exceptionType, Agency agency,
			String description) {
		this.calendarDateId = calendarDateId;
		this.date = date;
		this.serviceAdded = serviceAdded;
		this.serviceRemoved = serviceRemoved;
		this.exceptionType = exceptionType;
		this.description = description;
		setAgency(agency);
	}

	public int getCalendarDateId() {
		return this.calendarDateId;
	}

	public void setCalendarDateId(int calendarDateId) {
		this.calendarDateId = calendarDateId;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Calendar getServiceAdded() {
		return this.serviceAdded;
	}

	public void setServiceAdded(Calendar serviceAdded) {
		this.serviceAdded = serviceAdded;
	}

	public Calendar getServiceRemoved() {
		return this.serviceRemoved;
	}

	public void setServiceRemoved(Calendar serviceRemoved) {
		this.serviceRemoved = serviceRemoved;
	}

	public CalendarExceptionType getExceptionType() {
		return this.exceptionType;
	}

	public void setExceptionType(CalendarExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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
