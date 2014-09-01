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
import java.util.Date;

import org.ideaproject.model.CalendarDate;
import org.jboss.seam.annotations.Name;

@Name("calendarDateList")
public class CalendarDateList extends AbstractLastResultEntityQuery<CalendarDate> {

	private static final String EJBQL = "select calendarDate from CalendarDate calendarDate";

	private static final String[] RESTRICTIONS = {
		"calendarDate.agency.agencyId = #{agencyHome.agencyAgencyId}",
		"calendarDate.date >= #{calendarDateList.earliest}",
		"lower(calendarDate.description) like lower(concat(#{calendarDateList.calendarDate.description},'%'))",
	};

	private CalendarDate calendarDate = new CalendarDate();
	private Date earliest = null;

	public CalendarDateList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setOrder("calendarDate.date");
		setMaxResults(25);
	}

	public CalendarDate getCalendarDate() {
		return calendarDate;
	}

	/**
	 * @return the earliest
	 */
	public Date getEarliest() {
		return earliest;
	}

	/**
	 * @param earliest the earliest to set
	 */
	public void setEarliest(Date earliest) {
		this.earliest = earliest;
	}

}
