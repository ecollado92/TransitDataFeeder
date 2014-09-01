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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ideaproject.Constants;
import org.ideaproject.action.ValidationTableBean;
import org.ideaproject.action.entityhome.AgencyGroupHome;
import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityhome.BlockHome;
import org.ideaproject.action.entityhome.CalendarDateExceptionHome;
import org.ideaproject.action.entityhome.CalendarDateHome;
import org.ideaproject.action.entityhome.CalendarHome;
import org.ideaproject.action.entityhome.DirectionHome;
import org.ideaproject.action.entityhome.FareHome;
import org.ideaproject.action.entityhome.FareRuleHome;
import org.ideaproject.action.entityhome.FrequencyHome;
import org.ideaproject.action.entityhome.RouteHome;
import org.ideaproject.action.entityhome.ServiceScheduleBoundHome;
import org.ideaproject.action.entityhome.ServiceScheduleGroupHome;
import org.ideaproject.action.entityhome.StationHome;
import org.ideaproject.action.entityhome.StopHome;
import org.ideaproject.action.entityhome.StopTimeHome;
import org.ideaproject.action.entityhome.TransferHome;
import org.ideaproject.action.entityhome.TripHome;
import org.ideaproject.action.entityhome.ZoneHome;
import org.ideaproject.model.Agency;
import org.ideaproject.model.AgencyGroup;
import org.ideaproject.model.BikeOption;
import org.ideaproject.model.Block;
import org.ideaproject.model.Calendar;
import org.ideaproject.model.CalendarDate;
import org.ideaproject.model.CalendarDateException;
import org.ideaproject.model.CalendarExceptionType;
import org.ideaproject.model.Direction;
import org.ideaproject.model.Fare;
import org.ideaproject.model.FareRule;
import org.ideaproject.model.Frequency;
import org.ideaproject.model.Location;
import org.ideaproject.model.PaymentMethod;
import org.ideaproject.model.PickupType;
import org.ideaproject.model.Route;
import org.ideaproject.model.RouteType;
import org.ideaproject.model.ServiceScheduleBound;
import org.ideaproject.model.ServiceScheduleGroup;
import org.ideaproject.model.Station;
import org.ideaproject.model.Stop;
import org.ideaproject.model.StopTime;
import org.ideaproject.model.Transfer;
import org.ideaproject.model.TransferLimit;
import org.ideaproject.model.TransferType;
import org.ideaproject.model.Trip;
import org.ideaproject.model.Zone;
import org.jboss.seam.annotations.Transactional;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public abstract class ObjectTreeBuildingSeamTest extends AbstractAdminLoginSeamTest {

	@Test
	@Transactional
	public void testCreateAndRemove() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				createObjectTree();
			}
		}.run();
	}

//	@Test
//	public void testUpdate() throws Exception {
//		loginAdmin();
//
//		new FacesRequest() {
//			@Override
//			protected void invokeApplication() throws Exception {
//				setValue("#{agencyHome.agencyAgencyId}", TestConstants.DEFAULT_AGENCY_ID);
//				invokeMethod("#{agencyHome.load}");
//				assert invokeMethod("#{agencyHome.getDefinedInstance}") != null;
//
//				setValue("#{agencyHome.instance.agencyName}", TestConstants.BOGUS_AGENCY_NAME);
//				assert TestConstants.UPDATED.equals(invokeMethod("#{agencyHome.update}"));
//				
//				invokeMethod("#{agencyHome.clearInstance}");
//				assert invokeMethod("#{agencyHome.getDefinedInstance}") == null;
//
//				setValue("#{agencyHome.agencyAgencyId}", TestConstants.DEFAULT_AGENCY_ID);
//				invokeMethod("#{agencyHome.load}");
//				assert invokeMethod("#{agencyHome.getDefinedInstance}") != null;
//
//				Agency agency = (Agency) invokeMethod("#{agencyHome.getInstance}");
//				assert agency != null;
//				assert TestConstants.BOGUS_AGENCY_NAME.equals(agency.getAgencyName());
//			}
//		}.run();
//	}

//	@Test
//	public void testRemove() throws Exception {
//		loginAdmin();
//
//		new FacesRequest() {
//			@Override
//			protected void invokeApplication() throws Exception {
//				invokeMethod("#{agencyHome.load}");
//
//				assert invokeMethod("#{agencyHome.getDefinedInstance}") == null;
//				
//				setValue("#{agencyHome.instance.agencyName}", TestConstants.TEST_AGENCY_NAME);
//				setValue("#{agencyHome.instance.agencyShortName}", TestConstants.TEST_AGENCY_SHORT_NAME);
//				setValue("#{agencyHome.instance.agencyLanguage}", TestConstants.TEST_AGENCY_LANG);
//				setValue("#{agencyHome.instance.agencyTimezone}", TestConstants.TEST_AGENCY_TZ);
//				setValue("#{agencyHome.instance.agencyUrl}", TestConstants.TEST_AGENCY_URL);
//
//				assert TestConstants.PERSISTED.equals(invokeMethod("#{agencyHome.persist}"));
//				Agency agency = (Agency) invokeMethod("#{agencyHome.getInstance}");
//				assert agency != null;
//			}
//		}.run();
//	}

	protected void createObjectTree() {
		AgencyGroupHome agencyGroupHome = (AgencyGroupHome) getInstance("agencyGroupHome");
		agencyGroupHome.getInstance().setGroupName(TestConstants.TEST_AGENCY_GROUP_NAME);
		agencyGroupHome.persist();

		agencyGroupHome.getInstance().getAgencies().add(createAgency(agencyGroupHome.getInstance()));
		agencyGroupHome.update();
	}

	protected Agency createAgency(AgencyGroup agencyGroup) {
		AgencyHome agencyHome = (AgencyHome)getInstance("agencyHome");
		agencyHome.getInstance().getAgencyGroups().add(agencyGroup);
		agencyHome.getInstance().setAgencyName(TestConstants.TEST_AGENCY_NAME);
		agencyHome.getInstance().setAgencyShortName(TestConstants.TEST_AGENCY_SHORT_NAME);
		agencyHome.getInstance().setAgencyLanguage(TestConstants.TEST_AGENCY_LANG);
		agencyHome.getInstance().setAgencyTimezone(TestConstants.TEST_AGENCY_TZ);
		agencyHome.getInstance().setAgencyUrl(TestConstants.TEST_AGENCY_URL);

		agencyHome.persist();

		Direction direction = createDirection(agencyHome.getInstance());
		agencyHome.getInstance().getDirections().add(direction);

		Zone zone1 = createZone(agencyHome.getInstance(), TestConstants.TEST_ZONE_NAME_1);
		agencyHome.getInstance().getZones().add(zone1);
		Zone zone2 = createZone(agencyHome.getInstance(), TestConstants.TEST_ZONE_NAME_2);
		agencyHome.getInstance().getZones().add(zone2);
		Zone zone3 = createZone(agencyHome.getInstance(), TestConstants.TEST_ZONE_NAME_3);
		agencyHome.getInstance().getZones().add(zone3);

		Route route = createRoute(agencyHome.getInstance());
		agencyHome.getInstance().getRoutes().add(route);

		Station station1 = createStation(agencyHome.getInstance(), zone1, direction, TestConstants.TEST_STATION_NAME_1);
		agencyHome.getInstance().getStations().add(station1);
		Station station2 = createStation(agencyHome.getInstance(), zone1, direction, TestConstants.TEST_STATION_NAME_2);
		agencyHome.getInstance().getStations().add(station2);

		Stop stop1 = createStop(agencyHome.getInstance(), zone1, direction, station1, TestConstants.TEST_STOP_NAME_1);
		Stop stop2 = createStop(agencyHome.getInstance(), zone1, direction, station2, TestConstants.TEST_STOP_NAME_2);
		List<Stop> stopList = new ArrayList<Stop>();
		stopList.add(stop1);
		stopList.add(stop2);
		agencyHome.getInstance().getStops().addAll(stopList);

		Block block = createBlock(agencyHome.getInstance());
		agencyHome.getInstance().getBlocks().add(block);

		agencyHome.getInstance().getTransfers().add(createTransfer(agencyHome.getInstance(), station1, station2));
		agencyHome.getInstance().getTransfers().add(createTransfer(agencyHome.getInstance(), stop1, stop2));
		
		ServiceScheduleGroup schedule = createServiceScheduleGroup(agencyHome.getInstance());
		agencyHome.getInstance().getScheduleGroups().add(schedule);

		ServiceScheduleBound scheduleBound = createServiceScheduleBound(schedule);
		
		Calendar calendar = createCalendar(schedule);
		schedule.getCalendars().add(calendar);

		CalendarDate calendarDate = createCalendarDate(agencyHome.getInstance(), calendar, null);
		agencyHome.getInstance().getCalendarDates().add(calendarDate);

		Trip trip = createTrip(agencyHome.getInstance(), route, calendar, block, direction, stopList);
		route.getTrips().add(trip);

//		agencyHome.getInstance().getFares().add(createFare(agencyHome.getInstance(), route, zone1, zone2, zone3));
		createFare(agencyHome.getInstance(), route, zone1, zone2, zone3);
		return agencyHome.getInstance();
	}

	protected Route createRoute(Agency agency) {
		ValidationTableBean validationTableBean = (ValidationTableBean) getInstance("validationTableBean");
		RouteHome routeHome = (RouteHome) getInstance("routeHome");

		BikeOption bikeOption = validationTableBean.getBikeOptions().get(0);
		RouteType routeType = validationTableBean.getRouteTypes().get(0);

		routeHome.getInstance().setAgency(agency);
		routeHome.getInstance().setBikeOption(bikeOption);
		routeHome.getInstance().setRouteShortName(TestConstants.TEST_ROUTE_SHORT_NAME);
		routeHome.getInstance().setRouteLongName(TestConstants.TEST_ROUTE_LONG_NAME);
		routeHome.getInstance().setBikeOption(bikeOption);
		routeHome.getInstance().setRouteType(routeType);

		routeHome.persist();
		return routeHome.getInstance();
	}

	protected Zone createZone(Agency agency, String name) {
		ZoneHome zoneHome = (ZoneHome) getInstance("zoneHome");
		zoneHome.getInstance().setAgency(agency);
		zoneHome.getInstance().setZoneName(name);
		zoneHome.persist();
		return zoneHome.getInstance();
	}

	protected Block createBlock(Agency agency) {
		BlockHome blockHome = (BlockHome) getInstance("blockHome");
		blockHome.getInstance().setAgency(agency);
		blockHome.getInstance().setBlockLabel(TestConstants.TEST_BLOCK_LABEL);

		blockHome.persist();

		return blockHome.getInstance();
	}

	protected Direction createDirection(Agency agency) {
		DirectionHome directionHome = (DirectionHome) getInstance("directionHome");
		directionHome.getInstance().setAgency(agency);
		directionHome.getInstance().setDirectionLabel(TestConstants.TEST_DIRECTION_LABEL);
		directionHome.getInstance().setInboundFlag(Boolean.TRUE);
		directionHome.persist();
		return directionHome.getInstance();
	}
	
	protected Fare createFare(Agency agency, Route route, Zone origin, Zone destination, Zone contains) {
		FareHome fareHome = (FareHome) getInstance("fareHome");
		ValidationTableBean validationTableBean = (ValidationTableBean) getInstance("validationTableBean");
		PaymentMethod paymentMethod = validationTableBean.getPaymentMethods().get(0);
		TransferLimit transferLimit = validationTableBean.getTransferLimits().get(0);

		fareHome.getInstance().setAgency(agency);
		fareHome.getInstance().setCurrencyType(Constants.DEFAULT_CURRENCY_TYPE);
		fareHome.getInstance().setPrice(TestConstants.TEST_FARE_PRICE);
		fareHome.getInstance().setTransferDuration(TestConstants.TEST_FARE_TRANSFER_DURATION);
		fareHome.getInstance().setTransferLimit(transferLimit);
		fareHome.getInstance().setPaymentMethod(paymentMethod);

		fareHome.persist();
		fareHome.getEntityManager().refresh(agency);

		createFareRule(agency, fareHome.getInstance(), route, origin, destination, contains);

		return fareHome.getInstance();
	}

	protected FareRule createFareRule(Agency agency, Fare fare, Route route, Zone origin, Zone destination, Zone contains) {
		FareRuleHome fareRuleHome = (FareRuleHome) getInstance("fareRuleHome");
		fareRuleHome.getInstance().setAgency(agency);
		fareRuleHome.getInstance().setFare(fare);
		fareRuleHome.getInstance().setOrigin(origin);
		fareRuleHome.getInstance().setDestination(destination);
		fareRuleHome.getInstance().setContains(contains);
		fareRuleHome.getInstance().setRoute(route);

		fareRuleHome.persist();
		fareRuleHome.getEntityManager().refresh(agency);
		fareRuleHome.getEntityManager().refresh(fare);
		fareRuleHome.getEntityManager().refresh(route);
		fareRuleHome.getEntityManager().refresh(origin);
		fareRuleHome.getEntityManager().refresh(destination);
		fareRuleHome.getEntityManager().refresh(contains);

		return fareRuleHome.getInstance();
	}

	protected Station createStation(Agency agency, Zone zone, Direction direction, String name) {
		StationHome stationHome = (StationHome) getInstance("stationHome");

		stationHome.getInstance().setAgency(agency);
		stationHome.getInstance().setZone(zone);
		stationHome.getInstance().setDirection(direction);
		stationHome.getInstance().setStopName(name);
		stationHome.getInstance().setStopLat(TestConstants.TEST_LOCATION_LATITUDE);
		stationHome.getInstance().setStopLon(TestConstants.TEST_LOCATION_LONGITUDE);

		stationHome.persist();
		stationHome.getEntityManager().refresh(agency);
		stationHome.getEntityManager().refresh(zone);

		return stationHome.getInstance();
	}

	protected Stop createStop(Agency agency, Zone zone, Direction direction, Station parentStation,
			String name) {
		StopHome stopHome = (StopHome) getInstance("stopHome");

		stopHome.getInstance().setAgency(agency);
		stopHome.getInstance().setZone(zone);
		stopHome.getInstance().setParentStation(parentStation);
		stopHome.getInstance().setDirection(direction);
		stopHome.getInstance().setStopName(name);
		stopHome.getInstance().setStopLat(TestConstants.TEST_LOCATION_LATITUDE);
		stopHome.getInstance().setStopLon(TestConstants.TEST_LOCATION_LONGITUDE);

		stopHome.persist();
		stopHome.getEntityManager().refresh(agency);
		stopHome.getEntityManager().refresh(zone);
		stopHome.getEntityManager().refresh(parentStation);

		return stopHome.getInstance();
	}

	protected Transfer createTransfer(Agency agency, Location fromLocation, Location toLocation) {
		TransferHome transferHome = (TransferHome) getInstance("transfersHome");
		ValidationTableBean validationTableBean = (ValidationTableBean) getInstance("validationTableBean");

		TransferType transferType = validationTableBean.getTransferTypes().get(0);

		transferHome.getInstance().setAgency(agency);
		transferHome.getInstance().setTransferType(transferType);
		transferHome.getInstance().setToLocation(toLocation);
		transferHome.getInstance().setFromLocation(fromLocation);
		transferHome.getInstance().setMinTransferTime(TestConstants.TEST_TRANSFER_MIN_TIME);

		transferHome.persist();

		return transferHome.getInstance();
	}

	protected ServiceScheduleGroup createServiceScheduleGroup(Agency agency) {
		ServiceScheduleGroupHome serviceScheduleGroupHome = 
			(ServiceScheduleGroupHome) getInstance("serviceScheduleGroupHome");
		serviceScheduleGroupHome.getInstance().setAgency(agency);
		serviceScheduleGroupHome.getInstance().setCurrent(true);
		serviceScheduleGroupHome.getInstance().setServiceScheduleGroupLabel(TestConstants.TEST_SERVICE_SCHEDULE_GROUP_LABEL);

		serviceScheduleGroupHome.persist();

		return serviceScheduleGroupHome.getInstance();
	}

	protected ServiceScheduleBound createServiceScheduleBound(ServiceScheduleGroup serviceScheduleGroup) {
		ServiceScheduleBoundHome serviceScheduleBoundHome = 
			(ServiceScheduleBoundHome) getInstance("serviceScheduleBoundHome");
		Date now = new Date();
		Date startDate = TestUtilities.generatePastDate();
		Date endDate = TestUtilities.generateFutureDate();
		serviceScheduleBoundHome.getInstance().setServiceScheduleGroup(serviceScheduleGroup);
		serviceScheduleBoundHome.getInstance().setStartDate(startDate);
		serviceScheduleBoundHome.getInstance().setEndDate(endDate);
		
		serviceScheduleBoundHome.persist();
		serviceScheduleBoundHome.getEntityManager().refresh(serviceScheduleGroup);
		
		return serviceScheduleBoundHome.getInstance();
	}

	protected Calendar createCalendar(ServiceScheduleGroup serviceScheduleGroup) {
		CalendarHome calendarHome = (CalendarHome) getInstance("calendarHome");
		calendarHome.getInstance().setServiceScheduleGroup(serviceScheduleGroup);
		calendarHome.getInstance().setServiceLabel(TestConstants.TEST_CALENDAR_SERVICE_LABEL_1);
		calendarHome.getInstance().setMonday(true);
		calendarHome.getInstance().setTuesday(false);
		calendarHome.getInstance().setWednesday(true);
		calendarHome.getInstance().setThursday(false);
		calendarHome.getInstance().setFriday(true);
		calendarHome.getInstance().setSaturday(false);
		calendarHome.getInstance().setSunday(false);

		calendarHome.persist();

		return calendarHome.getInstance();
	}

	protected Trip createTrip(Agency agency, Route route, Calendar calendar, Block block, Direction direction, List<Stop> stops) {
		TripHome tripHome = (TripHome) getInstance("tripHome");
		ValidationTableBean validationTableBean = (ValidationTableBean) getInstance("validationTableBean");
		Date now = new Date();
		final long window = 300000;

		BikeOption bikeOption = validationTableBean.getBikeOptions().get(0);
		
		tripHome.getInstance().setAgency(agency);
		tripHome.getInstance().setRoute(route);
		tripHome.getInstance().setBikeOption(bikeOption);
		tripHome.getInstance().setBlock(block);
		tripHome.getInstance().setCalendar(calendar);
		tripHome.getInstance().setDirection(direction);
		tripHome.getInstance().setTripHeadsign(TestConstants.TEST_TRIP_HEADSIGN);

		tripHome.persist();
		tripHome.getEntityManager().refresh(agency);
		tripHome.getEntityManager().refresh(route);
		tripHome.getEntityManager().refresh(calendar);
		tripHome.getEntityManager().refresh(direction);
		tripHome.getEntityManager().refresh(block);

		tripHome.getInstance().getFrequencies().add(createFrequency(tripHome.getInstance()));

		Date departure = TestUtilities.generateDateBetween(now, new Date(now.getTime() + window));
		Date arrival = TestUtilities.generateDateBetween(departure, new Date(departure.getTime() + window));
		short sequence = 1;
		for (Stop stop : stops) {
			StopTime st = createStopTime(agency, tripHome.getInstance(), stop, sequence++, departure, arrival);
			departure = TestUtilities.generateDateBetween(st.getArrivalTime(), new Date(st.getArrivalTime().getTime() + window));
			arrival = TestUtilities.generateDateBetween(departure, new Date(departure.getTime() + window));
			tripHome.getInstance().getStopTimes().add(st);
		}

		return tripHome.getInstance();
	}

	protected Frequency createFrequency(Trip trip) {
		FrequencyHome frequencyHome = (FrequencyHome) getInstance("frequencyHome");
		frequencyHome.getInstance().setStartTime(TestUtilities.generatePastDate());
		frequencyHome.getInstance().setEndTime(TestUtilities.generateFutureDate());
		frequencyHome.getInstance().setExactTimes(TestConstants.TEST_FREQUENCY_EXACT_TIME);
		frequencyHome.getInstance().setHeadwaySecs(TestConstants.TEST_FREQUENCY_HEADWAY_SECS);
		frequencyHome.getInstance().setTrip(trip);

		frequencyHome.persist();

		return frequencyHome.getInstance();
	}

	protected StopTime createStopTime(Agency agency, Trip trip, Stop stop, short stopSequence, Date arrivalTime, Date departureTime) {
		StopTimeHome stopTimeHome = (StopTimeHome) getInstance("stopTimeHome");
		ValidationTableBean validationTableBean = (ValidationTableBean) getInstance("validationTableBean");
		PickupType pickupType = validationTableBean.getPickupTypes().get(0);

		stopTimeHome.getInstance().setAgency(agency);
		stopTimeHome.getInstance().setStop(stop);
		stopTimeHome.getInstance().setArrivalTime(arrivalTime);
		stopTimeHome.getInstance().setDepartureTime(departureTime);
		stopTimeHome.getInstance().setDropOffType(pickupType);
		stopTimeHome.getInstance().setPickupType(pickupType);
		stopTimeHome.getInstance().setStopSequence(stopSequence);
		stopTimeHome.getInstance().setTrip(trip);

		stopTimeHome.persist();
		stopTimeHome.getEntityManager().refresh(agency);
		stopTimeHome.getEntityManager().refresh(stop);
		stopTimeHome.getEntityManager().refresh(trip);
		return stopTimeHome.getInstance();
	}

	protected CalendarDate createCalendarDate(Agency agency, Calendar serviceAdded, Calendar serviceRemoved) {
		CalendarDateHome calendarDateHome = (CalendarDateHome) getInstance("calendarDateHome");
		ValidationTableBean validationTableBean = (ValidationTableBean) getInstance("validationTableBean");
		CalendarExceptionType exceptionType = validationTableBean.getCalendarExceptionTypes().get(0);

		calendarDateHome.getInstance().setAgency(agency);
		calendarDateHome.getInstance().setDate(new Date());
		calendarDateHome.getInstance().setDescription(TestConstants.TEST_CALENDAR_DATE_DESCRIPTION);
		calendarDateHome.getInstance().setServiceAdded(serviceAdded);
		calendarDateHome.getInstance().setServiceRemoved(serviceRemoved);
		calendarDateHome.getInstance().setExceptionType(exceptionType);

		calendarDateHome.persist();

		createCalendarDateException(serviceAdded, calendarDateHome.getInstance());

		return calendarDateHome.getInstance();
	}

	protected CalendarDateException createCalendarDateException(Calendar service, CalendarDate calendarDate) {
		CalendarDateExceptionHome calendarDateExceptionHome =
			(CalendarDateExceptionHome) getInstance("calendarDateExceptionHome");
		ValidationTableBean validationTableBean = (ValidationTableBean) getInstance("validationTableBean");
		CalendarExceptionType exceptionType = validationTableBean.getCalendarExceptionTypes().get(0);

		calendarDateExceptionHome.getInstance().setCalendarDate(calendarDate);
		calendarDateExceptionHome.getInstance().setExceptionType(exceptionType);
		calendarDateExceptionHome.getInstance().setServiceException(service);

		calendarDateExceptionHome.persist();
		calendarDateExceptionHome.getEntityManager().refresh(calendarDate);
		
		return calendarDateExceptionHome.getInstance();
	}
}
