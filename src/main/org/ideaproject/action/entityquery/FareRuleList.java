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

import org.ideaproject.action.entityhome.FareHome;
import org.ideaproject.model.FareRule;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

@Name("fareRuleList")
public class FareRuleList extends AbstractLastResultEntityQuery<FareRule> {
	@In(required = false)
	private FareHome fareHome;

	private static final String EJBQL = "select fareRule from FareRule fareRule";

	private static final String[] RESTRICTIONS = {
		"fareRule.fare.fareId = #{fareRuleList.ruleFareId}",
		"fareRule.origin.zoneId = #{fareRuleList.ruleOriginId}",
		"fareRule.destination.zoneId = #{fareRuleList.ruleDestinationId}",
		"fareRule.contains.zoneId = #{fareRuleList.ruleContainsId}",
		"fareRule.route.routeId = #{fareRuleList.ruleRouteId}",
		"fareRule.agency = #{agencyHome.definedInstance}",
	};

	private FareRule fareRule = new FareRule();
	private Integer ruleFareId;
	private Integer ruleOriginId;
	private Integer ruleDestinationId;
	private Integer ruleContainsId;
	private Integer ruleRouteId;

	public FareRuleList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public FareRule getFareRule() {
		return fareRule;
	}

	public void clearListAssociations() {
		if (fareHome != null) {
			fareHome.clearInstance();
		}
	}

	/**
	 * @return the ruleFareId
	 */
	public Integer getRuleFareId() {
		return ruleFareId;
	}

	/**
	 * @param ruleFareId the ruleFareId to set
	 */
	public void setRuleFareId(Integer ruleFareId) {
		this.ruleFareId = ruleFareId;
	}

	/**
	 * @return the ruleOriginId
	 */
	public Integer getRuleOriginId() {
		return ruleOriginId;
	}

	/**
	 * @param ruleOriginId the ruleOriginId to set
	 */
	public void setRuleOriginId(Integer ruleOriginId) {
		this.ruleOriginId = ruleOriginId;
	}

	/**
	 * @return the ruleDestinationId
	 */
	public Integer getRuleDestinationId() {
		return ruleDestinationId;
	}

	/**
	 * @param ruleDestinationId the ruleDestinationId to set
	 */
	public void setRuleDestinationId(Integer ruleDestinationId) {
		this.ruleDestinationId = ruleDestinationId;
	}

	/**
	 * @return the ruleContainsId
	 */
	public Integer getRuleContainsId() {
		return ruleContainsId;
	}

	/**
	 * @param ruleContainsId the ruleContainsId to set
	 */
	public void setRuleContainsId(Integer ruleContainsId) {
		this.ruleContainsId = ruleContainsId;
	}

	/**
	 * @return the ruleRouteId
	 */
	public Integer getRuleRouteId() {
		return ruleRouteId;
	}

	/**
	 * @param ruleRouteId the ruleRouteId to set
	 */
	public void setRuleRouteId(Integer ruleRouteId) {
		this.ruleRouteId = ruleRouteId;
	}
}
