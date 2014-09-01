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
package org.ideaproject.jsf.validator;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.core.ResourceBundle;

/**
 * @author dirk
 *
 */
@Name("startEndDatesValidator")
public class StartEndDatesValidator {
	private static final String START_DATE_REF_ID = "startDateRefId";
	private static final String START_DATE_FIELD_ID = "startDateFieldId";
	private static final String END_DATE_FIELD_ID = "endDateFieldId";

	public String getStartDateFieldId() {
		return START_DATE_FIELD_ID;
	}

	public String getEndDateFieldId() {
		return END_DATE_FIELD_ID;
	}

	public String getStartDateReferenceId() {
		return START_DATE_REF_ID;
	}

	public void validateEndDate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		String messageDetail = null;
		String messageSummary = null;
		String startDateIdentifier = (String) uiComponent.getAttributes().get(START_DATE_REF_ID);
		
//		UIInput startDateInput = (UIInput) facesContext.getViewRoot().findComponent("serviceScheduleGroup:boundsList:startDateFieldId");
		UIInput startDateInput = (UIInput) facesContext.getViewRoot().findComponent(startDateIdentifier);
		Date endDate = null, startDate = null;
		try {
			startDate = (Date) startDateInput.getValue();
		} catch (ClassCastException e) {
			messageDetail = ResourceBundle.instance().getString("org.ideaproject.jsf.validator.START_DATE_NOT_DATE_detail");
			messageSummary = ResourceBundle.instance().getString("org.ideaproject.jsf.validator.START_DATE_NOT_DATE");
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary(messageSummary);
			message.setDetail(messageDetail);
			throw new ValidatorException(message);
		}
		try {
			endDate = (Date) value;
		} catch (ClassCastException e) {
			messageDetail = ResourceBundle.instance().getString("org.ideaproject.jsf.validator.END_DATE_NOT_DATE_detail");
			messageSummary = ResourceBundle.instance().getString("org.ideaproject.jsf.validator.END_DATE_NOT_DATE");
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary(messageSummary);
			message.setDetail(messageDetail);
			throw new ValidatorException(message);
		}
		if (endDate.before(startDate)) {
			messageDetail = ResourceBundle.instance().getString("org.ideaproject.jsf.validator.END_DATE_BEFORE_START_DATE_detail");
			messageSummary = ResourceBundle.instance().getString("org.ideaproject.jsf.validator.END_DATE_BEFORE_START_DATE");
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary(messageSummary);
			message.setDetail(messageDetail);
			throw new ValidatorException(message);
		}
	}

}
