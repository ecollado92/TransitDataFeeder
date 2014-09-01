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
import java.util.List;
import java.util.TimeZone;

import org.ideaproject.model.Agency;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

@Name("fullAgencyList")
public class FullAgencyList extends EntityQuery<Agency> {
	private static final Object FULL_RESULT_LOCK = new Object();
	private static final String EJBQL = "select agency from Agency agency fetch all properties";
//	private static final String EJBQL = "select agency from Agency agency left join fetch agency.routes "
//		+ "left join fetch agency.stops  "
//		+ "left join fetch agency.routes routes "
//		+ "left join fetch routes.trips trips "
//		+ "left join fetch trips.stopTimes ";

	private static final String[] RESTRICTIONS = {
			"agency.agencyId = #{fullAgencyList.agencyId}",
			"lower(agency.agencyFareUrl) like lower(concat(#{fullAgencyList.agency.agencyFareUrl},'%'))",
			"lower(agency.agencyName) like lower(concat(#{fullAgencyList.agency.agencyName},'%'))",
			"lower(agency.agencyPhone) like lower(concat(#{fullAgencyList.agency.agencyPhone},'%'))",
			"lower(agency.agencyShortName) like lower(concat(#{fullAgencyList.agency.agencyShortName},'%'))",
			"lower(agency.agencyTimezone) like lower(concat(#{fullAgencyList.agency.agencyTimezone},'%'))",
			"lower(agency.agencyUrl) like lower(concat(#{fullAgencyList.agency.agencyUrl},'%'))",};

	private Agency agency = new Agency();
	private Integer agencyId;

	public FullAgencyList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Agency getAgency() {
		return agency;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public List<Agency> getAllAgencies() {
		List<Agency> result = new ArrayList<Agency>();
		synchronized(FULL_RESULT_LOCK) {
			Integer currentMax = getMaxResults();
			setMaxResults(null);
			result = getResultList();
			setMaxResults(currentMax);
		}
		return result;
	}

	public List<String> getAvailableTimezoneIds() {
		List<String> result = new ArrayList<String>();
		for (String timezoneId : TimeZone.getAvailableIDs()) {
			result.add(timezoneId);
		}
		return result;
	}
}
