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

import org.ideaproject.Constants;
import org.ideaproject.action.ValidationTableBean;
import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityhome.FareHome;
import org.ideaproject.action.entityquery.FareList;
import org.ideaproject.model.PaymentMethod;
import org.ideaproject.model.TransferLimit;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.mock.SeamTest;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class FareCRUDTest extends SeamTest {
	private static final String BOGUS_BLOCK_LABEL = "BOGUS";

	@org.testng.annotations.BeforeClass
	public void setup() {
//		agencyGroupHome = (AgencyGroupHome) getInstance("#agencyGroupHome");
//		agencyGroupHome.setAgencyGroupAgencyGroupId(AG_ID);
	}

	@Test
	public void testCreate() throws Exception {
		new ComponentTest() {
			@Override
			protected void testComponents() throws Exception {
				UserTransaction tx = getUserTransaction();
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				ValidationTableBean validationTableBean = (ValidationTableBean) getInstance("validationTableBean");
				FareHome fareHome = (FareHome) getInstance("fareHome");
				FareList fareList = (FareList) getInstance("fareList");

				setValue("#{credentials.username}", TestConstants.ADMIN_USER_USERNAME);
				setValue("#{credentials.password}", TestConstants.ADMIN_USER_PASSWORD);
				invokeMethod("#{identity.login}");

				PaymentMethod paymentMethod = validationTableBean.getPaymentMethods().get(0);
				assert paymentMethod != null;
				
				TransferLimit transferLimit = validationTableBean.getTransferLimits().get(0);
				assert transferLimit != null;

				agencyHome.setAgencyAgencyId(TestConstants.DEFAULT_AGENCY_ID);
				agencyHome.load();
				
				assert fareHome.isWired();
				assert !fareHome.isManaged();

				fareHome.getInstance().setAgency(agencyHome.getInstance());
				fareHome.getInstance().setPaymentMethod(paymentMethod);
				fareHome.getInstance().setTransferLimit(transferLimit);
				fareHome.getInstance().setPrice(TestConstants.TEST_FARE_PRICE);
				fareHome.getInstance().setCurrencyType(Constants.DEFAULT_CURRENCY_TYPE);
				fareHome.getInstance().setTransferDuration(TestConstants.TEST_FARE_TRANSFER_DURATION);

				tx.begin();
				assert TestConstants.PERSISTED.equals(fareHome.persist());
				tx.commit();

				fareList.getFare().setAgency(agencyHome.getInstance());
				assert fareList.getResultList().contains(fareHome.getInstance());

				fareList.getFare().setPaymentMethod(paymentMethod);
				fareList.refresh();
				assert fareList.getResultList().contains(fareHome.getInstance());

				fareList.getFare().setPrice(TestConstants.TEST_FARE_PRICE);
				fareList.refresh();
				assert fareList.getResultList().contains(fareHome.getInstance());

				fareList.getFare().setCurrencyType(Constants.DEFAULT_CURRENCY_TYPE);
				fareList.refresh();
				assert fareList.getResultList().contains(fareHome.getInstance());

				fareList.getFare().setTransferLimit(transferLimit);
				fareList.refresh();
				assert fareList.getResultList().contains(fareHome.getInstance());

				fareList.getFare().setTransferDuration(TestConstants.TEST_FARE_TRANSFER_DURATION);
				fareList.refresh();
				assert fareList.getResultList().contains(fareHome.getInstance());

				Long fareCount = fareList.getCurrentAgencyFareCount();
				assert fareCount != null;
				assert fareCount > 0L;
	
				Long unusedFareCount = fareList.getCurrentAgencyUnusedFareCount();
				assert unusedFareCount != null;
				assert unusedFareCount > 0L;
				assert unusedFareCount <= fareCount;

				fareList.getFare().setAgency(agencyHome.getInstance());
				fareList.getFare().setPrice(2.5); // DAMMIT--no way to clear this
				fareList.getFare().setCurrencyType(null);
				fareList.getFare().setPaymentMethod(null);
				fareList.getFare().setTransferLimit(null);
				fareList.getFare().setTransferDuration(null);
				fareList.setHasRules(Boolean.TRUE);

				assert !fareList.getResultList().isEmpty();
				
				Integer fareId = fareHome.getFareFareId();
				assert fareId != null;
				assert fareHome.isManaged();

				fareHome.clearInstance();
				assert fareHome.getDefinedInstance() == null;
				fareHome.load();
				assert fareHome.getDefinedInstance() == null;
				fareHome.setFareFareId(fareId);
				fareHome.load();

				assert fareHome.getDefinedInstance() != null;
				assert TestConstants.TEST_FARE_TRANSFER_DURATION.equals(fareHome.getInstance().getTransferDuration());

				tx.begin();
				assert TestConstants.REMOVED.equals(fareHome.remove());
				tx.commit();
			}
		}.run();
	}

	@Test
	public void testUpdate() throws Exception {
		new ComponentTest() {
			@Override
			protected void testComponents() throws Exception {
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
	}

	@Test
	public void testRemove() throws Exception {
		new ComponentTest() {
			@Override
			protected void testComponents() throws Exception {
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
	}
	
//	private Integer createBlock(BlockHome blockHome, String blockLabel) {
//		blockHome.clearInstance();
//		blockHome.getInstance().setBlockLabel(blockLabel);
//		blockHome.persist();
//		return blockHome.getBlockBlockId();
//	}
}
