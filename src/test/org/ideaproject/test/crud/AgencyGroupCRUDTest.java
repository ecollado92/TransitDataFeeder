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

import org.ideaproject.action.entityhome.AgencyGroupHome;
import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityquery.AgencyGroupList;
import org.ideaproject.test.AbstractAdminLoginSeamTest;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class AgencyGroupCRUDTest extends AbstractAdminLoginSeamTest {
	private static final String BOGUS_AG_NAME = "BOGUS";

	@Test
	@Transactional
	public void testCreate() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyGroupHome agencyGroupHome = (AgencyGroupHome) getInstance("agencyGroupHome");
				AgencyGroupList agencyGroupList = (AgencyGroupList) getInstance("agencyGroupList");

				assert agencyGroupHome.isWired();
				assert !agencyGroupHome.isManaged();
				Integer ahId = createAgencyHome(agencyGroupHome, TestConstants.TEST_AGENCY_GROUP_NAME);
				assert ahId != null;
				assert agencyGroupHome.getDefinedInstance() != null;
				assert agencyGroupHome.isManaged();

				agencyGroupList.getAgencyGroup().setGroupName(TestConstants.TEST_AGENCY_GROUP_NAME);
				assert agencyGroupList.getResultList().contains(agencyGroupHome.getInstance());

				agencyGroupList.getAgencyGroup().setGroupName(BOGUS_AG_NAME);
				agencyGroupList.refresh();
				assert !agencyGroupList.getResultList().contains(agencyGroupHome.getInstance());

				assert agencyGroupList.getAllAgencyGroups().contains(agencyGroupHome.getInstance());

				agencyGroupHome.clearInstance();
				assert agencyGroupHome.getAgencyGroupAgencyGroupId() == null;
				agencyGroupHome.load();
				assert !agencyGroupHome.isManaged();
				assert agencyGroupHome.getDefinedInstance() == null;
				agencyGroupHome.setAgencyGroupAgencyGroupId(ahId);
				agencyGroupHome.load();
				assert agencyGroupHome.isManaged();
				assert agencyGroupHome.getDefinedInstance() != null;
				assert TestConstants.TEST_AGENCY_GROUP_NAME.equals(agencyGroupHome.getInstance().getGroupName());
//				assert agencyGroupHome.getInstance().getDateLastModified() != null;
//				assert agencyGroupHome.getInstance().getUserLastModified() != null;
			}
		}.run();

		logoutAdmin();
	}

	@Test
	@Transactional
	public void testUpdate() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				AgencyGroupHome agencyGroupHome = (AgencyGroupHome) getInstance("agencyGroupHome");

				agencyHome.setAgencyAgencyId(TestConstants.DEFAULT_AGENCY_ID);
				agencyHome.load();

				assert agencyGroupHome.isWired();
				assert !agencyGroupHome.isManaged();
				Integer ahId = createAgencyHome(agencyGroupHome, TestConstants.TEST_AGENCY_GROUP_NAME);
				assert ahId != null;
				assert agencyGroupHome.isManaged();

				agencyGroupHome.clearInstance();
				agencyGroupHome.setAgencyGroupAgencyGroupId(ahId);
				agencyGroupHome.load();
				agencyGroupHome.getInstance().setGroupName(BOGUS_AG_NAME);
				agencyGroupHome.getInstance().getAgencies().add(agencyHome.getInstance());
				assert TestConstants.UPDATED.equals(agencyGroupHome.update());

				agencyHome.getEntityManager().refresh(agencyHome.getInstance());
				assert agencyHome.getInstance().getAgencyGroups().contains(agencyGroupHome.getInstance());

				agencyGroupHome.clearInstance();
				assert agencyGroupHome.getAgencyGroupAgencyGroupId() == null;
				agencyGroupHome.load();
				assert !agencyGroupHome.isManaged();
				assert agencyGroupHome.getDefinedInstance() == null;
				agencyGroupHome.setAgencyGroupAgencyGroupId(ahId);
				agencyGroupHome.load();
				assert agencyGroupHome.isManaged();
				assert agencyGroupHome.getDefinedInstance() != null;
				assert BOGUS_AG_NAME.equals(agencyGroupHome.getInstance().getGroupName());
//				assert agencyGroupHome.getInstance().getDateLastModified() != null;
//				assert agencyGroupHome.getInstance().getUserLastModified() != null;
			}
		}.run();

		logoutAdmin();
	
	}

	@Test
	@Transactional
	public void testRemove() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyGroupHome agencyGroupHome = (AgencyGroupHome) getInstance("agencyGroupHome");
				assert agencyGroupHome.isWired();
				assert !agencyGroupHome.isManaged();
				Integer ahId = createAgencyHome(agencyGroupHome, TestConstants.TEST_AGENCY_GROUP_NAME);
				assert ahId != null;
				assert agencyGroupHome.isManaged();
				
				agencyGroupHome.clearInstance();
				assert agencyGroupHome.getAgencyGroupAgencyGroupId() == null;
				agencyGroupHome.load();
				assert !agencyGroupHome.isManaged();
				assert agencyGroupHome.getDefinedInstance() == null;
				agencyGroupHome.setAgencyGroupAgencyGroupId(ahId);
				agencyGroupHome.load();
				assert agencyGroupHome.isManaged();
				assert agencyGroupHome.getDefinedInstance() != null;
				assert TestConstants.TEST_AGENCY_GROUP_NAME.equals(agencyGroupHome.getInstance().getGroupName());
//				assert agencyGroupHome.getInstance().getDateLastModified() != null;
//				assert agencyGroupHome.getInstance().getUserLastModified() != null;

				assert TestConstants.REMOVED.equals(agencyGroupHome.remove());
				agencyGroupHome.clearInstance();
				assert !agencyGroupHome.isManaged();
				
			}
		}.run();

		logoutAdmin();

	}
	
	private Integer createAgencyHome(AgencyGroupHome agencyGroupHome, String ahName) throws Exception {
		agencyGroupHome.clearInstance();
		agencyGroupHome.getInstance().setGroupName(ahName);

		assert TestConstants.PERSISTED.equals(agencyGroupHome.persist());

		return agencyGroupHome.getAgencyGroupAgencyGroupId();
	}
}
