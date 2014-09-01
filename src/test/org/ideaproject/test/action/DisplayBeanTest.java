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

import org.ideaproject.action.DisplayBean;
import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityhome.FareHome;
import org.ideaproject.action.entityhome.RouteHome;
import org.ideaproject.action.entityhome.TripHome;
import org.ideaproject.test.AbstractAdminLoginSeamTest;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class DisplayBeanTest extends AbstractAdminLoginSeamTest {

	@Test
	@Transactional
	public void testViewBeanFunctions() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");

				FareHome fareHome = (FareHome) getInstance("fareHome");
				RouteHome routeHome = (RouteHome) getInstance("routeHome");
				TripHome tripHome = (TripHome) getInstance("tripHome");

				DisplayBean displayBean = (DisplayBean) getInstance("displayBean");

				agencyHome.setAgencyAgencyId(TestConstants.DEFAULT_AGENCY_ID);
				agencyHome.load();

				fareHome.setFareFareId(TestConstants.DEFAULT_FARE_ID);
				fareHome.load();

				routeHome.setRouteRouteId(TestConstants.DEFAULT_ROUTE_ID);
				routeHome.load();

				tripHome.setTripTripId(TestConstants.CHILD_TRIP_ID);
				tripHome.load();

				String fareDisplay = displayBean.getFareDisplayValue(fareHome.getInstance());
				assert fareDisplay != null;

				String routeDisplay = displayBean.getRouteDisplayValue(routeHome.getInstance());
				assert routeDisplay != null;

				String tripDisplay = displayBean.getTripDisplayValue(tripHome.getInstance());
				assert tripDisplay != null;
			}
		}.run();
	}
}
