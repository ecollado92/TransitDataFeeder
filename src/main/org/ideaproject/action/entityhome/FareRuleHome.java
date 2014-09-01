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

import org.ideaproject.action.entityquery.FareList;
import org.ideaproject.model.Fare;
import org.ideaproject.model.FareRule;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Seam EntityHome for FareRules
 * @author dirk
 * @see org.ideaproject.action.entityhome.AbstractAgencyBasedEntityHome
 */
@Name("fareRuleHome")
public class FareRuleHome extends AbstractAgencyBasedEntityHome<FareRule> {

	private static final long serialVersionUID = 1L;

	/**
	 * The entity home from which we can determine the current instance's
	 * fare association.
	 */
	@In(create = true)
	private FareHome fareHome;

	/**
	 * Since fares have formula columns related to associated fare rules, this provides
	 * a handle on the fare list so that it can be refreshed upon CRUD operations
	 * on fare rules.
	 */
	@In(create = true)
	private FareList fareList;

	/**
	 * Sets the current fare rule by ID; side effect updates the
	 * instance.
	 * @param id the bike option ID
	 */
	public void setFareRuleFareRuleId(Integer id) {
		setId(id);
	}

	/**
	 * @return the ID of the current fare rule.
	 */
	public Integer getFareRuleFareRuleId() {
		return (Integer) getId();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected FareRule createInstance() {
		FareRule fareRule = new FareRule();
		fareRule.setFare(fareHome.getInstance());
		return fareRule;
	}

	/**
	 * Loads the current fare rule.
	 */
	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.ideaproject.action.entityhome.AbstractAgencyBasedEntityHome#wire()
	 */
	@Override
	public void wire() {
		super.wire();
		Fare fare = fareHome.getDefinedInstance();
		if (fare != null) {
			getInstance().setFare(fare);
		}
	}

	/**
	 * @return whether or not the current fare rule is "wired"
	 * @see #wire()
	 */
	public boolean isWired() {
		return true;
	}

	/**
	 * Gets the currently defined instance; may return null if the ID for this
	 * EntityHome is not supplied.
	 * @return the currently-defined instance, or null if none is defined.
	 */
	public FareRule getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ideaproject.action.entityhome.AbstractAgencyBasedEntityHome#persist()
	 */
	@Override
	public String persist() {
		try {
			return super.persist();
		} finally {
			// Need to refresh the fare list so that it recalculates the rule count.
			getEntityManager().flush();
			fareList.refresh();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.EntityHome#remove()
	 */
	@Override
	public String remove() {
		try {
			return super.remove();
		} finally {
			// Need to refresh the fare list so that it recalculates the rule count.
			fareList.refresh();
		}
	}

}
