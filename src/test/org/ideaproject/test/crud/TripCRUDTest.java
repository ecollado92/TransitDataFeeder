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
package org.ideaproject.test.crud;

import org.ideaproject.action.ValidationTableBean;
import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityhome.BlockHome;
import org.ideaproject.action.entityhome.CalendarHome;
import org.ideaproject.action.entityhome.DirectionHome;
import org.ideaproject.action.entityhome.RouteHome;
import org.ideaproject.action.entityhome.TripHome;
import org.ideaproject.action.entityquery.TripList;
import org.ideaproject.model.BikeOption;
import org.ideaproject.test.AbstractAdminLoginSeamTest;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class TripCRUDTest extends AbstractAdminLoginSeamTest {

	@Test
	@Transactional
	public void testCreate() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				RouteHome routeHome = (RouteHome) getInstance("routeHome");
				BlockHome blockHome = (BlockHome) getInstance("blockHome");
				CalendarHome calendarHome = (CalendarHome) getInstance("calendarHome");
				DirectionHome directionHome = (DirectionHome) getInstance("directionHome");
				ValidationTableBean validationTableBean = (ValidationTableBean) getInstance("validationTableBean");
				TripHome tripHome = (TripHome) getInstance("tripHome");
				TripList tripList = (TripList) getInstance("tripList");

				agencyHome.setAgencyAgencyId(TestConstants.DEFAULT_AGENCY_ID);
				agencyHome.load();

				routeHome.setRouteRouteId(TestConstants.DEFAULT_ROUTE_ID);
				routeHome.load();

				blockHome.setBlockBlockId(TestConstants.DEFAULT_BLOCK_ID);
				blockHome.load();

				calendarHome.setCalendarCalendarId(TestConstants.DEFAULT_CALENDAR_ID);
				calendarHome.load();

				directionHome.setDirectionDirectionId(TestConstants.DEFAULT_DIRECTION_ID);
				directionHome.load();

				BikeOption bikeOption = validationTableBean.getBikeOptions().get(0);
				assert bikeOption != null;

				tripHome.getInstance().setAgency(agencyHome.getInstance());
				tripHome.getInstance().setRoute(routeHome.getInstance());
				tripHome.getInstance().setBikeOption(bikeOption);
				tripHome.getInstance().setBlock(blockHome.getInstance());
				tripHome.getInstance().setCalendar(calendarHome.getInstance());
				tripHome.getInstance().setDirection(directionHome.getInstance());
				tripHome.getInstance().setTripHeadsign(TestConstants.TEST_TRIP_HEADSIGN);

				assert tripHome.isWired();
				assert !tripHome.isManaged();

				assert TestConstants.PERSISTED.equals(tripHome.persist());

				tripList.getTrip().setAgency(agencyHome.getInstance());
				assert tripList.getResultList().contains(tripHome.getInstance());

				tripList.setHasStopTimes(Boolean.FALSE);
				tripList.refresh();
				assert tripList.getResultList().contains(tripHome.getInstance());

				tripList.getTrip().setBikeOption(bikeOption);
				tripList.refresh();
				assert tripList.getResultList().contains(tripHome.getInstance());

				tripList.getTrip().setBlock(blockHome.getInstance());
				tripList.refresh();
				assert tripList.getResultList().contains(tripHome.getInstance());

				tripList.getTrip().setCalendar(calendarHome.getInstance());
				tripList.refresh();
				assert tripList.getResultList().contains(tripHome.getInstance());

				tripList.getTrip().setDirection(directionHome.getInstance());
				tripList.refresh();
				assert tripList.getResultList().contains(tripHome.getInstance());

				tripList.getTrip().setRoute(routeHome.getInstance());
				tripList.refresh();
				assert tripList.getResultList().contains(tripHome.getInstance());

				tripList.getTrip().setTripHeadsign(TestConstants.TEST_TRIP_HEADSIGN);
				tripList.refresh();
				assert tripList.getResultList().contains(tripHome.getInstance());

				Integer tripId = tripHome.getTripTripId();
				assert tripId != null;
				assert tripHome.isManaged();

				tripHome.clearInstance();
				assert tripHome.getDefinedInstance() == null;
				tripHome.load();
				assert tripHome.getDefinedInstance() == null;
				tripHome.setTripTripId(tripId);
				tripHome.load();
				assert tripHome.getDefinedInstance() != null;
				assert TestConstants.TEST_TRIP_HEADSIGN.equals(tripHome.getInstance().getTripHeadsign());

				assert TestConstants.REMOVED.equals(tripHome.remove());
			}
		}.run();

		logoutAdmin();
	}
	
}
