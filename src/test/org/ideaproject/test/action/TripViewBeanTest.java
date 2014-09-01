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

import org.ideaproject.action.TripViewBean;
import org.ideaproject.action.entityhome.AgencyHome;
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
public class TripViewBeanTest extends AbstractAdminLoginSeamTest {

	@Test
	@Transactional
	public void testViewBeanFunctions() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				boolean exceptionThrown = false;
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				RouteHome routeHome = (RouteHome) getInstance("routeHome");
				TripHome tripHome = (TripHome) getInstance("tripHome");

				TripViewBean tripViewBean = (TripViewBean) getInstance("tripViewBean");

				agencyHome.setAgencyAgencyId(TestConstants.DEFAULT_AGENCY_ID);
				agencyHome.load();

				tripHome.setTripTripId(TestConstants.CHILD_TRIP_ID);
				tripHome.load();
				
				assert !tripViewBean.getAvailableTripParents().isEmpty();

				routeHome.setRouteRouteId(TestConstants.DEFAULT_ROUTE_ID);
				routeHome.load();
				
				assert !tripViewBean.getAvailableTripParents().isEmpty();

				assert !tripViewBean.getCurrentRouteDirections().isEmpty();

				
				try {
					assert !tripViewBean.getCurrentTripInheritedStopTimes().isEmpty();
				} catch (IllegalArgumentException e) {
					// This shouldn't happen...
					exceptionThrown = true;
				}
				assert !exceptionThrown;

				exceptionThrown = false;
				
				// Doing it again to test the lazy-load functionality
				try {
					assert !tripViewBean.getCurrentTripInheritedStopTimes().isEmpty();
				} catch (IllegalArgumentException e) {
					// This shouldn't happen...
					exceptionThrown = true;
				}

				exceptionThrown = false;
				
				tripHome.setTripTripId(TestConstants.DEFAULT_TRIP_ID);
				tripHome.load();
				assert tripHome.getInstance() != null;
				try {
					assert !tripViewBean.getCurrentTripInheritedStopTimes().isEmpty();
				} catch (IllegalArgumentException e) {
					// since the default trip is not based upon another, this call should throw an illegal argument exception
					exceptionThrown = true;
				}
				assert exceptionThrown;
			}
		}.run();
	}
}
