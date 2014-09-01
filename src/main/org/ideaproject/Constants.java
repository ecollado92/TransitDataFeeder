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
 * @author dirk
 *
 */
public interface Constants {

	enum ExportType {
		GTFS;
	}

	enum DistanceUnit {
		MILE,
		KM;
	}

	int STOP_LOCATION_TYPE_ID = 0;
	String STOP_LOCATION_DISCRIMINATOR = String.valueOf(STOP_LOCATION_TYPE_ID);
	int STATION_LOCATION_TYPE_ID = 1;
	String STATION_LOCATION_DISCRIMINATOR = String.valueOf(STATION_LOCATION_TYPE_ID);

	double DEFAULT_AGENCY_LATITUDE = 45.518557;
	double DEFAULT_AGENCY_LONGITUDE = -122.664242;

	String DEFAULT_AGENCY_LANGUAGE = "en";
	String DEFAULT_CURRENCY_TYPE = "USD";

	int HOURS_IN_DAY = 24;
	int SECONDS_IN_MINUTE = 60;

	String EMPTY = "";
	String DEFAULT_TIME_ENTRY = "00:00:00";
	String TIME_SEPARATOR = ":";	

	String ROLE_ADMIN = "admin";

	/* Labels */
	String LABEL_UNLIMITED = "Unlimited";

	/* Navbar menu labels */
	String NAVBAR_NONE_LABEL = "NONE";
	String NAVBAR_HOME_LABEL = "HOME";
	String NAVBAR_DASHBOARD_LABEL = "DASH";
	String NAVBAR_STOPS_LABEL = "STOPS";
	String NAVBAR_ROUTES_LABEL = "ROUTES";
	String NAVBAR_FARES_LABEL = "FARES";
	String NAVBAR_CALENDARS_LABEL = "CALS";

	int CALENDAR_EXCEPTION_TYPE_ADDITION = 1;
	int CALENDAR_EXCEPTION_TYPE_REMOVAL = 2;
}
