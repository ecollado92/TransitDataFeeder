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
package org.ideaproject.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.ideaproject.QueryNames;

/**
 * Subclass of {@link org.ideaproject.model.BaseLocation} (and therefore {@link org.ideaproject.model.Location})
 * which specifies stops.  This subclass adds a collection of transfers from/to the stop, the parent station,
 * a collection of stop times, and a stop list order index to the existing set of fields.
 * 
 * @author dirk
 * @see org.ideaproject.model.BaseLocation
 * @see org.ideaproject.model.Location
 * 
 */
@Entity
@DiscriminatorValue("0")
@NamedQueries({
	@NamedQuery(name = QueryNames.COUNT_AGENCY_STOPS_ALL,
			query = "SELECT COUNT(*) FROM Stop stop WHERE stop.agency = ?1"),
	@NamedQuery(name = QueryNames.COUNT_AGENCY_STOPS_NO_LOCATION,
			query = "SELECT COUNT(*) FROM Stop stop WHERE stop.agency = ?1 "+
					"AND (stop.stopLat IS NULL OR stop.stopLon IS NULL)"),
	@NamedQuery(name = QueryNames.COUNT_AGENCY_STOPS_UNSCHEDULED,
			query = "SELECT COUNT(*) FROM Stop stop WHERE stop.agency = ?1 " +
					"AND NOT EXISTS (SELECT 1 FROM stop.stopTimes st INNER JOIN st.trip t WHERE t.calendar IS NOT NULL)")
})
public class Stop extends BaseLocation {

	private static final long serialVersionUID = 1L;

	@Column(name = "stop_list_order")
	private int stopListOrder = 0;

	@ManyToOne
	@JoinColumn(name = "parent_station")
	private Station parentStation;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="stop", cascade = CascadeType.ALL)
	@OrderBy("arrivalTime")
	private List<StopTime> stopTimes;

	@org.hibernate.annotations.Formula("exists (select 1 from stop_time st inner join trip t on t.trip_id = st.trip_id inner join calendar c on c.calendar_id = t.service_id where st.stop_id = stop_id)")
	private boolean inSchedules;

	public Stop() {
		super();
	}

	public Stop(int stopId, String stopName, String stopDesc, 
			Zone zone, Direction direction, int stopListOrder) {
		super(stopId, stopName, stopDesc, zone, direction);
		this.stopListOrder = stopListOrder;
	}

	public Stop(int stopId, String stopName, String stopDesc, 
			String stopCode, String stopComments,
			Double stopLat, Double stopLon, String stopUrl, Zone zone, Direction direction,
			Station parentStation, int stopListOrder) {
		super(stopId, stopName, stopDesc, stopCode, stopComments, stopLat, stopLon, stopUrl, zone, direction);
		this.parentStation = parentStation;
		this.stopListOrder = stopListOrder;
	}

	public Station getParentStation() {
		return this.parentStation;
	}

	public void setParentStation(Station parentStation) {
		this.parentStation = parentStation;
	}

	public int getStopListOrder() {
		return this.stopListOrder;
	}

	public void setStopListOrder(int stopListOrder) {
		this.stopListOrder = stopListOrder;
	}

	/**
	 * @return the stopTimes
	 */
	public List<StopTime> getStopTimes() {
		return stopTimes;
	}

	/**
	 * @param stopTimes the stopTimes to set
	 */
	public void setStopTimes(List<StopTime> stopTimes) {
		this.stopTimes = stopTimes;
	}

	public boolean isInSchedules() {
		return inSchedules;
	}

	public void setInSchedules(boolean inSchedules) {
		this.inSchedules = inSchedules;
	}

}
