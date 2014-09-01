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
import org.ideaproject.action.entityhome.DirectionHome;
import org.ideaproject.action.entityquery.DirectionList;
import org.ideaproject.test.AbstractAdminLoginSeamTest;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class DirectionCRUDTest extends AbstractAdminLoginSeamTest {

	@Test
	@Transactional
	public void testCreate() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				DirectionHome directionHome = (DirectionHome) getInstance("directionHome");
				DirectionList directionList = (DirectionList) getInstance("directionList");

				setValue("#{credentials.username}", TestConstants.ADMIN_USER_USERNAME);
				setValue("#{credentials.password}", TestConstants.ADMIN_USER_PASSWORD);
				invokeMethod("#{identity.login}");

				setValue("#{agencyHome.agencyAgencyId}", TestConstants.DEFAULT_AGENCY_ID);
				invokeMethod("#{agencyHome.load}");

				assert directionHome.isWired();
				assert !directionHome.isManaged();

				directionHome.getInstance().setAgency(agencyHome.getInstance());
				directionHome.getInstance().setDirectionLabel(TestConstants.TEST_DIRECTION_LABEL);
				directionHome.getInstance().setInboundFlag(Boolean.TRUE);

				assert TestConstants.PERSISTED.equals(directionHome.persist());

				directionList.getDirection().setAgency(agencyHome.getInstance());
				directionList.getDirection().setDirectionLabel(TestConstants.TEST_DIRECTION_LABEL);
				assert directionList.getResultList().contains(directionHome.getInstance());
				
				Integer directionId = directionHome.getDirectionDirectionId();
				assert directionId != null;
				assert directionId.intValue() == directionHome.getInstance().getDirectionId();
				assert directionHome.isManaged();

				directionHome.clearInstance();
				assert directionHome.getDefinedInstance() == null;
				directionHome.load();
				assert directionHome.getDefinedInstance() == null;
				directionHome.setDirectionDirectionId(directionId);
				directionHome.load();

				assert directionHome.getDefinedInstance() != null;
				assert TestConstants.TEST_DIRECTION_LABEL.equals(directionHome.getInstance().getDirectionLabel());

				directionHome.clearInstance();
				assert directionHome.getDefinedInstance() == null;
				directionHome.load();
				assert directionHome.getDefinedInstance() == null;
				directionHome.setDirectionDirectionId(directionId);
				directionHome.load();

				directionHome.getInstance().setInboundFlag(Boolean.FALSE);

				assert TestConstants.UPDATED.equals(directionHome.update());

				directionHome.clearInstance();
				assert directionHome.getDefinedInstance() == null;
				directionHome.load();
				assert directionHome.getDefinedInstance() == null;
				directionHome.setDirectionDirectionId(directionId);
				directionHome.load();
				assert directionHome.getDefinedInstance() != null;
				assert Boolean.FALSE.equals(directionHome.getInstance().getInboundFlag());

				assert TestConstants.REMOVED.equals(directionHome.remove());
			}
		}.run();

		logoutAdmin();
		
	}
}
