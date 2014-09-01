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

import java.util.Arrays;

import org.ideaproject.QueryNames;
import org.ideaproject.model.Fare;
import org.jboss.seam.annotations.Name;

@Name("fareList")
public class FareList extends AbstractCountableLastResultEntityQuery<Fare> {

	private static final String EJBQL = "select fare from Fare fare";

	private static final String[] RESTRICTIONS = {
		"fare.agency.agencyId = #{agencyHome.agencyAgencyId}",
		"#{fareList.hasRulesForQuery} = (SELECT CASE COUNT(*) WHEN 0 THEN 'false' ELSE 'true' END FROM fare.fareRules)",
		"lower(fare.currencyType) like lower(concat(#{fareList.fare.currencyType},'%'))",
	};

	private Fare fare = new Fare();
	private Boolean hasRules = null;

	public FareList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Fare getFare() {
		return fare;
	}

	public String getHasRulesForQuery() {
		if (hasRules != null) {
			return hasRules.toString();
		}
		return null;
	}

	/**
	 * @return the hasRules
	 */
	public Boolean getHasRules() {
		return hasRules;
	}

	/**
	 * @param hasRules the hasRules to set
	 */
	public void setHasRules(Boolean hasRules) {
		this.hasRules = hasRules;
	}

	public Long getCurrentAgencyFareCount() {
		return getNamedCountQueryResult(QueryNames.COUNT_AGENCY_FARES_ALL, agencyHome.getInstance());
	}

	public Long getCurrentAgencyUnusedFareCount() {
		return getNamedCountQueryResult(QueryNames.COUNT_AGENCY_FARES_UNUSED, agencyHome.getInstance());
	}
}
