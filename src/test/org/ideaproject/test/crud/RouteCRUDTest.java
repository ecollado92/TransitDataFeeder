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
package org.ideaproject.test.crud;

import javax.persistence.PersistenceException;

import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityhome.BikeOptionHome;
import org.ideaproject.action.entityhome.RouteHome;
import org.ideaproject.action.entityhome.RouteTypeHome;
import org.ideaproject.action.entityquery.RouteList;
import org.ideaproject.test.AbstractAdminLoginSeamTest;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class RouteCRUDTest extends AbstractAdminLoginSeamTest {

	@Test
	@Transactional
	public void testCreate() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				RouteTypeHome routeTypeHome = (RouteTypeHome) getInstance("routeTypeHome");
				BikeOptionHome bikeOptionHome = (BikeOptionHome) getInstance("bikeOptionHome");
				RouteHome routeHome = (RouteHome) getInstance("routeHome");
				RouteList routeList = (RouteList) getInstance("routeList");

				agencyHome.setAgencyAgencyId(TestConstants.DEFAULT_AGENCY_ID);
				agencyHome.load();
				assert agencyHome.getDefinedInstance() != null;

				bikeOptionHome.setBikeOptionBikeOptionId(TestConstants.DEFAULT_BIKE_OPTION_ID);
				bikeOptionHome.load();
				assert bikeOptionHome.getDefinedInstance() != null;

				routeTypeHome.setRouteTypeRouteTypeId(TestConstants.DEFAULT_ROUTE_TYPE_ID);
				routeTypeHome.load();
				assert routeTypeHome.getDefinedInstance() != null;

				assert routeHome.isWired();
				assert !routeHome.isManaged();

				routeHome.getInstance().setAgency(agencyHome.getInstance());
				routeHome.getInstance().setRouteShortName(TestConstants.TEST_ROUTE_SHORT_NAME);
				routeHome.getInstance().setRouteLongName(TestConstants.TEST_ROUTE_LONG_NAME);
				routeHome.getInstance().setBikeOption(bikeOptionHome.getInstance());
				routeHome.getInstance().setRouteType(routeTypeHome.getInstance());

				assert TestConstants.PERSISTED.equals(routeHome.persist());

				routeList.getRoute().setAgency(agencyHome.getInstance());
				assert routeList.getResultList().contains(routeHome.getInstance());
				
				routeList.getRoute().setBikeOption(bikeOptionHome.getInstance());
				routeList.refresh();
				assert routeList.getResultList().contains(routeHome.getInstance());

				routeList.getRoute().setRouteType(routeTypeHome.getInstance());
				routeList.refresh();
				assert routeList.getResultList().contains(routeHome.getInstance());
				
				routeList.getRoute().setRouteShortName(TestConstants.TEST_ROUTE_SHORT_NAME);
				routeList.refresh();
				assert routeList.getResultList().contains(routeHome.getInstance());
				
				routeList.getRoute().setRouteLongName(TestConstants.TEST_ROUTE_LONG_NAME);
				routeList.refresh();
				assert routeList.getResultList().contains(routeHome.getInstance());

				routeList.setHasTrips(Boolean.TRUE);
				routeList.refresh();
				try {
				assert !routeList.getResultList().contains(routeHome.getInstance());
				} catch (PersistenceException e) {
					System.out.println("MARK MARK MARK");
					throw e;
				}
				routeList.setHasTrips(Boolean.FALSE);
				routeList.refresh();
				assert routeList.getResultList().contains(routeHome.getInstance());

				Long routeCountNoTrips = routeList.getCurrentAgencyNoTripsRouteCount();
				Long routeCountAll = routeList.getCurrentAgencyRouteCount();
				assert routeCountNoTrips.longValue() <= routeCountAll.longValue();

				assert routeHome.isManaged();
				assert routeHome.getRouteRouteId() != null;
				Integer routeId = routeHome.getRouteRouteId();
				assert routeHome.getInstance().getRouteId() == routeId.intValue();

				routeHome.clearInstance();
				assert routeHome.getDefinedInstance() == null;
				routeHome.load();
				assert routeHome.getDefinedInstance() == null;
				routeHome.setRouteRouteId(routeId);
				routeHome.load();

				assert routeHome.getDefinedInstance() != null;
				assert TestConstants.TEST_ROUTE_SHORT_NAME.equals(routeHome.getInstance().getRouteShortName());
				assert TestConstants.TEST_ROUTE_LONG_NAME.equals(routeHome.getInstance().getRouteLongName());
			}
		}.run();

		logoutAdmin();
	}

	@Test
	@Transactional
	public void testUpdate() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				setValue("#{agencyHome.agencyAgencyId}", 11);
				invokeMethod("#{agencyHome.load}");
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");

				setValue("#{bikeOptionHome.bikeOptionBikeOptionId}", 11111);
				invokeMethod("#{bikeOptionHome.load}");
				BikeOptionHome bikeOptionHome = (BikeOptionHome) getInstance("bikeOptionHome");

				setValue("#{routeTypeHome.routeTypeRouteTypeId}", 111111);
				invokeMethod("#{routeTypeHome.load}");
				RouteTypeHome routeTypeHome = (RouteTypeHome) getInstance("routeTypeHome");

				RouteHome routeHome = (RouteHome) getInstance("routeHome");
				assert routeHome.isWired();
				assert !routeHome.isManaged();

				routeHome.getInstance().setAgency(agencyHome.getInstance());
				routeHome.getInstance().setRouteShortName(TestConstants.TEST_ROUTE_SHORT_NAME);
				routeHome.getInstance().setRouteLongName(TestConstants.TEST_ROUTE_LONG_NAME);
				routeHome.getInstance().setBikeOption(bikeOptionHome.getInstance());
				routeHome.getInstance().setRouteType(routeTypeHome.getInstance());

				assert TestConstants.PERSISTED.equals(routeHome.persist());

				Integer routeId = routeHome.getRouteRouteId();
				assert routeId != null;
				
				routeHome.clearInstance();
				routeHome.setRouteRouteId(routeId);
				routeHome.load();

				routeHome.getInstance().setRouteUrl(TestConstants.TEST_ROUTE_URL);

				assert TestConstants.UPDATED.equals(routeHome.update());

				routeHome.clearInstance();
				routeHome.setRouteRouteId(routeId);
				routeHome.load();

				assert TestConstants.TEST_ROUTE_URL.equals(routeHome.getInstance().getRouteUrl());
			}
		}.run();

		logoutAdmin();
	}

	@Test
	@Transactional
	public void testRemove() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				setValue("#{agencyHome.agencyAgencyId}", 11);
				invokeMethod("#{agencyHome.load}");
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");

				setValue("#{bikeOptionHome.bikeOptionBikeOptionId}", 11111);
				invokeMethod("#{bikeOptionHome.load}");
				BikeOptionHome bikeOptionHome = (BikeOptionHome) getInstance("bikeOptionHome");

				setValue("#{routeTypeHome.routeTypeRouteTypeId}", 111111);
				invokeMethod("#{routeTypeHome.load}");
				RouteTypeHome routeTypeHome = (RouteTypeHome) getInstance("routeTypeHome");

				RouteHome routeHome = (RouteHome) getInstance("routeHome");
				assert routeHome.isWired();
				assert !routeHome.isManaged();

				routeHome.getInstance().setAgency(agencyHome.getInstance());
				routeHome.getInstance().setRouteShortName(TestConstants.TEST_ROUTE_SHORT_NAME);
				routeHome.getInstance().setRouteLongName(TestConstants.TEST_ROUTE_LONG_NAME);
				routeHome.getInstance().setBikeOption(bikeOptionHome.getInstance());
				routeHome.getInstance().setRouteType(routeTypeHome.getInstance());

				assert TestConstants.PERSISTED.equals(routeHome.persist());

				Integer routeId = routeHome.getRouteRouteId();
				assert routeId != null;
				
				routeHome.clearInstance();
				routeHome.setRouteRouteId(routeId);
				routeHome.load();

				assert TestConstants.REMOVED.equals(routeHome.remove());
				
				routeHome.setRouteRouteId(routeId);
				routeHome.load();
			}
		}.run();

		logoutAdmin();
	}
}
