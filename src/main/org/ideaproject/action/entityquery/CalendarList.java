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

import org.ideaproject.model.Calendar;
import org.jboss.seam.annotations.Name;

@Name("calendarList")
public class CalendarList extends AbstractLastResultEntityQuery<Calendar> {

	private static final String EJBQL = "select calendar from Calendar calendar";

	private static final String[] RESTRICTIONS = {
		"calendar.serviceScheduleGroup.agency = #{agencyHome.instance}",
		"lower(calendar.serviceLabel) like lower(concat(#{calendarList.calendar.serviceLabel},'%'))",
	};

	private Calendar calendar = new Calendar();

	public CalendarList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Calendar getCalendar() {
		return calendar;
	}

}
