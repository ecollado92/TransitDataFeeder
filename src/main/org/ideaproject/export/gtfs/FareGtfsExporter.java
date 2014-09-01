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
import org.ideaproject.model.Fare;

/**
 * @author dirk
 *
 */
public class FareGtfsExporter extends AbstractGtfsExporter<Fare> {
	private static final Object BUFFER_LOCK = new Object();
	private static final StringBuffer BUFFER = new StringBuffer(255);

	public void export(Fare toExport, OutputStream outStream)
			throws IOException {
		BUFFER.delete(0, BUFFER.length());
		BUFFER.append(toExport.getFareId());
		BUFFER.append(SEPARATOR);
		BUFFER.append(toExport.getPrice());
		BUFFER.append(SEPARATOR);
		BUFFER.append(toExport.getCurrencyType());
		BUFFER.append(SEPARATOR);
		BUFFER.append(toExport.getPaymentMethod().getPaymentMethodId());
		BUFFER.append(SEPARATOR);
		BUFFER.append(toExport.getTransferLimit() != null ? toExport.getTransferLimit().getTransferLimitId() : Constants.EMPTY);
		BUFFER.append(SEPARATOR);
		BUFFER.append(toExport.getTransferDuration() != null ? toExport.getTransferDuration() : Constants.EMPTY);
		BUFFER.append(LINE_SEPARATOR);
		outStream.write(BUFFER.toString().getBytes());
	}

	Class<Fare> getSupportedClass() {
		return Fare.class;
	}

}
