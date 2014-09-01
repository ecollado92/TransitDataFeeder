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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.core.ResourceBundle;

/**
 * Seam validator class for minutes entries; since it's a "synthetic" field using a JSF converter.
 * Verifies that argument is numeric and greater than zero.
 * 
 * @see org.ideaproject.jsf.converter.MinutesSecondsConverter
 * @author dirk
 */
@Name("minutesValidator")
@BypassInterceptors
@org.jboss.seam.annotations.faces.Validator
public class MinutesValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value)
			throws ValidatorException {
		String messageDetail = null;
		String messageSummary = null;
		try {
			Integer minutes = Integer.parseInt(value.toString());
			if (minutes < 0) {
				messageDetail = ResourceBundle.instance().getString("org.ideaproject.jsf.validator.MINUTES_NEGATIVE_detail");
				messageSummary = ResourceBundle.instance().getString("org.ideaproject.jsf.validator.MINUTES_NEGATIVE");
			}
		} catch (NumberFormatException e) {
			messageDetail = ResourceBundle.instance().getString("org.ideaproject.jsf.validator.MINUTES_NAN_detail");
			messageSummary = ResourceBundle.instance().getString("org.ideaproject.jsf.validator.MINUTES_NAN");
		}
		if (messageDetail != null && messageSummary != null) {
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary(messageSummary);
			message.setDetail(messageDetail);
			throw new ValidatorException(message);
		}
	}

}
