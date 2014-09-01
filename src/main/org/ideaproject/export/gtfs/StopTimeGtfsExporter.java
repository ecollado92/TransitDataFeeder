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
import org.ideaproject.model.StopTime;
import org.ideaproject.model.Trip;
import org.ideaproject.util.StringUtil;

/**
 * @author dirk
 *
 */
public class StopTimeGtfsExporter extends AbstractTripCalendarGtfsExporter<StopTime> {
	private static final Object BUFFER_LOCK = new Object();
	private static final StringBuffer BUFFER = new StringBuffer(255);

	protected void handleExport(
			StopTime toExport,
			ServiceCalendar serviceCalendar,
			OutputStream outStream) throws IOException {
		//$stop_times_csv=$stop_times_csv.$trip_id_csv.",".$row["arrival_time"].",".$row["departure_time"].",".$row["stop_id"].",".$loop_count_index.",,".$row["pickup_type"].",".$row["drop_off_type"].",".$shape_dist_traveled."\n";
		TripCalendar tripCalendar = (TripCalendar) serviceCalendar;
//		synchronized(BUFFER_LOCK) {
			BUFFER.delete(0, BUFFER.length());
			BUFFER.append(buildTripIdFromTripCalendar(tripCalendar));
			BUFFER.append(SEPARATOR);
			BUFFER.append(StringUtil.formatTimeValue(toExport.getArrivalTime(), toExport.isArrivalTimeCarryover()));
			BUFFER.append(SEPARATOR);
			BUFFER.append(StringUtil.formatTimeValue(toExport.getDepartureTime(), toExport.isDepartureTimeCarryover()));
			BUFFER.append(SEPARATOR);
			BUFFER.append(toExport.getStop() != null ? toExport.getStop().getStopId() : Constants.EMPTY);
			BUFFER.append(SEPARATOR);
//			BUFFER.append(toExport.getTrip().getStopTimes().indexOf(toExport) + 1);
			BUFFER.append(toExport.getStopSequence());
			BUFFER.append(SEPARATOR);
			BUFFER.append(toExport.getPickupType() != null ? toExport.getPickupType().getPickupTypeId() : Constants.EMPTY);
			BUFFER.append(SEPARATOR);
			BUFFER.append(toExport.getDropOffType() != null ? toExport.getDropOffType().getPickupTypeId() : Constants.EMPTY);
			BUFFER.append(SEPARATOR);
			BUFFER.append(toExport.getShapeDistTraveled() != null ? toExport.getShapeDistTraveled() : Constants.EMPTY);
			BUFFER.append(LINE_SEPARATOR);
			outStream.write(BUFFER.toString().getBytes());
//		}
//		StringBuffer buffer = new StringBuffer();
//		buffer.append(buildTripIdFromTripCalendar(tripCalendar));
//		buffer.append(SEPARATOR);
//		buffer.append(toExport.getArrivalTime() != null ? toExport.getArrivalTime() : "00:00:00");
//		buffer.append(SEPARATOR);
//		buffer.append(toExport.getDepartureTime() != null ? toExport.getDepartureTime() : "00:00:00");
//		buffer.append(SEPARATOR);
//		buffer.append(toExport.getStop() != null ? toExport.getStop().getStopId() : "");
//		buffer.append(SEPARATOR);
//		buffer.append(toExport.getTrip().getStopTimes().indexOf(toExport) + 1);
//		buffer.append(SEPARATOR);
//		buffer.append(toExport.getPickupType() != null ? toExport.getPickupType().getPickupTypeId() : "");
//		buffer.append(SEPARATOR);
//		buffer.append(toExport.getDropOffType() != null ? toExport.getDropOffType().getPickupTypeId() : "");
//		buffer.append(SEPARATOR);
//		buffer.append(toExport.getShapeDistTraveled() != null ? toExport.getShapeDistTraveled() : "");
//		buffer.append(LINE_SEPARATOR);
//		outStream.write(buffer.toString().getBytes());
	}

	final Class<StopTime> getSupportedClass() {
		return StopTime.class;
	}

	protected Trip getTripToExport(StopTime currentObject) {
		return currentObject.getTrip();
	}

}
