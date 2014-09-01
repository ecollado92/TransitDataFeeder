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

import java.util.Arrays;

import org.ideaproject.QueryNames;
import org.ideaproject.model.Route;
import org.jboss.seam.annotations.Name;

@Name("routeList")
public class RouteList extends AbstractCountableLastResultEntityQuery<Route> {

	private static final String EJBQL = "select route from Route route";

	private static final String[] RESTRICTIONS = {
		    "route.agency.agencyId = #{agencyHome.agencyAgencyId}",
			"#{routeList.hasTripsForQuery} = (SELECT CASE COUNT(*) WHEN 0 THEN 'false' ELSE 'true' END FROM route.trips)",
			"lower(route.routeColor) like lower(concat(#{routeList.route.routeColor},'%'))",
			"lower(route.routeDesc) like lower(concat(#{routeList.route.routeDesc},'%'))",
			"lower(route.routeLongName) like lower(concat(#{routeList.route.routeLongName},'%'))",
			"lower(route.routeShortName) like lower(concat(#{routeList.route.routeShortName},'%'))",
			"lower(route.routeTextColor) like lower(concat(#{routeList.route.routeTextColor},'%'))",
			"lower(route.routeUrl) like lower(concat(#{routeList.route.routeUrl},'%'))",};

	private Route route = new Route();
	private Boolean hasTrips = null;

	public RouteList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Route getRoute() {
		return route;
	}


	public String getHasTripsForQuery() {
		if (hasTrips != null) {
			return hasTrips.toString();
		}
		return null;
	}

	/**
	 * @return the hasTrips
	 */
	public Boolean getHasTrips() {
		return hasTrips;
	}

	/**
	 * @param hasTrips the hasTrips to set
	 */
	public void setHasTrips(Boolean hasTrips) {
		this.hasTrips = hasTrips;
	}

	public Long getCurrentAgencyRouteCount() {
		return getNamedCountQueryResult(QueryNames.COUNT_AGENCY_ROUTES_ALL, agencyHome.getInstance());
	}

	public Long getCurrentAgencyNoTripsRouteCount() {
		return getNamedCountQueryResult(QueryNames.COUNT_AGENCY_ROUTES_NO_TRIPS, agencyHome.getInstance());
	}

}
