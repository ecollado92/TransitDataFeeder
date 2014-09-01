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

import java.util.Date;

import org.ideaproject.util.StringUtil;
import org.jboss.seam.annotations.Name;

/**
 * @author dirk
 *
 */
@Name("carryoverTimeViewBean")
public class CarryoverTimeViewBean {
	public String displayTime(Date value, boolean carryover) {
		return StringUtil.formatTimeValue(value, carryover);
	}

}
