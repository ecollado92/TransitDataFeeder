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
package org.ideaproject.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.ideaproject.Constants;
import org.ideaproject.util.StringUtil;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * JSF converter class that takes in a (numeric) argument for minutes and converts to
 * seconds for database storage, and converts seconds to minutes for display.
 * @author dirk
 */
@org.jboss.seam.annotations.faces.Converter
@BypassInterceptors
@Name("minutesSecondsConverter")
public class MinutesSecondsConverter implements Converter {

	/*
	 * (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		Integer result = null;
		if (!StringUtil.isEmpty(value)) {
			try {
				Integer input = Integer.parseInt(value);
				result = input * Constants.SECONDS_IN_MINUTE;
			} catch (NumberFormatException e) {
				return value;
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		String result = null;
		if (value != null) {
			try {
				Integer intValue = (Integer) value;
				result = String.valueOf(intValue / Constants.SECONDS_IN_MINUTE);
			} catch (ClassCastException e) {
				result = value.toString();
			}
		}
		return result;
	}
}
