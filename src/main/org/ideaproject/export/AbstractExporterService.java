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
package org.ideaproject.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ideaproject.Constants;
import org.ideaproject.Exportable;
import org.ideaproject.model.Agency;
import org.ideaproject.model.AgencyGroup;
import org.ideaproject.model.Calendar;
import org.ideaproject.model.CalendarDate;
import org.ideaproject.model.Fare;
import org.ideaproject.model.FareRule;
import org.ideaproject.model.Frequency;
import org.ideaproject.model.Route;
import org.ideaproject.model.ServiceScheduleGroup;
import org.ideaproject.model.Station;
import org.ideaproject.model.Stop;
import org.ideaproject.model.StopTime;
import org.ideaproject.model.Transfer;
import org.ideaproject.model.Trip;
import org.ideaproject.util.TripUtil;

/**
 * @author dirk
 *
 */
public abstract class AbstractExporterService {
	private final Map<Class<?>, GenericExporter<?>> EXPORTER_MAP = new HashMap<Class<?>, GenericExporter<?>>();
	private final static Object EXPORT_LOCK = new Object();

	private List<GenericExporter<? extends Exportable>> exporters;

	protected OutputStream agencyOutput;
	protected OutputStream calendarOutput;
	protected OutputStream calendarDateOutput;
	protected OutputStream fareOutput;
	protected OutputStream fareRuleOutput;
	protected OutputStream stopOutput;
	protected OutputStream routeOutput;
	protected OutputStream tripOutput;
	protected OutputStream stopTimeOutput;
	protected OutputStream frequencyOutput;
	protected OutputStream transferOutput;

	public void doExport(OutputStream outStream, Agency agencyToExport) throws IOException {
		synchronized(EXPORT_LOCK) {
			try {
				initializeOutputStreams(outStream);

				List<AgencyGroup> agencyGroups = agencyToExport.getAgencyGroups();
				if (agencyGroups == null || agencyGroups.isEmpty()) {
					GenericExporter<Agency> exporter = getExporter(Agency.class);
					exporter.export(agencyToExport, agencyOutput);
					exportCalendarsForAgency(agencyToExport);
					exportCalendarDatesForAgency(agencyToExport);
					exportFaresForAgency(agencyToExport);
					exportStationsForAgency(agencyToExport);
					exportStopsForAgency(agencyToExport);
					exportTransfersForAgency(agencyToExport);
					exportRoutesForAgency(agencyToExport);
				} else {
					for (AgencyGroup agencyGroup : agencyGroups) {
						for (Agency agency : agencyGroup.getAgencies()) {
							GenericExporter<Agency> exporter = getExporter(Agency.class);
							exporter.export(agency, agencyOutput);
							exportCalendarsForAgency(agency);
							exportCalendarDatesForAgency(agency);
							exportFaresForAgency(agency);
							exportStationsForAgency(agency);
							exportStopsForAgency(agency);
							exportTransfersForAgency(agency);
							exportRoutesForAgency(agency);
						}
					}
				}
			} finally {
				closeOutputStreams();
			}
		}
	}

	void exportCalendarsForAgency(Agency agency) throws IOException {
		for (ServiceScheduleGroup group : agency.getScheduleGroups()) {
			exportList(Calendar.class, group.getCalendars(), calendarOutput);
		}
	}

	void exportCalendarDatesForAgency(Agency agency) throws IOException {
		exportList(CalendarDate.class, agency.getCalendarDates(), calendarDateOutput);
	}

	void exportStationsForAgency(Agency agency) throws IOException {
		exportList(Station.class, agency.getStations(), stopOutput);
	}

	void exportStopsForAgency(Agency agency) throws IOException {
		exportList(Stop.class, agency.getStops(), stopOutput);
	}

	void exportRoutesForAgency(Agency agency) throws IOException {
		for (Route route : agency.getRoutes()) {
			exportItem(Route.class, route, routeOutput);
			exportTripsForRoute(route);
		}
	}

	void exportTransfersForAgency(Agency agency) throws IOException {
		exportList(Transfer.class, agency.getTransfers(), transferOutput);
	}

	void exportFaresForAgency(Agency agency) throws IOException {
		for (Fare fare : agency.getFares()) {
			exportItem(Fare.class, fare, fareOutput);
			exportList(FareRule.class, fare.getFareRules(), fareRuleOutput);
		}
	}

	void exportTripsForRoute(Route route) throws IOException {
		for (Trip trip : route.getTrips()) {
			exportItem(Trip.class, trip, tripOutput);
			exportStopTimesForTrip(trip);
			exportFrequenciesForTrip(trip);
		}
	}

	void exportStopTimesForTrip(Trip trip) throws IOException {
		if (trip.getBasedOn() != null) {
			exportList(StopTime.class, TripUtil.buildInheritedStopTimeList(trip), stopTimeOutput);
		} else {
			exportList(StopTime.class, trip.getStopTimes(), stopTimeOutput);
		}
	}

	void exportFrequenciesForTrip(Trip trip) throws IOException {
		exportList(Frequency.class, trip.getFrequencies(), frequencyOutput);
	}

	protected abstract Constants.ExportType getExportType();

	protected abstract void initializeOutputStreams(OutputStream destination) throws IOException;

	protected abstract void closeOutputStreams() throws IOException;

	
	private <T extends Exportable> void exportItem(Class<T> clazz, T exportedItem, OutputStream os)
	throws IOException {
		GenericExporter<T> exporter = getExporter(clazz);
		if (exporter != null) {
			exporter.export(exportedItem, os);
		}
	}

	private <T extends Exportable> void exportList(Class<T> clazz, List<T> exportedItems, OutputStream os)
	throws IOException {
		GenericExporter<T> exporter = getExporter(clazz);
		if (exporter != null) {
			for (T exportedItem : exportedItems) {
				exporter.export(exportedItem, os);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends Exportable> GenericExporter<T> getExporter(Class<T> exportable) {
		GenericExporter<T> result = (GenericExporter<T>) EXPORTER_MAP.get(exportable);
		if (result == null) {
			for (GenericExporter<?> exporter : exporters) {
				if (exporter.accept(exportable, getExportType())) {
					EXPORTER_MAP.put(exportable, exporter);
					result = (GenericExporter<T>) exporter;
					break;
				}
			}
		}
		return result;
	}

	public void setExporters(List<GenericExporter<? extends Exportable>> exporters) {
		this.exporters = exporters;
	}
}
