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
import org.ideaproject.action.entityquery.AgencyList;
import org.ideaproject.action.entityquery.FullAgencyList;
import org.ideaproject.model.Agency;
import org.ideaproject.model.User;
import org.ideaproject.test.AbstractAdminLoginSeamTest;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class AgencyCRUDTest extends AbstractAdminLoginSeamTest {

	@Test
	@Transactional
	public void testCreateAndRemove() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyGroupHome agencyGroupHome = (AgencyGroupHome) getInstance("agencyGroupHome");
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				AgencyList agencyList = (AgencyList) getInstance("agencyList");
				FullAgencyList fullAgencyList = (FullAgencyList) getInstance("fullAgencyList");

				agencyGroupHome.setAgencyGroupAgencyGroupId(TestConstants.DEFAULT_AGENCY_GROUP_ID);
				agencyGroupHome.load();
				assert agencyGroupHome.getDefinedInstance() != null;
				
				User authUser = (User) getValue("#{authenticatedUser}");
				assert authUser != null;

				invokeMethod("#{agencyHome.load}");

				assert invokeMethod("#{agencyHome.getDefinedInstance}") == null;

				agencyHome.getInstance().getAgencyGroups().add(agencyGroupHome.getInstance());
				agencyHome.getInstance().setAgencyName(TestConstants.TEST_AGENCY_NAME);
				agencyHome.getInstance().setAgencyShortName(TestConstants.TEST_AGENCY_SHORT_NAME);
				agencyHome.getInstance().setAgencyLanguage(TestConstants.TEST_AGENCY_LANG);
				agencyHome.getInstance().setAgencyTimezone(TestConstants.TEST_AGENCY_TZ);
				agencyHome.getInstance().setAgencyUrl(TestConstants.TEST_AGENCY_URL);

				assert TestConstants.PERSISTED.equals(invokeMethod("#{agencyHome.persist}"));

				Agency agency = agencyHome.getInstance();
				assert agency != null;
				assert agencyHome.isWired();
				assert agencyHome.isManaged();

				agencyGroupHome.getEntityManager().refresh(agencyGroupHome.getInstance());
				assert agencyGroupHome.getInstance().getAgencies().contains(agencyHome.getInstance());

				assert agency.getAgencyUsers() != null;
				assert !agency.getAgencyUsers().isEmpty();

				Integer agencyId = agencyHome.getAgencyAgencyId();
				assert agencyId != null;

				agencyHome.clearInstance();
				assert agencyHome.getDefinedInstance() == null;
				agencyHome.setAgencyAgencyId(agencyId);
				agencyHome.load();
				assert agencyHome.getDefinedInstance() != null;

				agencyList.getAgency().setAgencyName(TestConstants.TEST_AGENCY_NAME);
				agencyList.getAgency().setAgencyShortName(TestConstants.TEST_AGENCY_SHORT_NAME);
				agencyList.getAgency().setAgencyLanguage(TestConstants.TEST_AGENCY_LANG);
				agencyList.getAgency().setAgencyTimezone(TestConstants.TEST_AGENCY_TZ);
				agencyList.getAgency().setAgencyUrl(TestConstants.TEST_AGENCY_URL);
				fullAgencyList.getAgency().setAgencyName(TestConstants.TEST_AGENCY_NAME);
				fullAgencyList.getAgency().setAgencyShortName(TestConstants.TEST_AGENCY_SHORT_NAME);
				fullAgencyList.getAgency().setAgencyLanguage(TestConstants.TEST_AGENCY_LANG);
				fullAgencyList.getAgency().setAgencyTimezone(TestConstants.TEST_AGENCY_TZ);
				fullAgencyList.getAgency().setAgencyUrl(TestConstants.TEST_AGENCY_URL);

				assert fullAgencyList.getResultList().contains(agencyHome.getDefinedInstance());
				assert fullAgencyList.getAllAgencies().contains(agencyHome.getInstance());
				assert fullAgencyList.getAvailableTimezoneIds().contains(agencyHome.getDefinedInstance().getAgencyTimezone());

				assert agencyList.getResultList().contains(agencyHome.getDefinedInstance());
				assert agencyList.getAllAgencies().contains(agencyHome.getDefinedInstance());
				assert agencyList.getAvailableTimezoneIds().contains(agencyHome.getDefinedInstance().getAgencyTimezone());
				assert agencyList.getAvailableTimezoneIds().contains(agencyHome.getDefinedInstance().getAgencyTimezone());

				assert TestConstants.REMOVED.equals(invokeMethod("#{agencyHome.remove}"));
				invokeMethod("#{agencyGroupHome.clearInstance}");
				assert getValue("#{agencyHome.managed}").equals(false);
			}
		}.run();

		logoutAdmin();
	}

	@Test
	public void testUpdate() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				setValue("#{agencyHome.agencyAgencyId}", TestConstants.DEFAULT_AGENCY_ID);
				invokeMethod("#{agencyHome.load}");
				assert invokeMethod("#{agencyHome.getDefinedInstance}") != null;

				setValue("#{agencyHome.instance.agencyName}", TestConstants.BOGUS_AGENCY_NAME);
				assert TestConstants.UPDATED.equals(invokeMethod("#{agencyHome.update}"));
				
				invokeMethod("#{agencyHome.clearInstance}");
				assert invokeMethod("#{agencyHome.getDefinedInstance}") == null;

				setValue("#{agencyHome.agencyAgencyId}", TestConstants.DEFAULT_AGENCY_ID);
				invokeMethod("#{agencyHome.load}");
				assert invokeMethod("#{agencyHome.getDefinedInstance}") != null;

				Agency agency = (Agency) invokeMethod("#{agencyHome.getInstance}");
				assert agency != null;
				assert TestConstants.BOGUS_AGENCY_NAME.equals(agency.getAgencyName());
			}
		}.run();

		logoutAdmin();
	}

	@Test
	public void testRemove() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				invokeMethod("#{agencyHome.load}");

				assert invokeMethod("#{agencyHome.getDefinedInstance}") == null;
				
				setValue("#{agencyHome.instance.agencyName}", TestConstants.TEST_AGENCY_NAME);
				setValue("#{agencyHome.instance.agencyShortName}", TestConstants.TEST_AGENCY_SHORT_NAME);
				setValue("#{agencyHome.instance.agencyLanguage}", TestConstants.TEST_AGENCY_LANG);
				setValue("#{agencyHome.instance.agencyTimezone}", TestConstants.TEST_AGENCY_TZ);
				setValue("#{agencyHome.instance.agencyUrl}", TestConstants.TEST_AGENCY_URL);

				assert TestConstants.PERSISTED.equals(invokeMethod("#{agencyHome.persist}"));
				Agency agency = (Agency) invokeMethod("#{agencyHome.getInstance}");
				assert agency != null;

//				setValue("#{credentials.username}", "admin");
//				setValue("#{credentials.password}", "");
//				invokeMethod("#{identity.login}");
//
//				invokeMethod("#{agencyHome.clearInstance}");
//
//				assert invokeMethod("#{agencyHome.getDefinedInstance}") == null;
//				
//				
//				setValue("#{agencyHome.instance.agencyName}", AGENCY_NAME);
//				setValue("#{agencyHome.instance.agencyShortName}", AGENCY_SHORT_NAME);
//				setValue("#{agencyHome.instance.agencyLanguage}", AGENCY_LANG);
//				setValue("#{agencyHome.instance.agencyTimezone}", AGENCY_TZ);
//				setValue("#{agencyHome.instance.agencyUrl}", AGENCY_URL);
//
//				User currentUser = (User) Contexts.getSessionContext().get(JpaIdentityStore.AUTHENTICATED_USER);
//				assert currentUser != null;
//				assert PERSISTED.equals(invokeMethod("#{agencyHome.persist}"));
//				Agency agency = (Agency) invokeMethod("#{agencyHome.getInstance}");
//				assert agency != null;
//
//				setValue("#{agencyHome.agencyAgencyId}", TestConstants.DEFAULT_AGENCY_ID);
//				invokeMethod("#{agencyHome.load}");
//				assert invokeMethod("#{agencyHome.getDefinedInstance}") != null;
//
//				assert REMOVED.equals(invokeMethod("#{agencyHome.remove}"));
//				invokeMethod("#{agencyHome.clearInstance}");
//				
//				setValue("#{agencyHome.agencyAgencyId}", TestConstants.DEFAULT_AGENCY_ID);
//				assert invokeMethod("#{agencyHome.getDefinedInstance}") == null;

				//				AgencyGroupHome agencyGroupHome = (AgencyGroupHome) getInstance("agencyGroupHome");
//				assert agencyGroupHome.isWired();
//				assert !agencyGroupHome.isManaged();
//				Integer ahId = createAgencyHome(agencyGroupHome, AG_NAME);
//				assert ahId != null;
//				assert agencyGroupHome.isManaged();
//				
//				agencyGroupHome.clearInstance();
//				assert agencyGroupHome.getAgencyGroupAgencyGroupId() == null;
//				agencyGroupHome.load();
//				assert !agencyGroupHome.isManaged();
//				assert agencyGroupHome.getDefinedInstance() == null;
//				agencyGroupHome.setAgencyGroupAgencyGroupId(ahId);
//				agencyGroupHome.load();
//				assert agencyGroupHome.isManaged();
//				assert agencyGroupHome.getDefinedInstance() != null;
//				assert AG_NAME.equals(agencyGroupHome.getInstance().getGroupName());
////				assert agencyGroupHome.getInstance().getDateLastModified() != null;
////				assert agencyGroupHome.getInstance().getUserLastModified() != null;
//
//				agencyGroupHome.remove();
//				agencyGroupHome.clearInstance();
//				assert !agencyGroupHome.isManaged();
				
			}
		}.run();

		logoutAdmin();
	}
}
