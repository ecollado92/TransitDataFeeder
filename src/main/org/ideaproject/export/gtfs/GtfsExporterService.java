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
package org.ideaproject.export.gtfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.ideaproject.Constants.ExportType;
import org.ideaproject.export.AbstractExporterService;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;

/**
 * @author dirk
 *
 */
public class GtfsExporterService extends AbstractExporterService {
	private static final String AGENCY_FILE_NAME = "agency.txt";
	private static final String CALENDAR_FILE_NAME = "calendar.txt";
	private static final String CALENDAR_DATE_FILE_NAME = "calendar_dates.txt";
	private static final String FARE_FILE_NAME = "fare_attributes.txt";
	private static final String FARE_RULE_FILE_NAME = "fare_rules.txt";
	private static final String STOP_FILE_NAME = "stops.txt";
	private static final String ROUTE_FILE_NAME = "routes.txt";
	private static final String TRANSFER_FILE_NAME = "transfers.txt";
	private static final String TRIP_FILE_NAME = "trips.txt";
	private static final String STOP_TIME_FILE_NAME = "stop_times.txt";
	private static final String FREQUENCY_FILE_NAME = "frequencies.txt";
	private static final String SHAPE_FILE_NAME = "shapes.txt";

//	private static final String AGENCY_HEADER = "agency_id,agency_name,agency_url,agency_timezone,agency_phone,agency_fare_url\n";
	private static final String AGENCY_HEADER = "agency_id,agency_name,agency_url,agency_timezone,agency_lang,agency_phone\n";
	private static final String CALENDAR_HEADER = "service_id,monday,tuesday,wednesday,thursday,friday,saturday,sunday,start_date,end_date\n";
	private static final String CALENDAR_DATE_HEADER = "service_id,date,exception_type\n";
	private static final String FARE_HEADER = "fare_id,price,currency_type,payment_method,transfers,transfer_duration\n";
	private static final String FARE_RULE_HEADER = "fare_id,route_id,origin_id,destination_id,contains_id\n";
	private static final String STOP_HEADER = "stop_id,stop_name,stop_desc,stop_lat,stop_lon,zone_id,stop_url,location_type,parent_station\n";
	private static final String ROUTE_HEADER = "agency_id,route_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color\n";
	private static final String TRANSFER_HEADER = "from_stop_id,to_stop_id,transfer_type,min_transfer_time\n";
	private static final String TRIP_HEADER = "route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id\n";
	private static final String STOP_TIME_HEADER = "trip_id,arrival_time,departure_time,stop_id,stop_sequence,pickup_type,drop_off_type,shape_dist_traveled\n";
	private static final String FREQUENCY_HEADER = "trip_id,start_time,end_time,headway_secs\n";
	private static final String SHAPE_HEADER = "shape_id,shape_pt_lat,shape_pt_lon,shape_pt_sequence,shape_dist_traveled\n";

	@Logger
	private Log logger;

	private File agencyOutputFile;
	private File calendarOutputFile;
	private File calendarDateOutputFile;
	private File fareOutputFile;
	private File fareRuleOutputFile;
	private File stopOutputFile;
	private File routeOutputFile;
	private File transferOutputFile;
	private File tripOutputFile;
	private File stopTimeOutputFile;
	private File frequencyOutputFile;
	
	private OutputStream destination;

	/* (non-Javadoc)
	 * @see org.ideaproject.action.export.AbstractExporterService#getExportType()
	 */
	protected ExportType getExportType() {
		return ExportType.GTFS;
	}

	/* (non-Javadoc)
	 * @see org.ideaproject.action.export.AbstractExporterService#closeOutputStreams()
	 */
	protected void closeOutputStreams() throws IOException {
		frequencyOutput.close();
		stopTimeOutput.close();
		tripOutput.close();
		transferOutput.close();
		routeOutput.close();
		stopOutput.close();
		fareRuleOutput.close();
		fareOutput.close();
		calendarDateOutput.close();
		calendarOutput.close();
		agencyOutput.close();

		byte[] buffer = new byte[1024];
		ZipOutputStream zipOut = new ZipOutputStream(destination);
		Map<String, FileInputStream> inputFiles = buildFileInputStreamMap();
		for (Map.Entry<String, FileInputStream> inputFile : inputFiles.entrySet()) {
			FileInputStream in = inputFile.getValue();
			try {
				zipOut.putNextEntry(new ZipEntry(inputFile.getKey()));
				int len;
				while((len = in.read(buffer)) > 0) {
					zipOut.write(buffer, 0, len);
				}
				zipOut.closeEntry();
			} finally {
				inputFile.getValue().close();
			}
		}
		zipOut.close();
	}

	/* (non-Javadoc)
	 * @see org.ideaproject.action.export.AbstractExporterService#initializeOutputStreams()
	 */
	protected void initializeOutputStreams(OutputStream destination) throws IOException {
		this.destination = destination;

		agencyOutputFile = File.createTempFile("agency", ".txt");
		calendarOutputFile = File.createTempFile("calendar", ".txt");
		calendarDateOutputFile = File.createTempFile("calendar_dates", ".txt");
		fareOutputFile = File.createTempFile("fare_attributes", ".txt");
		fareRuleOutputFile = File.createTempFile("fare_rules", ".txt");
		stopOutputFile = File.createTempFile("stops", ".txt");
		routeOutputFile = File.createTempFile("routes", ".txt");
		transferOutputFile = File.createTempFile("transfers", ".txt");
		tripOutputFile = File.createTempFile("trips", ".txt");
		stopTimeOutputFile = File.createTempFile("stop_times", ".txt");
		frequencyOutputFile = File.createTempFile("frequencies", ".txt");

		agencyOutput = new FileOutputStream(agencyOutputFile);
		agencyOutput.write(AGENCY_HEADER.getBytes());

		calendarOutput = new FileOutputStream(calendarOutputFile);
		calendarOutput.write(CALENDAR_HEADER.getBytes());

		calendarDateOutput = new FileOutputStream(calendarDateOutputFile);
		calendarDateOutput.write(CALENDAR_DATE_HEADER.getBytes());

		fareOutput = new FileOutputStream(fareOutputFile);
		fareOutput.write(FARE_HEADER.getBytes());

		fareRuleOutput = new FileOutputStream(fareRuleOutputFile);
		fareRuleOutput.write(FARE_RULE_HEADER.getBytes());

		stopOutput = new FileOutputStream(stopOutputFile);
		stopOutput.write(STOP_HEADER.getBytes());

		routeOutput = new FileOutputStream(routeOutputFile);
		routeOutput.write(ROUTE_HEADER.getBytes());

		transferOutput = new FileOutputStream(transferOutputFile);
		transferOutput.write(TRANSFER_HEADER.getBytes());

		tripOutput = new FileOutputStream(tripOutputFile);
		tripOutput.write(TRIP_HEADER.getBytes());

		stopTimeOutput = new FileOutputStream(stopTimeOutputFile);
		stopTimeOutput.write(STOP_TIME_HEADER.getBytes());

		frequencyOutput = new FileOutputStream(frequencyOutputFile);
		frequencyOutput.write(FREQUENCY_HEADER.getBytes());
	}

	private Map<String, FileInputStream> buildFileInputStreamMap() throws IOException {
		Map<String, FileInputStream> result = new HashMap<String, FileInputStream>();
		result.put(AGENCY_FILE_NAME, new FileInputStream(agencyOutputFile));
		result.put(CALENDAR_FILE_NAME, new FileInputStream(calendarOutputFile));
		result.put(CALENDAR_DATE_FILE_NAME, new FileInputStream(calendarDateOutputFile));
		result.put(FARE_FILE_NAME, new FileInputStream(fareOutputFile));
		result.put(FARE_RULE_FILE_NAME, new FileInputStream(fareRuleOutputFile));
		result.put(STOP_FILE_NAME, new FileInputStream(stopOutputFile));
		result.put(ROUTE_FILE_NAME, new FileInputStream(routeOutputFile));
		result.put(TRANSFER_FILE_NAME, new FileInputStream(transferOutputFile));
		result.put(TRIP_FILE_NAME, new FileInputStream(tripOutputFile));
		result.put(STOP_TIME_FILE_NAME, new FileInputStream(stopTimeOutputFile));
		result.put(FREQUENCY_FILE_NAME, new FileInputStream(frequencyOutputFile));
		return result;
	}
}
