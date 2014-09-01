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
package org.ideaproject.action.entityhome;

import java.util.Date;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.framework.EntityNotFoundException;
import org.jboss.seam.mock.SeamTest;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class UserHomeTest extends SeamTest {
	private static final String USER_EMAIL = "test@test.com";
	private static final String USER_FNAME = "John";
	private static final String USER_LNAME = "Tester";
	private static final String USER_PWD = "Password";
	private static final String ZONE_NAME = "Test Zone";

	private static final String PERSISTED = "persisted";
	private static final String UPDATED = "updated";
	private static final String REMOVED = "removed";

	private EntityManagerFactory emf;

	@org.testng.annotations.BeforeClass
	public void setup() {
		emf = Persistence.createEntityManagerFactory("datafeeder");
	}

	@org.testng.annotations.AfterClass
	public void teardown() {
		emf.close();
	}

	@Test
	@Transactional
	public void testCreate() throws Exception {
		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				setValue("#{credentials.username}", "admin");
				setValue("#{credentials.password}", "");
				invokeAction("#{identity.login}");
				assert getValue("#{identity.loggedIn}").equals(true);
			}
		}.run();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				boolean exceptionThrown = false;
				setValue("#{agencyHome.agencyAgencyId}", 11);
				invokeMethod("#{agencyHome.load}");
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");


				UserHome userHome = (UserHome) getInstance("userHome");
				assert userHome.isWired();
				assert !userHome.isManaged();

				userHome.getInstance().setEmail(USER_EMAIL);
				userHome.getInstance().setEnabled(true);
				userHome.getInstance().setFirstName(USER_FNAME);
				userHome.getInstance().setLastName(USER_LNAME);
				userHome.getInstance().setRegistrationDate(new Date());
				setValue("#{userAction.confirm}", USER_PWD);
				setValue("#{userAction.password}", USER_PWD);
//				userHome.getInstance().setShaHashedPass(USER_PWD);
//				userHome.getInstance().getAgencyAffiliations().add(agencyHome.getInstance());
				
				assert PERSISTED.equals(invokeMethod("#{userHome.persist}"));

				assert userHome.isManaged();
				assert userHome.getUserUserId() != null;
				Integer userId = userHome.getUserUserId();
				assert userHome.getInstance().getUserId() == userId.intValue();

				userHome.clearInstance();
				assert userHome.getDefinedInstance() == null;
				userHome.load();
				assert userHome.getDefinedInstance() == null;
				userHome.setUserUserId(userId);
				userHome.load();
				assert userHome.getDefinedInstance() != null;

				userHome.getInstance().getAgencyAffiliations().add(agencyHome.getInstance());
				assert UPDATED.equals(invokeMethod("#{userHome.update}"));

				userHome.clearInstance();
				assert userHome.getDefinedInstance() == null;
				userHome.load();
				assert userHome.getDefinedInstance() == null;
				userHome.setUserUserId(userId);
				userHome.load();
				assert userHome.getDefinedInstance() != null;

				assert !userHome.getInstance().getAgencyAffiliations().isEmpty();
				agencyHome.clearInstance();
				agencyHome.setAgencyAgencyId(11);
				assert agencyHome.getInstance().getAgencyUsers().contains(userHome.getInstance());
				
				userHome.getInstance().setFirstName("foo");
				assert UPDATED.equals(invokeMethod("#{userHome.update}"));

				userHome.clearInstance();
				assert userHome.getDefinedInstance() == null;
				userHome.load();
				assert userHome.getDefinedInstance() == null;
				userHome.setUserUserId(userId);
				userHome.load();
				assert userHome.getDefinedInstance() != null;

				assert "foo".equals(userHome.getInstance().getFirstName());
				assert !userHome.getInstance().getAgencyAffiliations().isEmpty();
				agencyHome.clearInstance();
				agencyHome.setAgencyAgencyId(11);
				assert agencyHome.getInstance().getAgencyUsers().contains(userHome.getInstance());

				assert REMOVED.equals(invokeMethod("#{userHome.remove}"));

				userHome.clearInstance();
				assert userHome.getDefinedInstance() == null;
				userHome.load();
				assert userHome.getDefinedInstance() == null;
				userHome.setUserUserId(userId);
				try {
					userHome.load();
				} catch (EntityNotFoundException e) {
					exceptionThrown = true;
				}
				assert exceptionThrown;
				assert userHome.getDefinedInstance() == null;
			}
		}.run();
//		new ComponentTest() {
//			@Override
//			protected void testComponents() throws Exception {
//				UserTransaction tx = getUserTransaction();
//
////				UserAction userAction = (UserAction) Contexts.getConversationContext().get("userAction");
////				IdentityManager identityManager = (IdentityManager) Contexts.getConversationContext().get("identityManager");
//				IdentityManager identityManager = IdentityManager.instance();
//				Contexts.getConversationContext().set("identityManager", identityManager);
//				UserAction userAction = new UserAction();
//				Contexts.getConversationContext().set("userAction", userAction);
//
//				setValue("#{credentials.username}", "admin");
//				setValue("#{credentials.password}", "");
//				invokeMethod("#{identity.login}");
//
//				setValue("#{agencyHome.agencyAgencyId}", 11);
//				invokeMethod("#{agencyHome.load}");
//				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
//
//
//				UserHome userHome = (UserHome) getInstance("userHome");
//				assert userHome.isWired();
//				assert !userHome.isManaged();
//
//				userHome.getInstance().setEmail(USER_EMAIL);
//				userHome.getInstance().setEnabled(true);
//				userHome.getInstance().setFirstName(USER_FNAME);
//				userHome.getInstance().setLastName(USER_LNAME);
//				userHome.getInstance().setRegistrationDate(new Date());
//				userAction.setConfirm(USER_PWD);
//				userAction.setPassword(USER_PWD);
////				userHome.getInstance().setShaHashedPass(USER_PWD);
//				userHome.getInstance().getAgencyAffiliations().add(agencyHome.getInstance());
//				
//				tx.begin();
//				assert "failure".equals(userHome.persist());
////				assert PERSISTED.equals(userHome.persist());
//				tx.commit();
//
//				assert userHome.isManaged();
//				assert userHome.getUserUserId() != null;
//				Integer userId = userHome.getUserUserId();
//				assert userHome.getInstance().getUserId() == userId.intValue();
//
//				userHome.clearInstance();
//				assert userHome.getDefinedInstance() == null;
//				userHome.load();
//				assert userHome.getDefinedInstance() == null;
////				zoneHome.setZoneZoneId(zoneId);
////				zoneHome.load();
////
////				assert zoneHome.getDefinedInstance() != null;
////				assert ZONE_NAME.equals(zoneHome.getInstance().getZoneName());
////
////				tx.begin();
////				assert REMOVED.equals(zoneHome.remove());
////				tx.commit();
//			}
//		}.run();
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
	
	private Integer createBlock(BlockHome blockHome, String blockLabel) {
		blockHome.createInstance();
		blockHome.getInstance().setBlockLabel(blockLabel);
		blockHome.persist();
		return blockHome.getBlockBlockId();
	}
}
