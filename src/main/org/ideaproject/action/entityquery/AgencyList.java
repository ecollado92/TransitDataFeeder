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
package org.ideaproject.action.entityquery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

import org.ideaproject.model.Agency;
import org.jboss.seam.annotations.Name;

@Name("agencyList")
public class AgencyList extends AbstractLastResultEntityQuery<Agency> {
	private static final Object FULL_RESULT_LOCK = new Object();
	private static final Object TZ_IDS_LOCK = new Object();
	private static final String EJBQL = "select agency from Agency agency";
	private static final Comparator<Agency> AGENCY_NAME_COMPARATOR = new Comparator<Agency>() {
		@Override
		public int compare(Agency o1, Agency o2) {
			return o1.getAgencyName().compareTo(o2.getAgencyName());
		}
	};

	private static final String[] RESTRICTIONS = {
			"lower(agency.agencyFareUrl) like lower(concat(#{agencyList.agency.agencyFareUrl},'%'))",
			"lower(agency.agencyName) like lower(concat(#{agencyList.agency.agencyName},'%'))",
			"lower(agency.agencyPhone) like lower(concat(#{agencyList.agency.agencyPhone},'%'))",
			"lower(agency.agencyShortName) like lower(concat(#{agencyList.agency.agencyShortName},'%'))",
			"lower(agency.agencyTimezone) like lower(concat(#{agencyList.agency.agencyTimezone},'%'))",
			"lower(agency.agencyUrl) like lower(concat(#{agencyList.agency.agencyUrl},'%'))",};

	private Agency agency = new Agency();
	private List<String> availableTimezoneIds = null;

	public AgencyList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Agency getAgency() {
		return agency;
	}

	public List<Agency> getAllAgencies() {
		List<Agency> result = new ArrayList<Agency>();
		synchronized(FULL_RESULT_LOCK) {
			Integer currentMax = getMaxResults();
			setMaxResults(null);
			result = getResultList();
			setMaxResults(currentMax);
		}
		Collections.sort(result, AGENCY_NAME_COMPARATOR);
		return result;
	}

	public List<String> getAvailableTimezoneIds() {
		synchronized(TZ_IDS_LOCK) {
			if (availableTimezoneIds == null) {
				initAvailableTimezoneIds();
			}
		}
		return availableTimezoneIds;
	}

	private void initAvailableTimezoneIds() {
		List<String> result = new ArrayList<String>();
		for (String timezoneId : TimeZone.getAvailableIDs()) {
			if (TimeZoneFilter.accept(timezoneId)) {
				result.add(timezoneId);
			}
		}
		Collections.sort(result);
		this.availableTimezoneIds = result;
	}

	private static final class TimeZoneFilter {
		private static final List<String> AREAS = new ArrayList<String>();
		static {
			AREAS.add("Africa");
			AREAS.add("America");
			AREAS.add("Antarctica");
			AREAS.add("Asia");
			AREAS.add("Atlantic");
			AREAS.add("Australia");
			AREAS.add("Europe");
			AREAS.add("Indian");
			AREAS.add("Pacific");
		}
		
		public static boolean accept(String timezoneId) {
			boolean result = false;
			for (String area: AREAS) {
				if (timezoneId.startsWith(area)) {
					result = true;
					break;
				}
			}
			return result;
		}
	}
}
