/**
 * 
 */
package org.ideaproject.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.ideaproject.model.StopTime;
import org.ideaproject.model.Trip;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class TripUtilTest {
	private static final int NOW_YEAR = 2010;
	private static final int NOW_MONTH = Calendar.APRIL;
	private static final int NOW_DOM = 15;
	private static final int NOW_HOUR = 10;
	private static final int NOW_MINUTE = 23;

	private static final int STOP_TIME_ARRIVAL_HOUR = 11;
	private static final int STOP_TIME_ARRIVAL_MINUTE = 15;
	private Trip trip;
	private Trip tripParent;
	private StopTime stopTime;
	private Date stopArrivalTime;
	private Date now;

	@BeforeClass
	public void setup() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, NOW_MONTH);
		cal.set(Calendar.YEAR, NOW_YEAR);
		cal.set(Calendar.DAY_OF_MONTH, NOW_DOM);
		cal.set(Calendar.HOUR, NOW_HOUR);
		cal.set(Calendar.MINUTE, NOW_MINUTE);
		now = cal.getTime();
	
		cal.set(Calendar.HOUR, STOP_TIME_ARRIVAL_HOUR);
		cal.set(Calendar.MINUTE, STOP_TIME_ARRIVAL_MINUTE);
		stopArrivalTime = cal.getTime();
		
		stopTime = new StopTime();
		stopTime.setArrivalTime(stopArrivalTime);

		tripParent = new Trip();
		tripParent.getStopTimes().add(stopTime);
		
		trip = new Trip();
		trip.setTripStartTime(now);
		trip.setBasedOn(tripParent);
	}

	@Test
	public void testGetStopTimeArrivalTime() throws Exception {
		Calendar arrivalTimeCal = Calendar.getInstance();
		
		Date arrivalTime = TripUtil.getStopTimeArrivalTime(stopTime);
		arrivalTimeCal.setTime(arrivalTime);
		assert arrivalTimeCal.get(Calendar.YEAR) == NOW_YEAR;
		assert arrivalTimeCal.get(Calendar.MONTH) == NOW_MONTH;
		assert arrivalTimeCal.get(Calendar.DAY_OF_MONTH) == NOW_DOM;
		assert arrivalTimeCal.get(Calendar.HOUR) == STOP_TIME_ARRIVAL_HOUR;
		assert arrivalTimeCal.get(Calendar.MINUTE) == STOP_TIME_ARRIVAL_MINUTE;
		
		stopTime.setArrivalTimeCarryover(true);
		arrivalTime = TripUtil.getStopTimeArrivalTime(stopTime);
		arrivalTimeCal.setTime(arrivalTime);

		assert arrivalTimeCal.get(Calendar.YEAR) == NOW_YEAR;
		assert arrivalTimeCal.get(Calendar.MONTH) == NOW_MONTH;
		assert arrivalTimeCal.get(Calendar.DAY_OF_MONTH) == (NOW_DOM + 1);
		assert arrivalTimeCal.get(Calendar.HOUR) == STOP_TIME_ARRIVAL_HOUR;
		assert arrivalTimeCal.get(Calendar.MINUTE) == STOP_TIME_ARRIVAL_MINUTE;
	}

	@Test
	public void testBuildInheritedStopTimeList() throws Exception {
		List<StopTime> result = TripUtil.buildInheritedStopTimeList(trip);
	}
}
