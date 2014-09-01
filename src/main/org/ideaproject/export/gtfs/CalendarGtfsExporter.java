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

import org.ideaproject.model.Calendar;

/**
 * @author dirk
 *
 */
public class CalendarGtfsExporter extends AbstractServiceGtfsExporter<Calendar> {
	private static final Object BUFFER_LOCK = new Object();
	private static final StringBuffer BUFFER = new StringBuffer(255);

	@Override
	protected List<Calendar> getAssociatedCalendars(Calendar currentObject) {
		List<Calendar> result = new ArrayList<Calendar>();
		result.add(currentObject);
		return result;
	}

	protected void handleExport(
			Calendar toExport,
			ServiceCalendar serviceCalendar,
			OutputStream outStream) throws IOException {
		BUFFER.delete(0, BUFFER.length());
		BUFFER.append(buildServiceIdFromServiceCalendar(serviceCalendar));
		BUFFER.append(SEPARATOR);
		BUFFER.append(outputBoolean(toExport.isMonday()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(outputBoolean(toExport.isTuesday()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(outputBoolean(toExport.isWednesday()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(outputBoolean(toExport.isThursday()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(outputBoolean(toExport.isFriday()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(outputBoolean(toExport.isSaturday()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(outputBoolean(toExport.isSunday()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(formatDateValue(serviceCalendar.startDate));
		BUFFER.append(SEPARATOR);
		BUFFER.append(formatDateValue(serviceCalendar.endDate));
		BUFFER.append(LINE_SEPARATOR);
		outStream.write(BUFFER.toString().getBytes());
	}

	Class<Calendar> getSupportedClass() {
		return Calendar.class;
	}

	private byte outputBoolean(boolean bool) {
		byte result = (byte) 0;
		if (bool) {
			result = (byte) 1;
		}
		return result;
	}

}
