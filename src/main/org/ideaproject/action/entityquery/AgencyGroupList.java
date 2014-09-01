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

import org.ideaproject.model.AgencyGroup;
import org.jboss.seam.annotations.Name;

@Name("agencyGroupList")
public class AgencyGroupList extends AbstractLastResultEntityQuery<AgencyGroup> {
	private static final Object FULL_RESULT_LOCK = new Object();

	private static final String EJBQL = "select agencyGroup from AgencyGroup agencyGroup";

	private static final String[] RESTRICTIONS = {"lower(agencyGroup.groupName) like lower(concat(#{agencyGroupList.agencyGroup.groupName},'%'))",};

	private AgencyGroup agencyGroup = new AgencyGroup();

	public AgencyGroupList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setOrder("agencyGroup.groupName");
		setMaxResults(25);
	}

	public AgencyGroup getAgencyGroup() {
		return agencyGroup;
	}

	public List<AgencyGroup> getAllAgencyGroups() {
		List<AgencyGroup> result = new ArrayList<AgencyGroup>();
		synchronized(FULL_RESULT_LOCK) {
			Integer currentMax = getMaxResults();
			AgencyGroup currentGroup = agencyGroup;
			try {
				agencyGroup = new AgencyGroup();
				setMaxResults(null);
				result = getResultList();
			} finally {
				setMaxResults(currentMax);
				agencyGroup = currentGroup;
			}
		}
		return result;
	}

}
