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

import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityhome.ZoneHome;
import org.ideaproject.action.entityquery.ZoneList;
import org.ideaproject.test.AbstractAdminLoginSeamTest;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class ZoneCRUDTest extends AbstractAdminLoginSeamTest {

	@Test
	@Transactional
	public void testCreate() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				ZoneHome zoneHome = (ZoneHome) getInstance("zoneHome");
				ZoneList zoneList = (ZoneList) getInstance("zoneList");

				setValue("#{credentials.username}", TestConstants.ADMIN_USER_USERNAME);
				setValue("#{credentials.password}", TestConstants.ADMIN_USER_PASSWORD);
				invokeMethod("#{identity.login}");

				setValue("#{agencyHome.agencyAgencyId}", TestConstants.DEFAULT_AGENCY_ID);
				invokeMethod("#{agencyHome.load}");

				assert zoneHome.isWired();
				assert !zoneHome.isManaged();
				
				zoneHome.getInstance().setAgency(agencyHome.getInstance());
				zoneHome.getInstance().setZoneName(TestConstants.TEST_ZONE_NAME_1);

				assert TestConstants.PERSISTED.equals(zoneHome.persist());

				zoneList.getZone().setAgency(agencyHome.getInstance());
				assert zoneList.getResultList().contains(zoneHome.getInstance());

				zoneList.getZone().setZoneName(TestConstants.TEST_ZONE_NAME_1);
				zoneList.refresh();
				assert zoneList.getResultList().contains(zoneHome.getInstance());
				
				assert zoneHome.isManaged();
				assert zoneHome.getZoneZoneId() != null;
				Integer zoneId = zoneHome.getZoneZoneId();
				assert zoneHome.getInstance().getZoneId() == zoneId.intValue();

				zoneHome.clearInstance();
				assert zoneHome.getDefinedInstance() == null;
				zoneHome.load();
				assert zoneHome.getDefinedInstance() == null;
				zoneHome.setZoneZoneId(zoneId);
				zoneHome.load();

				assert zoneHome.getDefinedInstance() != null;
				assert TestConstants.TEST_ZONE_NAME_1.equals(zoneHome.getInstance().getZoneName());

				zoneHome.getInstance().setZoneName("foo");

				assert TestConstants.UPDATED.equals(zoneHome.update());
				
				assert zoneHome.getDefinedInstance() != null;
				assert !TestConstants.TEST_ZONE_NAME_1.equals(zoneHome.getInstance().getZoneName());

				zoneList.getZone().setZoneName("Totally Bogus Zone Name");
				zoneList.refresh();
				assert zoneList.getResultList().isEmpty();
				assert zoneList.getLastResult() == -1;
				zoneList.getZone().setZoneName("foo");
				zoneList.refresh();
				assert zoneList.getLastResult() == 0;
				assert zoneList.getResultList().contains(zoneHome.getInstance());
				
				assert TestConstants.REMOVED.equals(zoneHome.remove());
			}
		}.run();
	}

//	@Test
//	public void testUpdate() throws Exception {
//		new ComponentTest() {
//			@Override
//			protected void testComponents() throws Exception {
//			}
//		}.run();
//	}

//	@Test
//	public void testRemove() throws Exception {
//		new ComponentTest() {
//			@Override
//			protected void testComponents() throws Exception {
//			}
//		}.run();
//	}
}
