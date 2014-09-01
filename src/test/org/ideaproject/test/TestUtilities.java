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
package org.ideaproject.test;

import java.util.Date;
import java.util.Random;

/**
 * @author dirk
 *
 */
public final class TestUtilities {
	private static final long YEAR_IN_MILLISEC = 1000L * 60L * 60L * 24L * 365L;
	private static Object RANDOM_LOCK = new Object();
	private static Random RANDOM;

	public static final Date generatePastDate() {
		return generateDateBefore(new Date());
	}

	public static final Date generateDateBefore(Date after) {
		Date localAfterDate = after;
		if (localAfterDate == null) {
			localAfterDate = new Date();
		}
		return generateDateBetween(new Date(localAfterDate.getTime() - YEAR_IN_MILLISEC), localAfterDate);
	}

	public static final Date generateFutureDate() {
		return generateDateAfter(new Date());
	}

	public static final Date generateDateAfter(Date before) {
		Date localBeforeDate = before;
		if (localBeforeDate == null) {
			localBeforeDate = new Date();
		}
		return generateDateBetween(localBeforeDate, new Date(localBeforeDate.getTime() + YEAR_IN_MILLISEC));
	}

	public static final Date generateDateBetween(Date before, Date after) {
		if (before == null || after == null) {
			throw new IllegalArgumentException("Before and after dates must not be null");
		}
		long timespan = before.getTime() - after.getTime();
		return new Date(getRandom().nextLong() / timespan);
	}

	private static final Random getRandom() {
		synchronized(RANDOM_LOCK) {
			if (RANDOM == null) {
				RANDOM = new Random();
			}
			return RANDOM;
		}
	}
}
