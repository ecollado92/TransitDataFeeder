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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ideaproject.Exportable;
import org.ideaproject.model.Calendar;
import org.ideaproject.model.Trip;
import org.ideaproject.util.HashCodeUtil;

/**
 * @author dirk
 *
 */
public abstract class AbstractTripCalendarGtfsExporter<T extends Exportable> extends
	AbstractServiceGtfsExporter<T> {
	private static final Map<Integer, List<Calendar>> TRIP_ID_TO_CALENDAR_MAP = new HashMap<Integer, List<Calendar>>();
	private static final Map<TripCalendar, String> TRIP_ID_MAP = new HashMap<TripCalendar, String>();

	protected abstract Trip getTripToExport(T currentObject);

	@Override
	protected List<ServiceCalendar> getServiceCalendarEntries(final T toExport) {
		List<ServiceCalendar> result = new ArrayList<ServiceCalendar>();

		Trip trip = getTripToExport(toExport);
		for (ServiceCalendar serviceCalendar : super.getServiceCalendarEntries(toExport)) {
			result.add(new TripCalendar(trip.getTripId(), serviceCalendar));
		}
		return result;
	}

	protected String buildTripIdFromTripCalendar(TripCalendar tripCalendar) {
		String result = TRIP_ID_MAP.get(tripCalendar);
		if (result == null) {
			StringBuffer resultBuffer = new StringBuffer();
			resultBuffer.append(tripCalendar.tripId);
			resultBuffer.append(ID_SEGMENT_SEPARATOR);
			resultBuffer.append(buildServiceIdFromServiceCalendar(tripCalendar));
			result = resultBuffer.toString();
			TRIP_ID_MAP.put(tripCalendar, result);
		}
		return result;
	}

	protected List<Calendar> getAssociatedCalendars(T currentObject) {
		Trip _trip = getTripToExport(currentObject);
		List<Calendar> result = TRIP_ID_TO_CALENDAR_MAP.get(_trip.getTripId());
		if (result == null) {
			result = new ArrayList<Calendar>();
			Calendar toAdd = getTripToExport(currentObject).getCalendar();
			if (toAdd != null) {
				result.add(toAdd);
				TRIP_ID_TO_CALENDAR_MAP.put(_trip.getTripId(), result);
			}
		}
		return result;
	}
	
	protected static class TripCalendar extends ServiceCalendar {
		private final int tripId;
		private int _hashCode = -1;

		protected TripCalendar(int tripId, int calendarId, int scheduleBoundsId, Date startDate, Date endDate) {
			super(calendarId, scheduleBoundsId, startDate, endDate);
			this.tripId = tripId;
		}

		protected TripCalendar(int tripId, ServiceCalendar serviceCalendar) {
			this(tripId,
				 serviceCalendar.calendarId,
				 serviceCalendar.scheduleBoundsId,
				 serviceCalendar.startDate,
				 serviceCalendar.endDate);
		}

		@Override
		public int hashCode() {
			int result = _hashCode;
			if (result == -1) {
				result = super.hashCode();
				result = HashCodeUtil.hash(result, tripId);
				_hashCode = result;
			}
			return result;
		}

		@Override
		public boolean equals(Object other) {
			boolean result = false;
			if (other instanceof TripCalendar) {
				if (this == other || this.hashCode() == other.hashCode()) {
					result = true;
				}
			}
			return result;
		}
	}
}
