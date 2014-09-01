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

import org.ideaproject.Constants;

/**
 * @author dirk
 *
 */
public final class MapUtil {
	public static final Double MILES_TO_KM_MULTIPLIER = 1.609344;

	/** Mean radius from IUGG **/
	private static final Double AVERAGE_EARTH_RADIUS_KM = 6371.009;
	private static final Double MILES_TO_NAUTICAL_MILES_CONVERTER = 0.8684;

	public final static Double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2, 
			Constants.DistanceUnit unit) {
		Double result = null;
		if (lat1 == null || lat2 == null || lon1 == null || lon2 == null) {
			throw new IllegalArgumentException("Distance is not calculable if any field is null!");
		}
		final double distLat = Math.toRadians(lat2 - lat1);
		final double distLon = Math.toRadians(lon2 - lon1);
				
		final double lat1radians = Math.toRadians(lat1);
		final double lat2radians = Math.toRadians(lat2);

		double res = haversine(distLat) + Math.cos(lat1radians) * Math.cos(lat2radians) * haversine(distLon);
		double distanceInKm = 2.0 * AVERAGE_EARTH_RADIUS_KM  * Math.asin(Math.sqrt(res));

		if (unit == Constants.DistanceUnit.MILE) {
			result = distanceInKm / MILES_TO_KM_MULTIPLIER;
		} else {
			result = distanceInKm;
		}
		return result;
	}

	private static final double haversine(double x) {
		double toSquare = Math.sin(x / 2);
		return toSquare * toSquare;
	}
}
