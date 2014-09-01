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

import java.util.Calendar;

import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityhome.ServiceScheduleGroupHome;
import org.ideaproject.action.entityquery.ServiceScheduleGroupList;
import org.ideaproject.test.AbstractAdminLoginSeamTest;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class ServiceScheduleGroupCRUDTest extends AbstractAdminLoginSeamTest {

	@Test
	@Transactional
	public void testCreate() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				Calendar cal = Calendar.getInstance();
				cal.clear();
				cal.set(Calendar.YEAR, 2010);
				cal.set(Calendar.MONTH, Calendar.JANUARY);
				cal.set(Calendar.DAY_OF_MONTH, 1);
				
				cal.clear();
				cal.set(Calendar.YEAR, 2010);
				cal.set(Calendar.MONTH, Calendar.DECEMBER);
				cal.set(Calendar.DAY_OF_MONTH, 31);

				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				ServiceScheduleGroupHome serviceScheduleGroupHome = 
					(ServiceScheduleGroupHome) getInstance("serviceScheduleGroupHome");
				ServiceScheduleGroupList serviceScheduleGroupList = 
					(ServiceScheduleGroupList) getInstance("serviceScheduleGroupList");

				setValue("#{agencyHome.agencyAgencyId}", TestConstants.DEFAULT_AGENCY_ID);
				invokeMethod("#{agencyHome.load}");

				assert serviceScheduleGroupHome.isWired();
				assert !serviceScheduleGroupHome.isManaged();
				
				serviceScheduleGroupHome.getInstance().setAgency(agencyHome.getInstance());
				serviceScheduleGroupHome.getInstance().setCurrent(true);
				serviceScheduleGroupHome.getInstance().setServiceScheduleGroupLabel(TestConstants.TEST_SERVICE_SCHEDULE_GROUP_LABEL);

				assert TestConstants.PERSISTED.equals(serviceScheduleGroupHome.persist());

				serviceScheduleGroupList.getServiceScheduleGroup().setAgency(agencyHome.getInstance());
				assert serviceScheduleGroupList.getResultList().contains(serviceScheduleGroupHome.getInstance());
				
				serviceScheduleGroupList.getServiceScheduleGroup().setServiceScheduleGroupLabel(TestConstants.TEST_SERVICE_SCHEDULE_GROUP_LABEL);
				serviceScheduleGroupList.refresh();
				assert serviceScheduleGroupList.getResultList().contains(serviceScheduleGroupHome.getInstance());
								
				assert serviceScheduleGroupHome.isManaged();
				assert serviceScheduleGroupHome.getServiceScheduleGroupServiceScheduleGroupId() != null;
				Integer scheduleId = serviceScheduleGroupHome.getServiceScheduleGroupServiceScheduleGroupId();
				assert serviceScheduleGroupHome.getInstance().getServiceScheduleGroupId() == scheduleId.intValue();

				serviceScheduleGroupHome.clearInstance();
				assert serviceScheduleGroupHome.getDefinedInstance() == null;
				serviceScheduleGroupHome.load();
				assert serviceScheduleGroupHome.getDefinedInstance() == null;
				serviceScheduleGroupHome.setServiceScheduleGroupServiceScheduleGroupId(scheduleId);
				serviceScheduleGroupHome.load();

				assert serviceScheduleGroupHome.getDefinedInstance() != null;
				assert TestConstants.TEST_SERVICE_SCHEDULE_GROUP_LABEL.equals(serviceScheduleGroupHome.getInstance().getServiceScheduleGroupLabel());

				serviceScheduleGroupHome.getInstance().setServiceScheduleGroupLabel("foo");

				assert TestConstants.UPDATED.equals(serviceScheduleGroupHome.update());
				
				assert serviceScheduleGroupHome.getDefinedInstance() != null;
				assert !TestConstants.TEST_SERVICE_SCHEDULE_GROUP_LABEL.equals(serviceScheduleGroupHome.getInstance().getServiceScheduleGroupLabel());

				serviceScheduleGroupList.getServiceScheduleGroup().setServiceScheduleGroupLabel(TestConstants.TEST_SERVICE_SCHEDULE_GROUP_LABEL);
				serviceScheduleGroupList.refresh();
				assert serviceScheduleGroupList.getResultList().isEmpty();
				serviceScheduleGroupList.getServiceScheduleGroup().setServiceScheduleGroupLabel("foo");
				serviceScheduleGroupList.refresh();
				assert serviceScheduleGroupList.getResultList().contains(serviceScheduleGroupHome.getInstance());
				
				assert TestConstants.REMOVED.equals(serviceScheduleGroupHome.remove());
			}
		}.run();

		logoutAdmin();
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
