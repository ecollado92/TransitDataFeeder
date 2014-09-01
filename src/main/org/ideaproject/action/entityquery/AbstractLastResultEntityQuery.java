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
package org.ideaproject.action.entityquery;

import org.jboss.seam.framework.EntityQuery;

/**
 * Extension of {@link org.jboss.seam.freamework.EntityQuery} which adds the capability
 * of getting the last result index for the current results.
 * 
 * @author dirk
 * @see org.jboss.seam.freamework.EntityQuery
 */
public abstract class AbstractLastResultEntityQuery<E> extends EntityQuery<E> {
	/**
	 * Returns the integer index of the last result for the current query.  In paginated
	 * queries, this will provide the minimum of the values of the number of items per
	 * page and the number of items returned in the query.
	 * 
	 * @return the integer index of the last result for the current query.
	 */
	public final Integer getLastResult() {
		return Math.min(getResultCount().intValue(), getNextFirstResult()) - 1;
	}

}
