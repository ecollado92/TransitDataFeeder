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

import org.ideaproject.model.Agency;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;

/**
 * @author dirk
 *
 */
public class AgencyGtfsExporter extends AbstractGtfsExporter<Agency> {
	private static final Object BUFFER_LOCK = new Object();
	private static final StringBuffer BUFFER = new StringBuffer(255);
	@Logger
	private Log logger;
	
	public void export(Agency toExport, OutputStream outStream) throws IOException {
		// agencyId,agencyName,agencyUrl,agencyTimezone,agencyPhone,agencyFareUrl

		BUFFER.delete(0, BUFFER.length());
		BUFFER.append(toExport.getAgencyId());
		BUFFER.append(SEPARATOR);
		BUFFER.append(stringifyInput(toExport.getAgencyName()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(stringifyInput(toExport.getAgencyUrl()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(stringifyInput(toExport.getAgencyTimezone()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(stringifyInput(toExport.getAgencyLanguage()));
		BUFFER.append(SEPARATOR);
		BUFFER.append(stringifyInput(toExport.getAgencyPhone()));
//		buffer.append(SEPARATOR);
//		buffer.append(stringifyInput(toExport.getAgencyFareUrl()));
		BUFFER.append(LINE_SEPARATOR);
		outStream.write(BUFFER.toString().getBytes());
	}

	final Class<Agency> getSupportedClass() {
		return Agency.class;
	}

}
