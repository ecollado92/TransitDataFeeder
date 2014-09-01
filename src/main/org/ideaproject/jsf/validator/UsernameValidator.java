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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.ideaproject.action.entityquery.UserList;
import org.jboss.seam.Component;
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
@Name("usernameValidator")
@BypassInterceptors
@org.jboss.seam.annotations.faces.Validator
public class UsernameValidator implements Validator {
	/** 
	 * Grmbl.  This is the pattern copied-n-pasted from Hibernate's EmailValidator.  I would try to extend
	 * it, but apparently the @Email annotation is set up such that it can NOT be extended...
	 */
	private static String ATOM = "[^\\x00-\\x1F^\\(^\\)^\\<^\\>^\\@^\\,^\\;^\\:^\\\\^\\\"^\\.^\\[^\\]^\\s]";
	private static String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";
	private static String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";

	public static Object EMAIL_PATTERN_LOCK = new Object();
	private Pattern pattern;

	/*
	 * (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value)
			throws ValidatorException {
		UserList userList = (UserList) Component.getInstance("userList");
		String messageDetail = null;
		String messageSummary = null;

		String userName = value.toString();
		if (!isValid(userName)) {
			messageDetail = ResourceBundle.instance().getString("validator.email");
			messageSummary = ResourceBundle.instance().getString("validator.email");
		} else {
			Integer userId = userList.getUserIdForUsername(userName);
			if (userId != null) {
				messageDetail = ResourceBundle.instance().getString("org.ideaproject.jsf.validator.USERNAME_TAKEN_detail");
				messageSummary = ResourceBundle.instance().getString("org.ideaproject.jsf.validator.USERNAME_TAKEN");
			}
		}

		if (messageDetail != null && messageSummary != null) {
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary(messageSummary);
			message.setDetail(messageDetail);
			throw new ValidatorException(message);
		}
	}

	private boolean isValid(String value) {
		if ( value == null ) return true;
		if ( value.length() == 0 ) return true;
		Matcher m = getEmailPattern().matcher( value );
		return m.matches();
	}

	private Pattern getEmailPattern() {
		synchronized(EMAIL_PATTERN_LOCK) {
			if (pattern == null) {
				pattern = java.util.regex.Pattern.compile(
						"^" + ATOM + "+(\\." + ATOM + "+)*@"
								 + DOMAIN
								 + "|"
								 + IP_DOMAIN
								 + ")$",
						java.util.regex.Pattern.CASE_INSENSITIVE
				);				
			}
			return pattern;
		}
	}
}
