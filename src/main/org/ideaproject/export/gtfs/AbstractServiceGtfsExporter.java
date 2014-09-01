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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ideaproject.Exportable;
import org.ideaproject.model.Calendar;
import org.ideaproject.model.ServiceScheduleBound;
import org.ideaproject.model.ServiceScheduleGroup;
import org.ideaproject.util.HashCodeUtil;

/**
 * @author dirk
 *
 */
public abstract class AbstractServiceGtfsExporter<T extends Exportable> extends
		AbstractGtfsExporter<T> {
	private static final Map<ServiceCalendar, String> SERVICE_ID_MAP = new HashMap<ServiceCalendar, String>();

	public final void export(T toExport, OutputStream outStream)
	throws IOException {
		for (ServiceCalendar tripCalendar : getServiceCalendarEntries(toExport)) {
			handleExport(toExport, tripCalendar, outStream);
		}
	}

	
	protected abstract List<Calendar> getAssociatedCalendars(T currentObject);

	protected abstract void handleExport(T toExport, 
			ServiceCalendar serviceCalendar, OutputStream outStream) throws IOException;
	
	protected List<ServiceCalendar> getServiceCalendarEntries(final T toExport) {
		List<ServiceCalendar> result = new ArrayList<ServiceCalendar>();
		List<Calendar> calendars = getAssociatedCalendars(toExport);
		if (!calendars.isEmpty()) {
			for (Calendar calendar : calendars) {
				ServiceScheduleGroup calendarServiceGroup = calendar.getServiceScheduleGroup();
				if (calendarServiceGroup != null) {
					for (ServiceScheduleBound bound : calendarServiceGroup.getServiceScheduleBounds()) {
						result.add(new ServiceCalendar(calendar.getCalendarId(), bound.getServiceScheduleBoundsId(),
								bound.getStartDate(), bound.getEndDate()));
					}
				}
			}
		}
		return result;
	}

	protected String buildServiceIdFromServiceCalendar(ServiceCalendar tripCalendar) {
		String result = SERVICE_ID_MAP.get(tripCalendar);
		if (result == null) {
			StringBuffer resultBuffer = new StringBuffer();
			resultBuffer.append(tripCalendar.calendarId);
			resultBuffer.append(ID_SEGMENT_SEPARATOR);
			resultBuffer.append(tripCalendar.scheduleBoundsId);
			result = resultBuffer.toString();
			SERVICE_ID_MAP.put(tripCalendar, result);
		}
		return result;
	}

	protected static class ServiceCalendar {
		protected final int calendarId;
		protected final int scheduleBoundsId;
		protected final Date startDate;
		protected final Date endDate;
		private int _hashCode = -1;

		protected ServiceCalendar(int calendarId, int scheduleBoundsId, Date startDate, Date endDate) {
			this.calendarId = calendarId;
			this.scheduleBoundsId = scheduleBoundsId;
			this.startDate = startDate;
			this.endDate = endDate;
		}

		@Override
		public int hashCode() {
			int result = _hashCode;
			if (result == -1) {
				result = HashCodeUtil.SEED;
				result = HashCodeUtil.hash(result, calendarId);
				result = HashCodeUtil.hash(result, scheduleBoundsId);
				_hashCode = result;
			}
			return result;
		}

		@Override
		public boolean equals(Object other) {
			boolean result = false;
			if (other instanceof ServiceCalendar) {
				if (this == other || this.hashCode() == other.hashCode()) {
					result = true;
				}
			}
			return result;
		}
	}
}
