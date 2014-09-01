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

import javax.transaction.UserTransaction;

import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityhome.ServiceScheduleBoundHome;
import org.ideaproject.action.entityhome.ServiceScheduleGroupHome;
import org.ideaproject.action.entityquery.ServiceScheduleBoundList;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.mock.SeamTest;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class ServiceScheduleBoundCRUDTest extends SeamTest {

//	private EntityManagerFactory emf;
//
//	@org.testng.annotations.BeforeClass
//	public void setup() {
//		emf = Persistence.createEntityManagerFactory("datafeeder");
//	}
//
//	@org.testng.annotations.AfterClass
//	public void teardown() {
//		emf.close();
//	}

	@Test
	public void testCreate() throws Exception {
		new ComponentTest() {
			@Override
			protected void testComponents() throws Exception {
				UserTransaction tx = getUserTransaction();
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				ServiceScheduleGroupHome serviceScheduleGroupHome = 
					(ServiceScheduleGroupHome) getInstance("serviceScheduleGroupHome");
				ServiceScheduleBoundHome serviceScheduleBoundHome =
					(ServiceScheduleBoundHome) getInstance("serviceScheduleBoundHome");
				ServiceScheduleBoundList serviceScheduleBoundList = 
					(ServiceScheduleBoundList) getInstance("serviceScheduleBoundList");

				setValue("#{credentials.username}", TestConstants.ADMIN_USER_USERNAME);
				setValue("#{credentials.password}", TestConstants.ADMIN_USER_PASSWORD);
				invokeMethod("#{identity.login}");

				setValue("#{agencyHome.agencyAgencyId}", TestConstants.DEFAULT_AGENCY_ID);
				invokeMethod("#{agencyHome.load}");

				serviceScheduleGroupHome.setServiceScheduleGroupServiceScheduleGroupId(TestConstants.DEFAULT_SERVICE_SCHEDULE_GROUP_ID);
				serviceScheduleGroupHome.load();
				assert serviceScheduleGroupHome.getDefinedInstance() != null;

				serviceScheduleBoundHome.getInstance().setServiceScheduleGroup(serviceScheduleGroupHome.getInstance());
				serviceScheduleBoundHome.getInstance().setStartDate(TestConstants.TEST_SERVICE_SCHEDULE_BOUND_STARTDATE);
				serviceScheduleBoundHome.getInstance().setEndDate(TestConstants.TEST_SERVICE_SCHEDULE_BOUND_ENDDATE);

				tx.begin();
				assert TestConstants.PERSISTED.equals(serviceScheduleBoundHome.persist());
				tx.commit();

				serviceScheduleGroupHome.getEntityManager().refresh(serviceScheduleGroupHome.getInstance());

				assert serviceScheduleGroupHome.getInstance().getServiceScheduleBounds().contains(serviceScheduleBoundHome.getInstance());
				assert serviceScheduleGroupHome.getInstance().isCurrent();

				serviceScheduleBoundList.getServiceScheduleBound().setServiceScheduleGroup(serviceScheduleGroupHome.getInstance());
				assert serviceScheduleBoundList.getResultList().contains(serviceScheduleBoundHome.getInstance());
				
				serviceScheduleBoundList.getServiceScheduleBound().setStartDate(TestConstants.TEST_SERVICE_SCHEDULE_BOUND_STARTDATE);
				serviceScheduleBoundList.getServiceScheduleBound().setEndDate(TestConstants.TEST_SERVICE_SCHEDULE_BOUND_ENDDATE);
				serviceScheduleBoundList.refresh();
				assert serviceScheduleBoundList.getResultList().contains(serviceScheduleBoundHome.getInstance());
								
				assert serviceScheduleBoundHome.isManaged();
				assert serviceScheduleBoundHome.getServiceScheduleBoundServiceScheduleBoundsId() != null;
				Integer boundsId = serviceScheduleBoundHome.getServiceScheduleBoundServiceScheduleBoundsId();
				assert serviceScheduleBoundHome.getInstance().getServiceScheduleBoundsId() == boundsId.intValue();

				serviceScheduleBoundHome.clearInstance();
				assert serviceScheduleBoundHome.getDefinedInstance() == null;
				serviceScheduleBoundHome.load();
				assert serviceScheduleBoundHome.getDefinedInstance() == null;
				serviceScheduleBoundHome.setServiceScheduleBoundServiceScheduleBoundsId(boundsId);
				serviceScheduleBoundHome.load();

				assert serviceScheduleBoundHome.getDefinedInstance() != null;
				assert TestConstants.TEST_SERVICE_SCHEDULE_BOUND_STARTDATE.equals(serviceScheduleBoundHome.getInstance().getStartDate());
				assert TestConstants.TEST_SERVICE_SCHEDULE_BOUND_ENDDATE.equals(serviceScheduleBoundHome.getInstance().getEndDate());

//				serviceScheduleGroupHome.getInstance().setServiceScheduleGroupLabel("foo");
//				tx.begin();
//				assert TestConstants.UPDATED.equals(serviceScheduleGroupHome.update());
//				tx.commit();
//				
//				assert serviceScheduleGroupHome.getDefinedInstance() != null;
//				assert !TestConstants.TEST_SERVICE_SCHEDULE_GROUP_LABEL.equals(serviceScheduleGroupHome.getInstance().getServiceScheduleGroupLabel());
//
//				serviceScheduleGroupList.getServiceScheduleGroup().setServiceScheduleGroupLabel(TestConstants.TEST_SERVICE_SCHEDULE_GROUP_LABEL);
//				serviceScheduleGroupList.refresh();
//				assert serviceScheduleGroupList.getResultList().isEmpty();
//				serviceScheduleGroupList.getServiceScheduleGroup().setServiceScheduleGroupLabel("foo");
//				serviceScheduleGroupList.refresh();
//				assert serviceScheduleGroupList.getResultList().contains(serviceScheduleGroupHome.getInstance());
//				
//				tx.begin();
//				assert TestConstants.REMOVED.equals(serviceScheduleGroupHome.remove());
//				tx.commit();
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
