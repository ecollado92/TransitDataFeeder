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

import java.lang.reflect.Array;

/**
 * Utility for calculating an object's hash code. Note that this utility class
 * is based upon code currently found at the URL
 * http://www.javapractices.com/topic/TopicAction.do?Id=28.
 * 
 * @author sakai@elluminate.com
 * 
 */
public final class HashCodeUtil {
    /**
     * An initial value for a <code>hashCode</code>, to which is added
     * contributions from fields. Using a non-zero value decreases collisons of
     * <code>hashCode</code> values.
     */
    public static final int SEED = 23;

    /**
     * Multiplier for each subsequent field; meant to enforce uniqueness of hash
     * codes.
     */
    private static final int ODD_PRIME_NUMBER = 37;

    /**
     * Hashes the provided boolean argument and adds it to the
     * cumulatively-built seed.
     */
    public static int hash(final int seed, final boolean toHash) {
	return firstTerm(seed) + (toHash ? 1 : 0);
    }

    /**
     * Hashes the provided character argument and adds it to the
     * cumulatively-built seed.
     */
    public static int hash(final int seed, final char toHash) {
	return firstTerm(seed) + (int) toHash;
    }

    /**
     * Hashes the provided integer argument and adds it to the
     * cumulatively-built seed. Implementation Note: bytes and shorts are also
     * passed through this method by way of Java's implicit conversion.
     */
    public static int hash(final int seed, final int toHash) {
	return firstTerm(seed) + toHash;
    }

    /**
     * Hashes the provided long argument and adds it to the cumulatively-built
     * seed.
     */
    public static int hash(final int seed, final long toHash) {
	return firstTerm(seed) + (int) (toHash ^ (toHash >>> 32));
    }

    /**
     * Hashes the provided float argument and adds it to the cumulatively-built
     * seed.
     */
    public static int hash(final int seed, final float toHash) {
	return hash(seed, Float.floatToIntBits(toHash));
    }

    /**
     * Hashes the provided float argument and adds it to the cumulatively-built
     * seed.
     */
    public static int hash(final int seed, final double toHash) {
	return hash(seed, Double.doubleToLongBits(toHash));
    }

    /**
     * <code>aObject</code> is a possibly-null object field, and possibly an
     * array.
     * 
     * If <code>aObject</code> is an array, then each element may be a primitive
     * or a possibly-null object.
     */
    public static int hash(final int seed, final Object toHash) {
	int result = seed;
	if (toHash == null) {
	    result = hash(result, 0);
	} else if (!isArray(toHash)) {
	    result = hash(result, toHash.hashCode());
	} else {
	    int length = Array.getLength(toHash);
	    for (int idx = 0; idx < length; ++idx) {
		Object item = Array.get(toHash, idx);
		// recursive call!
		result = hash(result, item);
	    }
	}
	return result;
    }

    private static int firstTerm(final int seed) {
	return ODD_PRIME_NUMBER * seed;
    }

    private static boolean isArray(Object aObject) {
	return aObject.getClass().isArray();
    }

}
