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
package org.ideaproject.export.gtfs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.ideaproject.Constants;
import org.ideaproject.model.Calendar;
import org.ideaproject.model.CalendarDate;
import org.ideaproject.model.CalendarDateException;

/**
 * @author dirk
 *
 */
public class CalendarDateGtfsExporter extends
		AbstractServiceGtfsExporter<CalendarDate> {
	private static final Object BUFFER_LOCK = new Object();
	private static final StringBuffer BUFFER = new StringBuffer(255);

	protected List<Calendar> getAssociatedCalendars(CalendarDate currentObject) {
		List<Calendar> result = new ArrayList<Calendar>();
		for (CalendarDateException exception : currentObject.getCalendarDateExceptions()) {
			Calendar exceptionCalendar = exception.getServiceException();
			if (exceptionCalendar != null && !checkListContains(result, exceptionCalendar)) {
				result.add(exceptionCalendar);
			}
		}
		if (currentObject.getServiceAdded() != null) {
			result.add(currentObject.getServiceAdded());
		}
		if (currentObject.getServiceRemoved() != null) {
			result.add(currentObject.getServiceRemoved());
		}
		return result;
	}

	protected void handleExport(
			CalendarDate toExport,
			ServiceCalendar serviceCalendar,
			OutputStream outStream) throws IOException {
		for (CalendarDateException exception : toExport.getCalendarDateExceptions()) {
			// Make sure to filter by calendar (as passed in via the ServiceCalendar parameter)
			if (exception.getServiceException().getCalendarId() == serviceCalendar.calendarId) {
				// This should in a nice, rosy world be executed only once.
				BUFFER.delete(0, BUFFER.length());
				BUFFER.append(buildServiceIdFromServiceCalendar(serviceCalendar));
				BUFFER.append(SEPARATOR);
				BUFFER.append(formatDateValue(toExport.getDate()));
				BUFFER.append(SEPARATOR);
				BUFFER.append(translateExceptionType(exception.getExceptionType().getExceptionTypeId()));
				BUFFER.append(LINE_SEPARATOR);
				outStream.write(BUFFER.toString().getBytes());
			}
		}
		if (toExport.getServiceAdded() != null && (toExport.getServiceAdded().getCalendarId() == serviceCalendar.calendarId)) {
			BUFFER.delete(0, BUFFER.length());
			BUFFER.append(buildServiceIdFromServiceCalendar(serviceCalendar));
			BUFFER.append(SEPARATOR);
			BUFFER.append(formatDateValue(toExport.getDate()));
			BUFFER.append(SEPARATOR);
			BUFFER.append(Constants.CALENDAR_EXCEPTION_TYPE_ADDITION);
			BUFFER.append(LINE_SEPARATOR);
			outStream.write(BUFFER.toString().getBytes());
		}
		if (toExport.getServiceRemoved() != null && (toExport.getServiceRemoved().getCalendarId() == serviceCalendar.calendarId)) {
			BUFFER.delete(0, BUFFER.length());
			BUFFER.append(buildServiceIdFromServiceCalendar(serviceCalendar));
			BUFFER.append(SEPARATOR);
			BUFFER.append(formatDateValue(toExport.getDate()));
			BUFFER.append(SEPARATOR);
			BUFFER.append(Constants.CALENDAR_EXCEPTION_TYPE_REMOVAL);
			BUFFER.append(LINE_SEPARATOR);
			outStream.write(BUFFER.toString().getBytes());
		}
	}

	final Class<CalendarDate> getSupportedClass() {
		return CalendarDate.class;
	}


	private boolean checkListContains(List<Calendar> calendarList, Calendar calendar) {
		boolean result = false;
		for (Calendar cal : calendarList) {
			if (cal.getCalendarId() == calendar.getCalendarId()) {
				result = true;
				break;
			}
		}
		return result;
	}

	private int translateExceptionType(int fromData) {
		int result = Constants.CALENDAR_EXCEPTION_TYPE_ADDITION;
		if (fromData == 0) {
			result = Constants.CALENDAR_EXCEPTION_TYPE_REMOVAL;
		}
		return result;
	}
}
