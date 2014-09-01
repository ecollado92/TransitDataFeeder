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
package org.ideaproject.export.gtfs;

import java.io.IOException;
import java.io.OutputStream;

import org.ideaproject.Constants;
import org.ideaproject.model.Route;
import org.ideaproject.util.StringUtil;

/**
 * @author dirk
 *
 */
public class RouteGtfsExporter extends AbstractGtfsExporter<Route> {
	private static final Object BUFFER_LOCK = new Object();
	private static final StringBuffer BUFFER = new StringBuffer(255);

	public void export(Route toExport, OutputStream outStream)
			throws IOException {
		//agency.agencyId,routeId,routeShortName,routeLongName,routeDesc,routeType,routeUrl,routeColor,routeTextColor
		BUFFER.delete(0, BUFFER.length());
		BUFFER.append(toExport.getAgency().getAgencyId());
		BUFFER.append(SEPARATOR);
		BUFFER.append(toExport.getRouteId());
		BUFFER.append(SEPARATOR);
		BUFFER.append(stringifyInput(toExport.getRouteShortName()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(stringifyInput(toExport.getRouteLongName()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(stringifyInput(toExport.getRouteDesc()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(toExport.getRouteType() != null ? toExport.getRouteType().getRouteTypeId() : Constants.EMPTY);
		BUFFER.append(SEPARATOR);
		BUFFER.append(stringifyInput(toExport.getRouteUrl()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(stripLeadingHashmark(toExport.getRouteColor()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(stripLeadingHashmark(toExport.getRouteTextColor()));
		BUFFER.append(LINE_SEPARATOR);
		outStream.write(BUFFER.toString().getBytes());
	}

	final Class<Route> getSupportedClass() {
		return Route.class;
	}

	private final static String stripLeadingHashmark(String withHashMark) {
		String result = withHashMark;
		if (!StringUtil.isEmpty(withHashMark)) {
			if (withHashMark.charAt(0) == '#') {
				result = result.substring(1);
			}
		} else {
			result = Constants.EMPTY;
		}
		return result;
	}
}
