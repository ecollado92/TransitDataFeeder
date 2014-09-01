/**
 * 
 */
package org.ideaproject.util;

import org.ideaproject.Constants;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class MapUtilTest {
	private final static double OTHER_LAT = 45.530199;
	private final static double OTHER_LON = -122.563207;

	private final static double EXPECTED_DIST_KM = 7.976765739071323;
	private final static double EXPECTED_DIST_MILES = EXPECTED_DIST_KM / MapUtil.MILES_TO_KM_MULTIPLIER;

	@Test
	public void testCalculateDistance() throws Exception {
		Double result =
			MapUtil.calculateDistance(Constants.DEFAULT_AGENCY_LATITUDE,
					Constants.DEFAULT_AGENCY_LONGITUDE, 
					OTHER_LAT, 
					OTHER_LON,
					Constants.DistanceUnit.KM);
		assert result.doubleValue() == EXPECTED_DIST_KM;
		
		result =
			MapUtil.calculateDistance(Constants.DEFAULT_AGENCY_LATITUDE,
					Constants.DEFAULT_AGENCY_LONGITUDE, 
					OTHER_LAT, 
					OTHER_LON,
					Constants.DistanceUnit.MILE);
		assert result.doubleValue() == EXPECTED_DIST_MILES;
	}
}
