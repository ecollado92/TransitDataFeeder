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
package org.ideaproject.test.action;

import org.ideaproject.action.OneStepDeletionHandler;
import org.ideaproject.action.entityhome.AgencyGroupHome;
import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityhome.CalendarHome;
import org.ideaproject.action.entityhome.FareHome;
import org.ideaproject.action.entityhome.FareRuleHome;
import org.ideaproject.action.entityhome.RouteHome;
import org.ideaproject.action.entityhome.ServiceScheduleBoundHome;
import org.ideaproject.action.entityhome.StationHome;
import org.ideaproject.action.entityhome.StopHome;
import org.ideaproject.action.entityhome.StopTimeHome;
import org.ideaproject.action.entityhome.ZoneHome;
import org.ideaproject.test.ObjectTreeBuildingSeamTest;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class OneStepDeletionHandlerTest extends ObjectTreeBuildingSeamTest {

	@Test
	@Transactional
	public void testDeleteAgencyGroup() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyGroupHome agencyGroupHome = (AgencyGroupHome) getInstance("agencyGroupHome");
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				OneStepDeletionHandler oneStepDeletionHandler =
					(OneStepDeletionHandler) getInstance("oneStepDeletionHandler");

				createObjectTree();

				assert agencyGroupHome.getDefinedInstance() != null;
				assert agencyGroupHome.isManaged();
				assert !agencyGroupHome.getInstance().getAgencies().isEmpty();

				assert TestConstants.REMOVED.equals(oneStepDeletionHandler.deleteAgencyGroup(agencyGroupHome.getAgencyGroupAgencyGroupId()));

//				assert agencyHome.getDefinedInstance() != null;
//				assert agencyHome.isManaged();
//				assert !agencyHome.getInstance().getAgencyGroups().isEmpty();
//
//				assert TestConstants.REMOVED.equals(oneStepDeletionHandler.deleteAgency(agencyHome.getAgencyAgencyId()));
			}
		}.run();
	}

	@Test
	@Transactional
	public void testDeleteAgency() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				OneStepDeletionHandler oneStepDeletionHandler =
					(OneStepDeletionHandler) getInstance("oneStepDeletionHandler");

				createObjectTree();

				assert agencyHome.getDefinedInstance() != null;
				assert agencyHome.isManaged();
				assert !agencyHome.getInstance().getAgencyGroups().isEmpty();

				assert TestConstants.REMOVED.equals(oneStepDeletionHandler.deleteAgency(agencyHome.getAgencyAgencyId()));
			}
		}.run();
	}

	@Test
	@Transactional
	public void testDeleteZone() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				ZoneHome zoneHome = (ZoneHome) getInstance("zoneHome");
				OneStepDeletionHandler oneStepDeletionHandler =
					(OneStepDeletionHandler) getInstance("oneStepDeletionHandler");

				createObjectTree();
				Integer zoneId = zoneHome.getZoneZoneId();
	
				assert zoneHome.getDefinedInstance() != null;
				assert zoneHome.isManaged();
				assert !zoneHome.getInstance().getStops().isEmpty();

				assert TestConstants.REMOVED.equals(oneStepDeletionHandler.deleteZone(zoneId));
			}
		}.run();
	}

	@Test
	@Transactional
	public void testDeleteRoute() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				RouteHome routeHome = (RouteHome) getInstance("routeHome");
				OneStepDeletionHandler oneStepDeletionHandler =
					(OneStepDeletionHandler) getInstance("oneStepDeletionHandler");

				createObjectTree();
				Integer routeId = routeHome.getRouteRouteId();
	
				assert routeHome.getDefinedInstance() != null;
				assert routeHome.isManaged();
				assert !routeHome.getInstance().getTrips().isEmpty();

				assert TestConstants.REMOVED.equals(oneStepDeletionHandler.deleteRoute(routeId));
			}
		}.run();
	}

	@Test
	@Transactional
	public void testDeleteServiceScheduleBound() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				ServiceScheduleBoundHome serviceScheduleBoundHome = 
					(ServiceScheduleBoundHome) getInstance("serviceScheduleBoundHome");
				OneStepDeletionHandler oneStepDeletionHandler =
					(OneStepDeletionHandler) getInstance("oneStepDeletionHandler");

				createObjectTree();
				Integer scheduleId = serviceScheduleBoundHome.getServiceScheduleBoundServiceScheduleBoundsId();
	
				assert serviceScheduleBoundHome.getDefinedInstance() != null;
				assert serviceScheduleBoundHome.isManaged();

				assert TestConstants.REMOVED.equals(oneStepDeletionHandler.deleteServiceScheduleBound(scheduleId));
			}
		}.run();
	}

	@Test
	@Transactional
	public void testDeleteStop() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				StopHome stopHome = 
					(StopHome) getInstance("stopHome");
				OneStepDeletionHandler oneStepDeletionHandler =
					(OneStepDeletionHandler) getInstance("oneStepDeletionHandler");

				createObjectTree();
				Integer stopId = stopHome.getStopStopId();
	
				assert stopHome.getDefinedInstance() != null;
				assert stopHome.isManaged();
				assert !stopHome.getInstance().getStopTimes().isEmpty();

				assert TestConstants.REMOVED.equals(oneStepDeletionHandler.deleteStop(stopId));
			}
		}.run();
	}

	@Test
	@Transactional
	public void testDeleteStopTime() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				StopTimeHome stopTimeHome = 
					(StopTimeHome) getInstance("stopTimeHome");
				OneStepDeletionHandler oneStepDeletionHandler =
					(OneStepDeletionHandler) getInstance("oneStepDeletionHandler");

				createObjectTree();
				Integer stopTimeId = stopTimeHome.getStopTimeStopTimeId();
	
				assert stopTimeHome.getDefinedInstance() != null;
				assert stopTimeHome.isManaged();

				assert TestConstants.REMOVED.equals(oneStepDeletionHandler.deleteStopTime(stopTimeId));
			}
		}.run();
	}

	@Test
	@Transactional
	public void testDeleteStation() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				StationHome stationHome = 
					(StationHome) getInstance("stationHome");
				OneStepDeletionHandler oneStepDeletionHandler =
					(OneStepDeletionHandler) getInstance("oneStepDeletionHandler");

				createObjectTree();
				Integer stopId = stationHome.getStopStopId();
	
				assert stationHome.getDefinedInstance() != null;
				assert stationHome.isManaged();
				assert !stationHome.getInstance().getChildStops().isEmpty();

				assert TestConstants.REMOVED.equals(oneStepDeletionHandler.deleteStation(stopId));
			}
		}.run();
	}

	@Test
	@Transactional
	public void testDeleteFare() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				FareHome fareHome = (FareHome) getInstance("fareHome");
				OneStepDeletionHandler oneStepDeletionHandler =
					(OneStepDeletionHandler) getInstance("oneStepDeletionHandler");

				createObjectTree();
				Integer fareId = fareHome.getFareFareId();
	
				assert fareHome.getDefinedInstance() != null;
				assert fareHome.isManaged();
				assert !fareHome.getInstance().getFareRules().isEmpty();

				assert TestConstants.REMOVED.equals(oneStepDeletionHandler.deleteFare(fareId));
			}
		}.run();
	}

	@Test
	@Transactional
	public void testDeleteFareRule() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				FareRuleHome fareRuleHome = (FareRuleHome) getInstance("fareRuleHome");
				OneStepDeletionHandler oneStepDeletionHandler =
					(OneStepDeletionHandler) getInstance("oneStepDeletionHandler");

				createObjectTree();
				Integer fareRuleId = fareRuleHome.getFareRuleFareRuleId();
	
				assert fareRuleHome.getDefinedInstance() != null;
				assert fareRuleHome.isManaged();

				assert TestConstants.REMOVED.equals(oneStepDeletionHandler.deleteFareRule(fareRuleId));
			}
		}.run();
	}

	@Test
	@Transactional
	public void testDeleteCalendar() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				CalendarHome calendarHome = (CalendarHome) getInstance("calendarHome");
				OneStepDeletionHandler oneStepDeletionHandler =
					(OneStepDeletionHandler) getInstance("oneStepDeletionHandler");

				createObjectTree();
				Integer calendarId = calendarHome.getCalendarCalendarId();
	
//				calendarHome.clearInstance();
//				assert calendarHome.getDefinedInstance() == null;
//				assert !calendarHome.isManaged();
//				calendarHome.setCalendarCalendarId(calendarId);
//				calendarHome.load();
				assert calendarHome.getDefinedInstance() != null;
				assert calendarHome.isManaged();
				assert !calendarHome.getInstance().getTrips().isEmpty();

				assert TestConstants.REMOVED.equals(oneStepDeletionHandler.deleteCalendar(calendarId));
			}
		}.run();
	}

	//	@Test
//	public void testRemove() throws Exception {
//		loginAdmin();
//
//		new FacesRequest() {
//			@Override
//			protected void invokeApplication() throws Exception {
//				invokeMethod("#{agencyHome.load}");
//
//				assert invokeMethod("#{agencyHome.getDefinedInstance}") == null;
//				
//				setValue("#{agencyHome.instance.agencyName}", TestConstants.TEST_AGENCY_NAME);
//				setValue("#{agencyHome.instance.agencyShortName}", TestConstants.TEST_AGENCY_SHORT_NAME);
//				setValue("#{agencyHome.instance.agencyLanguage}", TestConstants.TEST_AGENCY_LANG);
//				setValue("#{agencyHome.instance.agencyTimezone}", TestConstants.TEST_AGENCY_TZ);
//				setValue("#{agencyHome.instance.agencyUrl}", TestConstants.TEST_AGENCY_URL);
//
//				assert TestConstants.PERSISTED.equals(invokeMethod("#{agencyHome.persist}"));
//				Agency agency = (Agency) invokeMethod("#{agencyHome.getInstance}");
//				assert agency != null;
//			}
//		}.run();
//	}

}
