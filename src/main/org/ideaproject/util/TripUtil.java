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
package org.ideaproject.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.ideaproject.model.StopTime;
import org.ideaproject.model.Trip;

/**
 * @author dirk
 *
 */
public final class TripUtil {
	public static List<StopTime> buildInheritedStopTimeList(Trip trip) {
		if(trip == null) {
			throw new IllegalArgumentException("Null trip passed to buildInheritedStopTimeList");
		}
		Trip basedOn = trip.getBasedOn();
		if (basedOn == null) {
			throw new IllegalArgumentException("The trip " + trip.getTripId() + " is not based upon another trip. " +
					"Use trip.getStopTimes() instead.");
		}
		long tripStartTime = trip.getTripStartTime() != null ? 
				trip.getTripStartTime().getTime()  + (trip.isStartTimeCarryover() ? 24L * 60 * 60 * 1000 : 0L):
				0L;
		
		List<StopTime> result = new ArrayList<StopTime>();
		List<StopTime> inheritedTimes = basedOn.getStopTimes();
		if (tripStartTime > 0 && !inheritedTimes.isEmpty()) {
			long adjustment = tripStartTime - getStopTimeArrivalTime(inheritedTimes.get(0)).getTime();
			int adjToInt = ((Long) adjustment).intValue();
			for (StopTime inheritedTime : inheritedTimes) {
				result.add(copyStopTimeWithAdjustment(trip, inheritedTime, adjToInt));
			}
		} else {
			result = inheritedTimes;
		}
		return Collections.unmodifiableList(result);
	}

	public static Date getStopTimeArrivalTime(StopTime stopTime) {
		return getTimeWithAdjustment(stopTime.getArrivalTime(), stopTime.isArrivalTimeCarryover());
	}

	private static Date getTimeWithAdjustment(Date date, boolean carryover) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		if (carryover) {
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
		return cal.getTime();
	}

	private static void setStopTimeArrivalTime(StopTime stopTime, Date arrivalTime) {
		if (arrivalTime != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(arrivalTime);
			if (cal.get(Calendar.DAY_OF_YEAR) > 1) {
				stopTime.setArrivalTimeCarryover(true);
				cal.add(Calendar.DAY_OF_YEAR, -1);
			}
			stopTime.setArrivalTime(cal.getTime());
		} else {
			stopTime.setArrivalTime(arrivalTime);
		}
	}

	private static void setStopTimeDepartureTime(StopTime stopTime, Date departureTime) {
		if (departureTime != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(departureTime);
			if (cal.get(Calendar.DAY_OF_YEAR) > 1) {
				stopTime.setDepartureTimeCarryover(true);
				cal.add(Calendar.DAY_OF_YEAR, -1);
			}
			stopTime.setDepartureTime(cal.getTime());
		} else {
			stopTime.setDepartureTime(departureTime);
		}
	}

	private static StopTime copyStopTimeWithAdjustment(Trip destination, StopTime toCopy, int millisecondAdjustment) {
		StopTime result = new StopTime();
		result.setStopTimeId(toCopy.getStopTimeId());
		result.setAgency(toCopy.getAgency());
		result.setDropOffType(toCopy.getDropOffType());
		result.setPickupType(toCopy.getPickupType());
		result.setShapeDistTraveled(toCopy.getShapeDistTraveled());
		result.setStop(toCopy.getStop());
		result.setStopSequence(toCopy.getStopSequence());
		result.setTimingPoint(toCopy.getTimingPoint());
		result.setTrip(destination);
		result.setArrivalTimeCarryover(toCopy.isArrivalTimeCarryover());
		result.setDepartureTimeCarryover(toCopy.isDepartureTimeCarryover());

		setStopTimeArrivalTime(result, getAdjustedDate(toCopy.getArrivalTime(), millisecondAdjustment));
		setStopTimeDepartureTime(result, getAdjustedDate(toCopy.getDepartureTime(), millisecondAdjustment));

		result.splitArrivalAndDepartureTimes();

		return result;
	}

	private static Date getAdjustedDate(Date original, int millisecondAdjustment) {
		Date result = null;
		if (original != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(original);
			cal.add(Calendar.MILLISECOND, millisecondAdjustment);
			result = cal.getTime();
		}
		return result;
	}
}
