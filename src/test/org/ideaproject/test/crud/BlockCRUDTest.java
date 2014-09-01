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
import org.ideaproject.action.entityhome.BlockHome;
import org.ideaproject.action.entityquery.BlockList;
import org.ideaproject.test.AbstractAdminLoginSeamTest;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class BlockCRUDTest extends AbstractAdminLoginSeamTest {
	private static final String BOGUS_BLOCK_LABEL = "BOGUS";

	@Test
	@Transactional
	public void testCreate() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				BlockHome blockHome = (BlockHome) getInstance("blockHome");
				BlockList blockList = (BlockList) getInstance("blockList");

				setValue("#{credentials.username}", TestConstants.ADMIN_USER_USERNAME);
				setValue("#{credentials.password}", TestConstants.ADMIN_USER_PASSWORD);
				invokeMethod("#{identity.login}");

				setValue("#{agencyHome.agencyAgencyId}", TestConstants.DEFAULT_AGENCY_ID);
				invokeMethod("#{agencyHome.load}");

				assert blockHome.isWired();
				assert !blockHome.isManaged();

				blockHome.getInstance().setAgency(agencyHome.getInstance());
				blockHome.getInstance().setBlockLabel(TestConstants.TEST_BLOCK_LABEL);

				assert TestConstants.PERSISTED.equals(blockHome.persist());

				blockList.getBlock().setAgency(agencyHome.getInstance());
				blockList.getBlock().setBlockLabel(TestConstants.TEST_BLOCK_LABEL);
				assert blockList.getResultList().contains(blockHome.getInstance());
				
				Integer blockId = blockHome.getBlockBlockId();
////				Integer blockId = createBlock(blockHome, BLOCK_LABEL);
				assert blockId != null;
				assert blockHome.isManaged();

				blockHome.clearInstance();
				assert blockHome.getDefinedInstance() == null;
				blockHome.load();
				assert blockHome.getDefinedInstance() == null;
				blockHome.setBlockBlockId(blockId);
				blockHome.load();

				assert blockHome.getDefinedInstance() != null;
				assert TestConstants.TEST_BLOCK_LABEL.equals(blockHome.getInstance().getBlockLabel());

				assert TestConstants.REMOVED.equals(blockHome.remove());
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
//				AgencyGroupHome agencyGroupHome = (AgencyGroupHome) getInstance("agencyGroupHome");
//				assert agencyGroupHome.isWired();
//				assert !agencyGroupHome.isManaged();
//				Integer ahId = createAgencyHome(agencyGroupHome, AG_NAME);
//				assert ahId != null;
//				assert agencyGroupHome.isManaged();
//
//				agencyGroupHome.clearInstance();
//				agencyGroupHome.setAgencyGroupAgencyGroupId(ahId);
//				agencyGroupHome.load();
//				agencyGroupHome.getInstance().setGroupName(BOGUS_AG_NAME);
//				agencyGroupHome.update();
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
//				assert BOGUS_AG_NAME.equals(agencyGroupHome.getInstance().getGroupName());
////				assert agencyGroupHome.getInstance().getDateLastModified() != null;
////				assert agencyGroupHome.getInstance().getUserLastModified() != null;
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
	
//	private Integer createBlock(BlockHome blockHome, String blockLabel) {
//		blockHome.clearInstance();
//		blockHome.getInstance().setBlockLabel(blockLabel);
//		blockHome.persist();
//		return blockHome.getBlockBlockId();
//	}
}
