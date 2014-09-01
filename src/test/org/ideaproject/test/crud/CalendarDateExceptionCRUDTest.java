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
package org.ideaproject.test.crud;

import org.ideaproject.action.ValidationTableBean;
import org.ideaproject.action.entityhome.CalendarDateExceptionHome;
import org.ideaproject.action.entityhome.CalendarDateHome;
import org.ideaproject.action.entityhome.CalendarHome;
import org.ideaproject.action.entityquery.CalendarDateExceptionList;
import org.ideaproject.model.CalendarExceptionType;
import org.ideaproject.test.AbstractAdminLoginSeamTest;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class CalendarDateExceptionCRUDTest extends AbstractAdminLoginSeamTest {

	@Test
	@Transactional
	public void testCreate() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				ValidationTableBean validationTableBean = (ValidationTableBean) getInstance("validationTableBean");
				CalendarHome calendarHome = (CalendarHome) getInstance("calendarHome");
				CalendarDateHome calendarDateHome = (CalendarDateHome) getInstance("calendarDateHome");

				CalendarDateExceptionHome calendarDateExceptionHome =
					(CalendarDateExceptionHome) getInstance("calendarDateExceptionHome");
				CalendarDateExceptionList calendarDateExceptionList =
					(CalendarDateExceptionList) getInstance("calendarDateExceptionList");
				
				calendarHome.setCalendarCalendarId(TestConstants.DEFAULT_CALENDAR_ID);
				calendarHome.load();

				calendarDateHome.setCalendarDateCalendarDateId(TestConstants.DEFAULT_CALENDAR_DATE_ID);
				calendarDateHome.load();

				CalendarExceptionType exceptionType = validationTableBean.getCalendarExceptionTypes().get(0);

				assert calendarDateExceptionHome.isWired();
				assert !calendarDateExceptionHome.isManaged();

				calendarDateExceptionHome.getInstance().setCalendarDate(calendarDateHome.getInstance());
				calendarDateExceptionHome.getInstance().setServiceException(calendarHome.getInstance());
				calendarDateExceptionHome.getInstance().setExceptionType(exceptionType);

				assert TestConstants.PERSISTED.equals(calendarDateExceptionHome.persist());

				calendarDateExceptionList.getCalendarDateExceptions().setServiceException(calendarHome.getInstance());
				assert calendarDateExceptionList.getResultList().contains(calendarDateExceptionHome.getInstance());

				calendarDateExceptionList.getCalendarDateExceptions().setCalendarDate(calendarDateHome.getInstance());
				calendarDateExceptionList.refresh();
				assert calendarDateExceptionList.getResultList().contains(calendarDateExceptionHome.getInstance());

				calendarDateExceptionList.getCalendarDateExceptions().setExceptionType(exceptionType);
				calendarDateExceptionList.refresh();
				assert calendarDateExceptionList.getResultList().contains(calendarDateExceptionHome.getInstance());

				Integer exceptionId = calendarDateExceptionHome.getCalendarDateExceptionCalendarDateExceptionId();
				assert exceptionId != null;
				assert calendarDateExceptionHome.isManaged();

				calendarDateExceptionHome.clearInstance();
				assert calendarDateExceptionHome.getDefinedInstance() == null;
				calendarDateExceptionHome.load();
				assert calendarDateExceptionHome.getDefinedInstance() == null;
				calendarDateExceptionHome.setCalendarDateExceptionCalendarDateExceptionId(exceptionId);
				calendarDateExceptionHome.load();

				assert calendarDateExceptionHome.getDefinedInstance() != null;
				
				assert TestConstants.REMOVED.equals(calendarDateExceptionHome.remove());
			}
		}.run();

		logoutAdmin();
	}
}
