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

import org.ideaproject.action.ValidationTableBean;
import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityhome.StopHome;
import org.ideaproject.action.entityhome.TransferHome;
import org.ideaproject.action.entityquery.TransferList;
import org.ideaproject.model.Stop;
import org.ideaproject.model.TransferType;
import org.ideaproject.test.AbstractAdminLoginSeamTest;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class TransferCRUDTest extends AbstractAdminLoginSeamTest {

	@Test
	@Transactional
	public void testCreate() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				ValidationTableBean validationTableBean = (ValidationTableBean) getInstance("validationTableBean");
				StopHome stopHome = (StopHome) getInstance("stopHome");
				TransferHome transferHome = (TransferHome) getInstance("transfersHome");
				TransferList transferList = (TransferList) getInstance("transferList");

				TransferType transferType = validationTableBean.getTransferTypes().get(0);
				assert transferType != null;

				agencyHome.setAgencyAgencyId(TestConstants.DEFAULT_AGENCY_ID);
				agencyHome.load();

				stopHome.setStopStopId(TestConstants.DEFAULT_STOP_ID_1);
				stopHome.load();
				Stop from = stopHome.getInstance();

				stopHome.setStopStopId(TestConstants.DEFAULT_STOP_ID_2);
				stopHome.load();
				Stop to = stopHome.getInstance();

				assert transferHome.isWired();
				assert !transferHome.isManaged();

				transferHome.getInstance().setAgency(agencyHome.getInstance());
				transferHome.getInstance().setFromLocation(from);
				transferHome.getInstance().setToLocation(to);
				transferHome.getInstance().setTransferType(transferType);
				transferHome.getInstance().setMinTransferTime(TestConstants.TEST_TRANSFER_MIN_TIME);

				assert TestConstants.PERSISTED.equals(transferHome.persist());

				transferList.getTransfers().setAgency(agencyHome.getInstance());
				assert transferList.getResultList().contains(transferHome.getInstance());

				transferList.getTransfers().setFromLocation(from);
				transferList.refresh();
				assert transferList.getResultList().contains(transferHome.getInstance());

				transferList.getTransfers().setToLocation(to);
				transferList.refresh();
				assert transferList.getResultList().contains(transferHome.getInstance());

				transferList.getTransfers().setTransferType(transferType);
				transferList.refresh();
				assert transferList.getResultList().contains(transferHome.getInstance());

				transferList.getTransfers().setMinTransferTime(TestConstants.TEST_TRANSFER_MIN_TIME);
				transferList.refresh();
				assert transferList.getResultList().contains(transferHome.getInstance());

				Integer transferId = transferHome.getTransfersTransferId();
				assert transferId != null;
				assert transferHome.isManaged();

				transferHome.clearInstance();
				assert transferHome.getDefinedInstance() == null;
				transferHome.load();
				assert transferHome.getDefinedInstance() == null;
				transferHome.setTransfersTransferId(transferId);
				transferHome.load();

				assert transferHome.getDefinedInstance() != null;

				transferHome.getInstance().setMinTransferTime(1212);

				assert TestConstants.UPDATED.equals(transferHome.update());

				transferHome.clearInstance();
				assert transferHome.getDefinedInstance() == null;
				transferHome.load();
				assert transferHome.getDefinedInstance() == null;
				transferHome.setTransfersTransferId(transferId);
				transferHome.load();

				assert transferHome.getInstance().getMinTransferTime().equals(1212);

				assert TestConstants.REMOVED.equals(transferHome.remove());
			}
		}.run();

		logoutAdmin();
	}
}
