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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ideaproject.Constants;
import org.ideaproject.Exportable;
import org.ideaproject.Constants.ExportType;
import org.ideaproject.export.CsvExporter;
import org.ideaproject.export.GenericExporter;

/**
 * @author dirk
 *
 */
public abstract class AbstractGtfsExporter<T extends Exportable> implements GenericExporter<T>,
		CsvExporter {
	private final static Object STRING_BUFFER_LOCK = new Object();
	private final static StringBuffer STRING_BUFFER = new StringBuffer();

	private final static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMdd");

	protected ExportType getSupportedExportType() {
		return ExportType.GTFS;
	}

	public final <E extends Exportable> boolean accept(Class<E> exportableClass,
			Constants.ExportType exportType) {
		return exportType == getSupportedExportType()
			&& getSupportedClass().isAssignableFrom(exportableClass);
	}
	
	abstract Class<T> getSupportedClass();

	protected static final String stringifyInput(String input) {
		synchronized(STRING_BUFFER_LOCK) {
			STRING_BUFFER.delete(0, STRING_BUFFER.length());
			if (input != null) {
				STRING_BUFFER.ensureCapacity(input.length() + 2);
				STRING_BUFFER.append("\"");
				STRING_BUFFER.append(input.replace("\"", "\"\""));
				STRING_BUFFER.append("\"");
			} else {
				STRING_BUFFER.append("\"\"");
			}
			return STRING_BUFFER.toString();
		}
	}

	protected static final String formatDateValue(Date value) {
		String result = Constants.EMPTY;
		if (value != null) {
			result = FORMATTER.format(value);
		}
		return result;
	}

}
