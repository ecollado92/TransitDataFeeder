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
import org.ideaproject.model.Transfer;

/**
 * @author dirk
 *
 */
public class TransferGtfsExporter extends AbstractGtfsExporter<Transfer> {

	public void export(Transfer toExport, OutputStream outStream)
			throws IOException {
		StringBuffer buffer = new StringBuffer();
		buffer.append(toExport.getFromLocation() != null ? toExport.getFromLocation().getStopId() : Constants.EMPTY);
		buffer.append(SEPARATOR);
		buffer.append(toExport.getToLocation() != null ? toExport.getToLocation().getStopId() : Constants.EMPTY);
		buffer.append(SEPARATOR);
		buffer.append(toExport.getTransferType().getTransferTypeId());
		buffer.append(SEPARATOR);
		buffer.append(toExport.getMinTransferTime() != null ? toExport.getMinTransferTime() : Constants.EMPTY);
		buffer.append(LINE_SEPARATOR);
		outStream.write(buffer.toString().getBytes());
	}

	final Class<Transfer> getSupportedClass() {
		return Transfer.class;
	}

}
