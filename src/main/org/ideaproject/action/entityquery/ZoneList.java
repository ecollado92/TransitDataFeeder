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

import org.ideaproject.model.Zone;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

@Name("zoneList")
public class ZoneList extends AbstractLastResultEntityQuery<Zone> {

	private static final String EJBQL = "select zone from Zone zone";

	private static final String[] RESTRICTIONS = {
		"zone.agency.agencyId = #{agencyHome.agencyAgencyId}",
		"lower(zone.zoneName) like lower(concat(#{zoneList.zone.zoneName},'%'))",
	};

	private Zone zone = new Zone();

	public ZoneList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Zone getZone() {
		return zone;
	}

}
