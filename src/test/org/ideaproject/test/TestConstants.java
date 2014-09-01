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
package org.ideaproject.test;

import java.util.Date;

/**
 * @author dirk
 *
 */
public interface TestConstants {
	// Operation outcomes
	/** Outcome "persisted" **/
	String PERSISTED = "persisted";
	/** Outcome "updated" **/
	String UPDATED = "updated";
	/** Outcome "removed" **/
	String REMOVED = "removed";

	// Credential Constants
	String ADMIN_USER_USERNAME = "admin";
	String ADMIN_USER_PASSWORD = "";

	// Payment Method constants
	Integer DEFAULT_PAYMENT_METHOD_ID = 1;
	
	// Bike Option constants
	Integer DEFAULT_BIKE_OPTION_ID = 11111;
	
	// Route Type constants
	Integer DEFAULT_ROUTE_TYPE_ID = 111111;

	// Calendar Exception Type constants
	Integer DEFAULT_CALENDAR_EXCEPTION_TYPE_ID = 1;

	// Agency Group constants
	Integer DEFAULT_AGENCY_GROUP_ID = 1;
	String TEST_AGENCY_GROUP_NAME = "TestNG Test Agency Group";

	// Agency constants
	Integer DEFAULT_AGENCY_ID = 11;
	String TEST_AGENCY_NAME = "TestNG Test Agancy";
	String TEST_AGENCY_SHORT_NAME = "T_T_A";
	String TEST_AGENCY_LANG = "en";
	String TEST_AGENCY_TZ = "America/Los_Angeles";
	String TEST_AGENCY_URL = "http://www.test.com";
	String BOGUS_AGENCY_NAME = "BOGUS";

	// Zone constants
	Integer DEFAULT_ZONE_ID = 1;
	String TEST_ZONE_NAME_1 = "TestNG Test Zone 1";
	String TEST_ZONE_NAME_2 = "TestNG Test Zone 1";
	String TEST_ZONE_NAME_3 = "TestNG Test Zone 1";
	
	// Direction constants
	Integer DEFAULT_DIRECTION_ID = 1;
	String TEST_DIRECTION_LABEL = "TestNG Test Direction";

	// Route constants
	Integer DEFAULT_ROUTE_ID = 1;
	String TEST_ROUTE_SHORT_NAME = "TestNG Route";
	String TEST_ROUTE_LONG_NAME = "My most excellent Route for testing";
	String TEST_ROUTE_URL = "http://www.my.route.com";

	// Block constants
	Integer DEFAULT_BLOCK_ID = 1;
	String TEST_BLOCK_LABEL = "TestNG Test Block";

	// Service Schedule Group constants
	Integer DEFAULT_SERVICE_SCHEDULE_GROUP_ID = 1;
	String TEST_SERVICE_SCHEDULE_GROUP_LABEL = "TestNG Test Schedule";
	
	// Service Schedule Bound constants
	Integer DEFAULT_SERVICE_SCHEDULE_BOUND_ID = 1;
	// Jan 01, 2010
	Date TEST_SERVICE_SCHEDULE_BOUND_STARTDATE = new Date(1262332800000L);
	// Dec 31, 2010
	Date TEST_SERVICE_SCHEDULE_BOUND_ENDDATE = new Date(1293782400000L);

	// Location constants
	double TEST_LOCATION_LATITUDE = 45.518557;
	double TEST_LOCATION_LONGITUDE = -122.664242;

	// Stop constants
	Integer DEFAULT_STOP_ID_1 = 3;
	Integer DEFAULT_STOP_ID_2 = 4;
	String TEST_STOP_NAME_1 = "TestNG Test Stop 1";
	String TEST_STOP_NAME_2 = "TestNG Test Stop 2";
	
	// Station constants
	Integer DEFAULT_STATION_ID_1 = 1;
	Integer DEFAULT_STATION_ID_2 = 2;
	String TEST_STATION_NAME_1 = "TestNG Test Station 1";
	String TEST_STATION_NAME_2 = "TestNG Test Station 2";

	// Fare constants
	Integer DEFAULT_FARE_ID = 1;
	double TEST_FARE_PRICE = 3.35;
	Integer TEST_FARE_TRANSFER_DURATION = 60;

	// Fare Rule constants
	Integer DEFAULT_FARE_RULE_ID = 1;

	// Transfer constants
	Integer DEFAULT_TRANSFER_ID = 1;
	Integer TEST_TRANSFER_MIN_TIME = 300;

	// Calendar constants
	Integer DEFAULT_CALENDAR_ID = 1;
	String TEST_CALENDAR_SERVICE_LABEL_1 = "TestNG Test Calendar 1";

	// Trip constants
	Integer DEFAULT_TRIP_ID = 1;
	Integer CHILD_TRIP_ID = 2;
	String TEST_TRIP_HEADSIGN = "TestNG Test Trip Headsign";

	// Frequency constants
	Integer DEFAULT_FREQUENCY_ID = 1;
	boolean TEST_FREQUENCY_EXACT_TIME = true;
	int TEST_FREQUENCY_HEADWAY_SECS = 600;

	// Calendar Date constants
	Integer DEFAULT_CALENDAR_DATE_ID = 1;
	String TEST_CALENDAR_DATE_DESCRIPTION = "TestNG Test Calendar Date";

}
