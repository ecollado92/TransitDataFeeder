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
package org.ideaproject.action.entityhome;

import org.ideaproject.model.Calendar;
import org.ideaproject.model.CalendarDate;
import org.ideaproject.model.CalendarDateException;
import org.ideaproject.model.ServiceScheduleGroup;
import org.ideaproject.model.Trip;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Seam EntityHome for Calendars
 * @author dirk
 * @see org.ideaproject.action.entityhome.AbstractLastModifiedEntityHome
 */
@Name("calendarHome")
public class CalendarHome extends AbstractLastModifiedEntityHome<Calendar> {

	private static final long serialVersionUID = 1L;

	/**
	 * The entity home from which we can determine the current instance's
	 * service schedule group association.
	 */
	@In(create = true)
	ServiceScheduleGroupHome serviceScheduleGroupHome;

	@In(create = true)
	TripHome tripHome;

	@In(create = true)
	CalendarDateHome calendarDateHome;

	@In(create = true)
	CalendarDateExceptionHome calendarDateExceptionHome;

	/**
	 * Sets the current calendar date by ID; side effect updates the
	 * instance.
	 * @param id the bike option ID
	 */
	public void setCalendarCalendarId(Integer id) {
		setId(id);
	}

	/**
	 * @return the ID of the current calendar.
	 */
	public Integer getCalendarCalendarId() {
		return (Integer) getId();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Calendar createInstance() {
		Calendar calendar = new Calendar();
		return calendar;
	}

	/**
	 * Loads the current calendar.
	 */
	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	/**
	 * "Wires" the current instance
	 */
	public void wire() {
		getInstance();
		ServiceScheduleGroup serviceScheduleGroup = serviceScheduleGroupHome
				.getDefinedInstance();
		if (serviceScheduleGroup != null) {
			getInstance().setServiceScheduleGroup(serviceScheduleGroup);
		}
	}

	/**
	 * @return whether or not the current calendar is "wired"
	 * @see #wire()
	 */
	public boolean isWired() {
		return true;
	}

	/**
	 * Gets the currently defined instance; may return null if the ID for this
	 * EntityHome is not supplied.
	 * @return the currently-defined instance, or null if none is defined.
	 */
	public Calendar getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.EntityHome#remove()
	 */
	@Override
	public String remove() {
		Integer originalTripId = tripHome.getTripTripId();
		Integer originalCalendarDateId = calendarDateHome.getCalendarDateCalendarDateId();
		Integer originalCalendarExceptionId = calendarDateExceptionHome.getCalendarDateExceptionCalendarDateExceptionId();
		try {
			for (Trip trip : getInstance().getTrips()) {
				tripHome.setInstance(trip);
				tripHome.remove();
			}
			for (CalendarDate calendarDate : getInstance().getCalendarDatesAdded()) {
				calendarDateHome.setInstance(calendarDate);
				calendarDateHome.getInstance().setServiceAdded(null);
				calendarDateHome.update();
			}
			for (CalendarDate calendarDate : getInstance().getCalendarDatesRemoved()) {
				calendarDateHome.setInstance(calendarDate);
				calendarDateHome.getInstance().setServiceRemoved(null);
				calendarDateHome.update();
			}
			for (CalendarDateException exception : getInstance().getCalendarDateExceptions()) {
				calendarDateExceptionHome.setInstance(exception);
				calendarDateExceptionHome.remove();
			}
		} finally {
			tripHome.setTripTripId(originalTripId);
			calendarDateHome.setCalendarDateCalendarDateId(originalCalendarDateId);
			calendarDateExceptionHome.setCalendarDateExceptionCalendarDateExceptionId(originalCalendarExceptionId);
		}
		return super.remove();
	}
}
