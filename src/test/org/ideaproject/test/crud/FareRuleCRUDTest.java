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

import org.ideaproject.action.ValidationTableBean;
import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityhome.FareHome;
import org.ideaproject.action.entityhome.FareRuleHome;
import org.ideaproject.action.entityhome.RouteHome;
import org.ideaproject.action.entityhome.ZoneHome;
import org.ideaproject.action.entityquery.FareRuleList;
import org.ideaproject.model.PaymentMethod;
import org.ideaproject.model.TransferLimit;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.mock.SeamTest;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class FareRuleCRUDTest extends SeamTest {

	@Test
	public void testCreate() throws Exception {
		new ComponentTest() {
			@Override
			protected void testComponents() throws Exception {
				UserTransaction tx = getUserTransaction();
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				ValidationTableBean validationTableBean = (ValidationTableBean) getInstance("validationTableBean");
				RouteHome routeHome = (RouteHome) getInstance("routeHome");
				ZoneHome zoneHome = (ZoneHome) getInstance("zoneHome");
				FareHome fareHome = (FareHome) getInstance("fareHome");
				FareRuleHome fareRuleHome = (FareRuleHome) getInstance("fareRuleHome");
				FareRuleList fareRuleList = (FareRuleList) getInstance("fareRuleList");

				setValue("#{credentials.username}", TestConstants.ADMIN_USER_USERNAME);
				setValue("#{credentials.password}", TestConstants.ADMIN_USER_PASSWORD);
				invokeMethod("#{identity.login}");

				PaymentMethod paymentMethod = validationTableBean.getPaymentMethods().get(0);
				assert paymentMethod != null;
				
				TransferLimit transferLimit = validationTableBean.getTransferLimits().get(0);
				assert transferLimit != null;

				agencyHome.setAgencyAgencyId(TestConstants.DEFAULT_AGENCY_ID);
				agencyHome.load();

				fareHome.setFareFareId(TestConstants.DEFAULT_FARE_ID);
				fareHome.load();

				routeHome.setRouteRouteId(TestConstants.DEFAULT_ROUTE_ID);
				routeHome.load();

				zoneHome.setZoneZoneId(TestConstants.DEFAULT_ZONE_ID);
				zoneHome.load();

				assert fareRuleHome.isWired();
				assert !fareRuleHome.isManaged();

				fareRuleHome.getInstance().setAgency(agencyHome.getInstance());
				fareRuleHome.getInstance().setContains(zoneHome.getInstance());
				fareRuleHome.getInstance().setOrigin(zoneHome.getInstance());
				fareRuleHome.getInstance().setDestination(zoneHome.getInstance());
				fareRuleHome.getInstance().setFare(fareHome.getInstance());
				fareRuleHome.getInstance().setRoute(routeHome.getInstance());

				tx.begin();
				assert TestConstants.PERSISTED.equals(fareRuleHome.persist());
				tx.commit();

				fareRuleList.getFareRule().setAgency(agencyHome.getInstance());
				assert fareRuleList.getResultList().contains(fareRuleHome.getInstance());

				fareRuleList.setRuleContainsId(zoneHome.getZoneZoneId());
				fareRuleList.refresh();
				assert fareRuleList.getResultList().contains(fareRuleHome.getInstance());

				fareRuleList.setRuleOriginId(zoneHome.getZoneZoneId());
				fareRuleList.refresh();
				assert fareRuleList.getResultList().contains(fareRuleHome.getInstance());

				fareRuleList.setRuleDestinationId(zoneHome.getZoneZoneId());
				fareRuleList.refresh();
				assert fareRuleList.getResultList().contains(fareRuleHome.getInstance());
				
				fareRuleList.setRuleFareId(fareHome.getFareFareId());
				fareRuleList.refresh();
				assert fareRuleList.getResultList().contains(fareRuleHome.getInstance());

				fareRuleList.setRuleRouteId(routeHome.getRouteRouteId());
				fareRuleList.refresh();
				assert fareRuleList.getResultList().contains(fareRuleHome.getInstance());

				Integer fareRuleId = fareRuleHome.getFareRuleFareRuleId();
				assert fareRuleId != null;
				assert fareRuleHome.isManaged();

				fareRuleHome.clearInstance();
				assert fareRuleHome.getDefinedInstance() == null;
				fareRuleHome.load();
				assert fareRuleHome.getDefinedInstance() == null;
				fareRuleHome.setFareRuleFareRuleId(fareRuleId);
				fareRuleHome.load();

				assert fareRuleHome.getDefinedInstance() != null;

				zoneHome.getInstance().setAgency(agencyHome.getInstance());
				zoneHome.getInstance().setZoneName(TestConstants.TEST_ZONE_NAME_1);

				tx.begin();
				assert TestConstants.PERSISTED.equals(zoneHome.persist());
				tx.commit();

				tx.begin();
				fareRuleHome.getInstance().setDestination(zoneHome.getInstance());
				tx.commit();

				fareRuleHome.clearInstance();
				assert fareRuleHome.getDefinedInstance() == null;
				fareRuleHome.load();
				assert fareRuleHome.getDefinedInstance() == null;
				fareRuleHome.setFareRuleFareRuleId(fareRuleId);
				fareRuleHome.load();

				tx.begin();
				assert TestConstants.UPDATED.equals(fareRuleHome.update());
				tx.commit();

				fareRuleList.setRuleDestinationId(zoneHome.getZoneZoneId());
				fareRuleList.refresh();
				assert fareRuleList.getResultList().contains(fareRuleHome.getInstance());
				
				tx.begin();
				assert TestConstants.REMOVED.equals(fareRuleHome.remove());
				tx.commit();
			}
		}.run();
	}
}
