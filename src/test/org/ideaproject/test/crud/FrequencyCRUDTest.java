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
import org.ideaproject.action.entityhome.FrequencyHome;
import org.ideaproject.action.entityhome.TripHome;
import org.ideaproject.action.entityquery.FrequencyList;
import org.ideaproject.test.TestConstants;
import org.ideaproject.test.TestUtilities;
import org.jboss.seam.mock.SeamTest;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class FrequencyCRUDTest extends SeamTest {

	@Test
	public void testCreate() throws Exception {
		new ComponentTest() {
			@Override
			protected void testComponents() throws Exception {
				UserTransaction tx = getUserTransaction();
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				TripHome tripHome = (TripHome) getInstance("tripHome");
				FrequencyHome frequencyHome = (FrequencyHome) getInstance("frequencyHome");
				FrequencyList frequencyList = (FrequencyList) getInstance("frequenciesList");
				
				setValue("#{credentials.username}", TestConstants.ADMIN_USER_USERNAME);
				setValue("#{credentials.password}", TestConstants.ADMIN_USER_PASSWORD);
				invokeMethod("#{identity.login}");
				
				agencyHome.setAgencyAgencyId(TestConstants.DEFAULT_AGENCY_ID);
				agencyHome.load();

				tripHome.setTripTripId(TestConstants.DEFAULT_TRIP_ID);
				tripHome.load();

				frequencyHome.getInstance().setStartTime(TestUtilities.generatePastDate());
				frequencyHome.getInstance().setEndTime(TestUtilities.generateFutureDate());
				frequencyHome.getInstance().setExactTimes(TestConstants.TEST_FREQUENCY_EXACT_TIME);
				frequencyHome.getInstance().setHeadwaySecs(TestConstants.TEST_FREQUENCY_HEADWAY_SECS);
				frequencyHome.getInstance().setTrip(tripHome.getInstance());

				assert frequencyHome.isWired();
				assert !frequencyHome.isManaged();

				tx.begin();
				assert TestConstants.PERSISTED.equals(frequencyHome.persist());
				tx.commit();

				frequencyList.getFrequencies().setTrip(tripHome.getInstance());
				assert frequencyList.getResultList().contains(frequencyHome.getInstance());
				
				frequencyList.getFrequencies().setStartTime(frequencyHome.getInstance().getStartTime());
				frequencyList.refresh();
				assert frequencyList.getResultList().contains(frequencyHome.getInstance());

				frequencyList.getFrequencies().setEndTime(frequencyHome.getInstance().getEndTime());
				frequencyList.refresh();
				assert frequencyList.getResultList().contains(frequencyHome.getInstance());

				frequencyList.getFrequencies().setExactTimes(TestConstants.TEST_FREQUENCY_EXACT_TIME);
				frequencyList.refresh();
				assert frequencyList.getResultList().contains(frequencyHome.getInstance());

				frequencyList.getFrequencies().setHeadwaySecs(TestConstants.TEST_FREQUENCY_HEADWAY_SECS);
				frequencyList.refresh();
				assert frequencyList.getResultList().contains(frequencyHome.getInstance());

				assert frequencyList.getMultiColumnSort() == null;
				frequencyList.setMultiColumnSort("startTime,headwaySecs");
				assert frequencyList.getMultiColumnSort() != null;
				frequencyList.refresh();
				assert frequencyList.getResultList().contains(frequencyHome.getInstance());
				
				Integer frequencyId = frequencyHome.getFrequencyFrequencyId();
				assert frequencyId != null;
				assert frequencyHome.isManaged();

				frequencyHome.clearInstance();
				assert frequencyHome.getDefinedInstance() == null;
				frequencyHome.load();
				assert frequencyHome.getDefinedInstance() == null;
				frequencyHome.setFrequencyFrequencyId(frequencyId);
				frequencyHome.load();
				assert frequencyHome.getDefinedInstance() != null;
				assert TestConstants.TEST_FREQUENCY_HEADWAY_SECS == frequencyHome.getInstance().getHeadwaySecs();

				tx.begin();
				assert TestConstants.REMOVED.equals(frequencyHome.remove());
				tx.commit();
			}
		}.run();
	}
	
}
