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

import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityhome.DirectionHome;
import org.ideaproject.action.entityhome.RouteHome;
import org.ideaproject.action.entityhome.StationHome;
import org.ideaproject.action.entityhome.StopHome;
import org.ideaproject.action.entityhome.ZoneHome;
import org.ideaproject.action.entityquery.StopList;
import org.ideaproject.model.Agency;
import org.ideaproject.model.Direction;
import org.ideaproject.model.Station;
import org.ideaproject.model.Zone;
import org.ideaproject.test.AbstractAdminLoginSeamTest;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class StopCRUDTest extends AbstractAdminLoginSeamTest {

	@Test
	@Transactional
	public void testCreate() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				DirectionHome directionHome = (DirectionHome) getInstance("directionHome");
				ZoneHome zoneHome = (ZoneHome) getInstance("zoneHome");
				StationHome stationHome = (StationHome) getInstance("stationHome");
				RouteHome routeHome = (RouteHome) getInstance("routeHome");
				StopHome stopHome = (StopHome) getInstance("stopHome");
				StopList stopList = (StopList) getInstance("stopList");

				agencyHome.setAgencyAgencyId(TestConstants.DEFAULT_AGENCY_ID);
				agencyHome.load();

				directionHome.setDirectionDirectionId(TestConstants.DEFAULT_DIRECTION_ID);
				directionHome.load();
				
				zoneHome.setZoneZoneId(TestConstants.DEFAULT_ZONE_ID);
				zoneHome.load();

				routeHome.setRouteRouteId(TestConstants.DEFAULT_ROUTE_ID);
				routeHome.load();
				
				stationHome.setStopStopId(TestConstants.DEFAULT_STATION_ID_1);
				stationHome.load();

				assert stopHome.isWired();
				assert !stopHome.isManaged();

				createStop(stopHome, agencyHome.getInstance(), 
						zoneHome.getInstance(), stationHome.getInstance(), directionHome.getInstance(),
						TestConstants.TEST_STOP_NAME_1);
				assert TestConstants.PERSISTED.equals(stopHome.persist());

				stopList.getStop().setAgency(agencyHome.getInstance());
				assert stopList.getResultList().contains(stopHome.getInstance());

				stopList.getStop().setDirection(directionHome.getInstance());
				stopList.refresh();
				assert stopList.getResultList().contains(stopHome.getInstance());
				
				stopList.setZoneId(zoneHome.getZoneZoneId());
				stopList.refresh();
				assert stopList.getResultList().contains(stopHome.getInstance());
				
				stopList.getStop().setStopName(TestConstants.TEST_STOP_NAME_1);
				stopList.refresh();
				assert stopList.getResultList().contains(stopHome.getInstance());

				stopList.setGeolocated(Boolean.FALSE);
				stopList.refresh();
				assert stopList.getResultList().contains(stopHome.getInstance());

				stopList.setParentStationId(stationHome.getInstance().getStopId());
				stopList.refresh();
				assert stopList.getResultList().contains(stopHome.getInstance());

				assert stopList.getResultCount() == stopList.getLastResult() + 1;

				Integer stopId = stopHome.getStopStopId();
				assert stopId != null;
				assert stopHome.isManaged();

				stopHome.clearInstance();
				assert stopHome.getDefinedInstance() == null;
				stopHome.load();
				assert stopHome.getDefinedInstance() == null;
				stopHome.setStopStopId(stopId);
				stopHome.load();

				assert stopHome.getDefinedInstance() != null;
				assert TestConstants.TEST_STOP_NAME_1.equals(stopHome.getInstance().getStopName());
//
//				tx.begin();
//				assert REMOVED.equals(blockHome.remove());
//				tx.commit();
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
//				AgencyGroupHome agencyGroupHome = (AgencyGroupHome) getInstance("agencyGroupHome");
//				assert agencyGroupHome.isWired();
//				assert !agencyGroupHome.isManaged();
//				Integer ahId = createAgencyHome(agencyGroupHome, AG_NAME);
//				assert ahId != null;
//				assert agencyGroupHome.isManaged();
//
//				agencyGroupHome.clearInstance();
//				agencyGroupHome.setAgencyGroupAgencyGroupId(ahId);
//				agencyGroupHome.load();
//				agencyGroupHome.getInstance().setGroupName(BOGUS_AG_NAME);
//				agencyGroupHome.update();
//				
//				agencyGroupHome.clearInstance();
//				assert agencyGroupHome.getAgencyGroupAgencyGroupId() == null;
//				agencyGroupHome.load();
//				assert !agencyGroupHome.isManaged();
//				assert agencyGroupHome.getDefinedInstance() == null;
//				agencyGroupHome.setAgencyGroupAgencyGroupId(ahId);
//				agencyGroupHome.load();
//				assert agencyGroupHome.isManaged();
//				assert agencyGroupHome.getDefinedInstance() != null;
//				assert BOGUS_AG_NAME.equals(agencyGroupHome.getInstance().getGroupName());
////				assert agencyGroupHome.getInstance().getDateLastModified() != null;
////				assert agencyGroupHome.getInstance().getUserLastModified() != null;
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
//				AgencyGroupHome agencyGroupHome = (AgencyGroupHome) getInstance("agencyGroupHome");
//				assert agencyGroupHome.isWired();
//				assert !agencyGroupHome.isManaged();
//				Integer ahId = createAgencyHome(agencyGroupHome, AG_NAME);
//				assert ahId != null;
//				assert agencyGroupHome.isManaged();
//				
//				agencyGroupHome.clearInstance();
//				assert agencyGroupHome.getAgencyGroupAgencyGroupId() == null;
//				agencyGroupHome.load();
//				assert !agencyGroupHome.isManaged();
//				assert agencyGroupHome.getDefinedInstance() == null;
//				agencyGroupHome.setAgencyGroupAgencyGroupId(ahId);
//				agencyGroupHome.load();
//				assert agencyGroupHome.isManaged();
//				assert agencyGroupHome.getDefinedInstance() != null;
//				assert AG_NAME.equals(agencyGroupHome.getInstance().getGroupName());
////				assert agencyGroupHome.getInstance().getDateLastModified() != null;
////				assert agencyGroupHome.getInstance().getUserLastModified() != null;
//
//				agencyGroupHome.remove();
//				agencyGroupHome.clearInstance();
//				assert !agencyGroupHome.isManaged();
				
			}
		}.run();

		logoutAdmin();
	}

	protected void createStop(StopHome stopHome,
			Agency agency, Zone zone, Station parentStation, Direction direction, String name) 
	throws Exception {
		createStop(stopHome, agency, zone, parentStation, direction, name, null, null);
	}

	protected void createStop(StopHome stopHome,
			Agency agency, Zone zone, Station parentStation, Direction direction, String name,
			Double lat, Double lon) 
	throws Exception {
		stopHome.getInstance().setAgency(agency);
		stopHome.getInstance().setZone(zone);
		stopHome.getInstance().setParentStation(parentStation);
		stopHome.getInstance().setDirection(direction);
		stopHome.getInstance().setStopName(name);
		stopHome.getInstance().setStopLat(lat);
		stopHome.getInstance().setStopLon(lon);
	}

}
