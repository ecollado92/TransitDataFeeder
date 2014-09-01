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

import org.ideaproject.model.Frequency;
import org.ideaproject.model.Trip;
import org.ideaproject.util.StringUtil;

/**
 * @author dirk
 *
 */
public class FrequencyGtfsExporter extends
		AbstractTripCalendarGtfsExporter<Frequency> {
	private static final Object BUFFER_LOCK = new Object();
	private static final StringBuffer BUFFER = new StringBuffer(255);

	protected void handleExport(
			Frequency toExport,
			ServiceCalendar serviceCalendar,
			OutputStream outStream) throws IOException {

		TripCalendar tripCalendar = (TripCalendar) serviceCalendar;
		BUFFER.delete(0, BUFFER.length());
		BUFFER.append(buildTripIdFromTripCalendar(tripCalendar));
		BUFFER.append(SEPARATOR);
		BUFFER.append(StringUtil.formatTimeValue(toExport.getStartTime(), toExport.isStartTimeCarryover()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(StringUtil.formatTimeValue(toExport.getEndTime(), toExport.isEndTimeCarryover()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(toExport.getHeadwaySecs());
		BUFFER.append(LINE_SEPARATOR);
		outStream.write(BUFFER.toString().getBytes());
	}

	Class<Frequency> getSupportedClass() {
		return Frequency.class;
	}

	protected Trip getTripToExport(Frequency currentObject) {
		return currentObject.getTrip();
	}

}
