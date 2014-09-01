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
package org.ideaproject;

/**
 * Storage for EJB NamedQuery query names
 * 
 * @author dirk
 */
public interface QueryNames {
	/**
	 * Query which counts all trips associated with a given agency.
	 */
	String COUNT_AGENCY_TRIPS_ALL = "allAgencyTripCount";
	/**
	 * Query which counts only those trips associated with a given agency which have no stop times.
	 */
	String COUNT_AGENCY_TRIPS_NO_STOPTIME = "noStopTimeAgencyTripCount";

	/**
	 * Query which counts all stops associated with a given agency.
	 */
	String COUNT_AGENCY_STOPS_ALL = "allAgencyStopCount";

	/**
	 * Query which counts only those stops associated with a given agency which have no geolocation data.
	 */
	String COUNT_AGENCY_STOPS_NO_LOCATION = "noLocationAgencyStopCount";

	/**
	 * Query which counts only those stops associated with a given agency which have no stop times.
	 */
	String COUNT_AGENCY_STOPS_UNSCHEDULED = "unscheduledAgencyStopCount";

	/**
	 * Query which counts all routes associated with a given agency.
	 */
	String COUNT_AGENCY_ROUTES_ALL = "allAgencyRouteCount";

	/**
	 * Query which counts only those routes associated with a given agency which have no trips defined.
	 */
	String COUNT_AGENCY_ROUTES_NO_TRIPS = "noTripAgencyRouteCount";

	/**
	 * Query which counts all fares associated with a given agency.
	 */
	String COUNT_AGENCY_FARES_ALL = "allAgencyFareCount";
	
	/**
	 * Query which counts only those fares associated with a given agency which have no fare rules.
	 */
	String COUNT_AGENCY_FARES_UNUSED = "unusedAgencyFareCount";

}
