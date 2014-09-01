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
import org.ideaproject.action.entityhome.StationHome;
import org.ideaproject.action.entityhome.ZoneHome;
import org.ideaproject.action.entityquery.StationList;
import org.ideaproject.model.Agency;
import org.ideaproject.model.Direction;
import org.ideaproject.model.Zone;
import org.ideaproject.test.AbstractAdminLoginSeamTest;
import org.ideaproject.test.TestConstants;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class StationCRUDTest extends AbstractAdminLoginSeamTest {

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
				StationList stationList = (StationList) getInstance("stationList");

				agencyHome.setAgencyAgencyId(TestConstants.DEFAULT_AGENCY_ID);
				agencyHome.load();

				directionHome.setDirectionDirectionId(TestConstants.DEFAULT_DIRECTION_ID);
				directionHome.load();
				
				zoneHome.setZoneZoneId(TestConstants.DEFAULT_ZONE_ID);
				zoneHome.load();

				assert stationHome.isWired();
				assert !stationHome.isManaged();

				createStation(stationHome, agencyHome.getInstance(), 
						zoneHome.getInstance(), directionHome.getInstance(), TestConstants.TEST_STATION_NAME_1);				
				assert TestConstants.PERSISTED.equals(stationHome.persist());

				stationList.getStation().setAgency(agencyHome.getInstance());
				assert stationList.getResultList().contains(stationHome.getInstance());

				stationList.getStation().setDirection(directionHome.getInstance());
				stationList.refresh();
				assert stationList.getResultList().contains(stationHome.getInstance());
				
				stationList.setZoneId(zoneHome.getZoneZoneId());
				stationList.refresh();
				assert stationList.getResultList().contains(stationHome.getInstance());
				
				stationList.getStation().setStopName(TestConstants.TEST_STATION_NAME_1);
				stationList.refresh();
				assert stationList.getResultList().contains(stationHome.getInstance());

				stationList.setGeolocated(Boolean.FALSE);
				stationList.refresh();
				assert stationList.getResultList().contains(stationHome.getInstance());

				Integer stationId = stationHome.getStopStopId();
				assert stationId != null;
				assert stationHome.isManaged();

				stationHome.clearInstance();
				assert stationHome.getDefinedInstance() == null;
				stationHome.load();
				assert stationHome.getDefinedInstance() == null;
				stationHome.setStopStopId(stationId);
				stationHome.load();

				assert stationHome.getDefinedInstance() != null;
				assert TestConstants.TEST_STATION_NAME_1.equals(stationHome.getInstance().getStopName());

				stationHome.getInstance().setStopLat(TestConstants.TEST_LOCATION_LATITUDE);
				stationHome.getInstance().setStopLon(TestConstants.TEST_LOCATION_LONGITUDE);

				assert TestConstants.UPDATED.equals(stationHome.update());

				stationList.setGeolocated(Boolean.TRUE);
				stationList.refresh();
				assert stationList.getResultList().contains(stationHome.getInstance());
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
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				DirectionHome directionHome = (DirectionHome) getInstance("directionHome");
				ZoneHome zoneHome = (ZoneHome) getInstance("zoneHome");
				StationHome stationHome = (StationHome) getInstance("stationHome");
				StationList stationList = (StationList) getInstance("stationList");

				agencyHome.setAgencyAgencyId(TestConstants.DEFAULT_AGENCY_ID);
				agencyHome.load();

				directionHome.setDirectionDirectionId(TestConstants.DEFAULT_DIRECTION_ID);
				directionHome.load();
				
				zoneHome.setZoneZoneId(TestConstants.DEFAULT_ZONE_ID);
				zoneHome.load();

				assert stationHome.isWired();
				assert !stationHome.isManaged();

				createStation(stationHome, agencyHome.getInstance(), 
						zoneHome.getInstance(), directionHome.getInstance(), TestConstants.TEST_STATION_NAME_1,
						TestConstants.TEST_LOCATION_LATITUDE, TestConstants.TEST_LOCATION_LONGITUDE);
				assert TestConstants.PERSISTED.equals(stationHome.persist());

				stationList.setGeolocated(Boolean.TRUE);
				stationList.refresh();
				assert stationList.getResultList().contains(stationHome.getInstance());

				Integer stationId = stationHome.getStopStopId();
				assert stationId != null;

				stationHome.clearInstance();
				assert stationHome.getDefinedInstance() == null;
				stationHome.load();
				assert stationHome.getDefinedInstance() == null;
				stationHome.setStopStopId(stationId);
				stationHome.load();

				stationHome.getInstance().setStopLat(null);
				stationHome.getInstance().setStopLon(null);

				assert TestConstants.UPDATED.equals(stationHome.update());

				stationList.setGeolocated(Boolean.FALSE);
				stationList.refresh();
				assert stationList.getResultList().contains(stationHome.getInstance());
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
	
	protected void createStation(StationHome stationHome,
			Agency agency, Zone zone, Direction direction, String name) 
	throws Exception {
		createStation(stationHome, agency, zone, direction, name, null, null);
	}

	protected void createStation(StationHome stationHome,
			Agency agency, Zone zone, Direction direction, String name, Double lat, Double lon) 
	throws Exception {		
		stationHome.getInstance().setAgency(agency);
		stationHome.getInstance().setZone(zone);
		stationHome.getInstance().setDirection(direction);
		stationHome.getInstance().setStopName(name);
		stationHome.getInstance().setStopLat(lat);
		stationHome.getInstance().setStopLon(lon);
	}
}
