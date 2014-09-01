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

import org.ideaproject.action.entityhome.FrequencyHome;
import org.ideaproject.model.Frequency;
import org.ideaproject.util.StringUtil;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Unwrap;

/**
 * @author dirk
 *
 */
@Name("frequencyViewBean")
public class FrequencyViewBean {
	@In(create = true)
	private FrequencyHome frequencyHome;


	public Frequency getInstance() {
		return frequencyHome.getInstance();
	}

	public String getFrequencyStartTime() {
		String result = null;
		Frequency local = getInstance();
		if (local != null) {
			result = StringUtil.formatTimeValue(
					local.getStartTime(),
					local.isStartTimeCarryover());
		}
		return result;
	}

	public String getFrequencyEndTime() {
		String result = null;
		Frequency local = getInstance();
		if (local != null) {
			result = StringUtil.formatTimeValue(
					local.getEndTime(),
					local.isEndTimeCarryover());
		}
		return result;
	}

	public Integer getFrequencyId() {
		return (Integer) frequencyHome.getId();
	}

	public void setFrequencyId(Integer frequencyId) {
		frequencyHome.setId(frequencyId);
	}
}
