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
package org.ideaproject.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ideaproject.Constants;

/**
 * @author dirk
 *
 */
public final class StringUtil {
	private final static SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss");

	public static final boolean isEmpty(String toCheck) {
		return toCheck == null || toCheck.length() == 0;
	}

	public static final String formatTimeValue(Date value) {
		return formatTimeValue(value, false);
	}

	public static final String formatTimeValue(Date value, boolean carryover) {
		String result = Constants.DEFAULT_TIME_ENTRY;
		if (value != null) {
			if (carryover) {
				String[] parts = TIME_FORMATTER.format(value).split(Constants.TIME_SEPARATOR);
				int hour = Integer.parseInt(parts[0]);
				hour += Constants.HOURS_IN_DAY;
				StringBuffer resultBuff = new StringBuffer(16);
				resultBuff.append(hour);
				resultBuff.append(Constants.TIME_SEPARATOR);
				resultBuff.append(parts[1]);
				resultBuff.append(Constants.TIME_SEPARATOR);
				resultBuff.append(parts[2]);
				result = resultBuff.toString();
			} else {
				result = TIME_FORMATTER.format(value);
			}
		}
		return result;
	}
}
