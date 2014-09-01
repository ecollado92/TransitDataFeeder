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

import java.text.DecimalFormat;
import java.util.Currency;

import org.ideaproject.Constants;
import org.ideaproject.model.Fare;
import org.ideaproject.model.Route;
import org.ideaproject.model.Trip;
import org.ideaproject.util.StringUtil;
import org.jboss.seam.annotations.Name;

/**
 * @author dirk
 *
 */
@Name("displayBean")
public class DisplayBean {
	private DecimalFormat priceFormatter = new DecimalFormat("###.00");

	public String getFareDisplayValue(Fare fare) {
		StringBuffer resultBuffer = new StringBuffer();
		if (fare != null) {
			resultBuffer.append("ID ");
			resultBuffer.append(fare.getFareId());
			resultBuffer.append(" (");
			resultBuffer.append(Currency.getInstance(fare.getCurrencyType()).getSymbol());
			resultBuffer.append(priceFormatter.format(fare.getPrice()));
			resultBuffer.append(", ");
			resultBuffer.append(fare.getTransferLimit() != null ? 
					fare.getTransferLimit().getTransferLimitDescription() :
					Constants.LABEL_UNLIMITED);
			resultBuffer.append(")");
		}
		return resultBuffer.toString();
	}

	public String getRouteDisplayValue(Route route) {
		StringBuffer buffer = new StringBuffer(58);
		if (route != null) {
			buffer.append(route.getRouteLongName());
			String routeShortName = route.getRouteShortName();
			if (!StringUtil.isEmpty(routeShortName)) {
				if (buffer.length() > 0) {
					buffer.append(" - ");
				}
				buffer.append(routeShortName);
			}
		}
		return buffer.toString();
	}

	public String getTripDisplayValue(Trip trip) {
		StringBuffer buffer = new StringBuffer();
		if (trip != null) {
			buffer.append(trip.getTripHeadsign());
			if (trip.getFirstStopTime() != null) {
				buffer.append(" beginning at ");
				buffer.append(trip.getFirstStopTime());
			}
			buffer.append(" (");
			buffer.append(trip.getTripId());
			buffer.append(")");
		}
		return buffer.toString();
	}
}
