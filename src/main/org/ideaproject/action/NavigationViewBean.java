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

import java.io.Serializable;

import org.ideaproject.Constants;
import org.ideaproject.action.entityhome.AgencyHome;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author dirk
 *
 */
@Name("navigationViewBean")
@Scope(ScopeType.SESSION)
public class NavigationViewBean implements Serializable {
	@In(create = true)
	private AgencyHome agencyHome;
	
	private String currentMenuItem = Constants.NAVBAR_NONE_LABEL;

	public String getCurrentMenuItem() {
		return currentMenuItem;
	}

	public void setCurrentMenuItem(String currentMenuItem) {
		this.currentMenuItem = currentMenuItem;
	}

	public boolean isAgencyMenuShown() {
		return !(agencyHome.getAgencyAgencyId() == null
				|| Constants.NAVBAR_NONE_LABEL.equals(currentMenuItem)
				|| Constants.NAVBAR_HOME_LABEL.equals(currentMenuItem));
	}
}
