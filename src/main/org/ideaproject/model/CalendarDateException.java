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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "calendar_date_exception")
public class CalendarDateException extends AbstractLastModifiedEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "calendar_date_exception_id", unique = true, nullable = false)
	private int calendarDateExceptionId;

	@ManyToOne
	@JoinColumn(name = "calendar_date_id", nullable = false)
	private CalendarDate calendarDate;

	@ManyToOne
	@JoinColumn(name = "exception_type", nullable = false)
	private CalendarExceptionType exceptionType;

	@ManyToOne
	@JoinColumn(name = "service_exception", nullable = false)
	private Calendar serviceException;

	public CalendarDateException() {
	}

	public CalendarDateException(int calendarDateExceptionId,
			CalendarDate calendarDate, CalendarExceptionType exceptionType,
			Calendar serviceException) {
		this.calendarDateExceptionId = calendarDateExceptionId;
		this.calendarDate = calendarDate;
		this.exceptionType = exceptionType;
		this.serviceException = serviceException;
	}

	public int getCalendarDateExceptionId() {
		return this.calendarDateExceptionId;
	}

	public void setCalendarDateExceptionId(int calendarDateExceptionId) {
		this.calendarDateExceptionId = calendarDateExceptionId;
	}

	public CalendarDate getCalendarDate() {
		return this.calendarDate;
	}

	public void setCalendarDate(CalendarDate calendarDate) {
		this.calendarDate = calendarDate;
	}

	public CalendarExceptionType getExceptionType() {
		return this.exceptionType;
	}

	public void setExceptionType(CalendarExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}

	public Calendar getServiceException() {
		return this.serviceException;
	}

	public void setServiceException(Calendar serviceException) {
		this.serviceException = serviceException;
	}

}
