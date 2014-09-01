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

import org.ideaproject.Constants;
import org.ideaproject.model.Direction;
import org.ideaproject.model.Trip;

/**
 * @author dirk
 *
 */
public class TripGtfsExporter extends AbstractTripCalendarGtfsExporter<Trip> {
	private static final Object BUFFER_LOCK = new Object();
	private static final StringBuffer BUFFER = new StringBuffer(255);

	final Class<Trip> getSupportedClass() {
		return Trip.class;
	}

	private int translateDirectonId(Direction direction) {
		int result = 1;
		if (direction != null && direction.getInboundFlag() == Boolean.FALSE) {
			result = 0;
		}
		return result;
	}

	@Override
	protected void handleExport(
			Trip toExport,
			ServiceCalendar serviceCalendar,
			OutputStream outStream) throws IOException {
		TripCalendar tripCalendar = (TripCalendar) serviceCalendar;
		BUFFER.delete(0, BUFFER.length());
		BUFFER.append(toExport.getRoute() != null ? toExport.getRoute().getRouteId() : Constants.EMPTY);
		BUFFER.append(SEPARATOR);
		BUFFER.append(buildServiceIdFromServiceCalendar(tripCalendar));
		BUFFER.append(SEPARATOR);
		BUFFER.append(buildTripIdFromTripCalendar(tripCalendar));
		BUFFER.append(SEPARATOR);
		BUFFER.append(toExport.getTripHeadsign());
		BUFFER.append(SEPARATOR);
		BUFFER.append(translateDirectonId(toExport.getDirection()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(toExport.getBlock() != null ? toExport.getBlock().getBlockId() : Constants.EMPTY);
		BUFFER.append(SEPARATOR);
		BUFFER.append(Constants.EMPTY);
//		BUFFER.append(SEPARATOR);
//		BUFFER.append(toExport.getBikeOption().getBikeOptionId());
		BUFFER.append(LINE_SEPARATOR);
		outStream.write(BUFFER.toString().getBytes());
	}

	@Override
	protected Trip getTripToExport(Trip currentObject) {
		return currentObject;
	}
}
