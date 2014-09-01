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

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.model.Agency;
import org.jboss.seam.annotations.In;

/**
 * Extension of {@link org.ideaproject.action.entityquery.AbstractLastResultEntityQuery} which adds
 * the capability of returning the results of a &quot;count&quot; named query.
 * 
 * @author dirk
 * @see org.ideaproject.action.entityquery.AbstractLastResultEntityQuery
 */
public abstract class AbstractCountableLastResultEntityQuery<E> extends
		AbstractLastResultEntityQuery<E> 
{
	@In(create = true)
	protected AgencyHome agencyHome;

	/**
	 * Returns the result of the given named query (assumed to be a count query, or minimally
	 * one which returns a single long value).  The named query is expected to take an agency
	 * as a parameter; the provided agency will be sent to it.
	 * 
	 * @param queryName the name of the named query
	 * @param agency the agency to be provided to the named query.
	 * @return the result of the given named query
	 */
	protected Long getNamedCountQueryResult(String queryName, Agency agency) {
		Long result = null;
		
		Query query = getEntityManager().createNamedQuery(queryName);
		try {
			result = (Long) query.setParameter(1, agency).getSingleResult();
		} catch (NoResultException e) {
			/** TODO: add logging as/if necessary **/
		}
		return result;		
	}

}
