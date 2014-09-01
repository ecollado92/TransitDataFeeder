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

import org.ideaproject.action.AgencyViewBean;
import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.test.AbstractAdminLoginSeamTest;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class AgencyViewBeanTest extends AbstractAdminLoginSeamTest {

	@Test
	@Transactional
	public void testViewBeanFunctions() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				AgencyViewBean agencyViewBean = (AgencyViewBean) getInstance("agencyViewBean");

				agencyHome.setAgencyAgencyId(TestConstants.DEFAULT_AGENCY_ID);
				agencyHome.load();

				assert !agencyViewBean.getCurrentAgencyBlocks().isEmpty();
				assert !agencyViewBean.getCurrentAgencyCalendars().isEmpty();
				assert !agencyViewBean.getCurrentAgencyDirections().isEmpty();
				assert !agencyViewBean.getCurrentAgencyFares().isEmpty();
				assert !agencyViewBean.getCurrentAgencyRoutes().isEmpty();
				assert !agencyViewBean.getCurrentAgencyStops().isEmpty();
				assert !agencyViewBean.getCurrentAgencyTransferableStops().isEmpty();
				assert !agencyViewBean.getCurrentAgencyTrips().isEmpty();
				assert !agencyViewBean.getCurrentAgencyZones().isEmpty();
			}
		}.run();
	}
}
