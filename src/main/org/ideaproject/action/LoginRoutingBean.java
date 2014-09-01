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
package org.ideaproject.action;

import java.util.List;

import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.model.Agency;
import org.ideaproject.model.User;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * @author dirk
 *
 */
@Name("loginRoutingBean")
public class LoginRoutingBean {
	@In
	private User authenticatedUser;

	@In(create = true)
	private AgencyHome agencyHome;

	public String getRedirectIfOneAgency() {
		List<Agency> affiliations = authenticatedUser.getAgencyAffiliations();
		if (affiliations != null && affiliations.size() == 1) {
			agencyHome.setAgencyAgencyId(affiliations.get(0).getAgencyId());
			return "agencyDashboard";
		}
		return null;
	}
}
