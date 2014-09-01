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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


/**
 * Subclass of {@link org.ideaproject.model.BaseLocation} (and therefore {@link org.ideaproject.model.Location})
 * which specifies stations.  This subclass adds a collection of {@link org.ideaproject.model.Stop}s for which this
 * station is the parent station to the set of fields defined in superclasses.
 * 
 * @author dirk
 * @see org.ideaproject.model.BaseLocation
 * @see org.ideaproject.model.Location
 * @see org.ideaproject.model.Stop
 * 
 */@Entity
@DiscriminatorValue("1")
public class Station extends BaseLocation {

		private static final long serialVersionUID = 1L;

	 @OneToMany(fetch = FetchType.LAZY, mappedBy="parentStation", cascade={CascadeType.REFRESH, CascadeType.REMOVE})
	private List<Stop> childStops = new ArrayList<Stop>();

	public Station() {
		super();
	}

	public Station(int stopId, String stopName, String stopDesc,
			Zone zone, Direction direction) {
		super(stopId, stopName, stopDesc, zone, direction);
	}

	public Station(int stopId, String stopName,
			String stopCode, String stopDesc, String stopComments,
			Double stopLat, Double stopLon, Zone zone, Direction direction,
			String stopUrl) {
		super(stopId, stopName, stopDesc, stopCode, stopComments, stopLat, stopLon, stopUrl, zone, direction);
	}

	/**
	 * @return the childStops
	 */
	public List<Stop> getChildStops() {
		return childStops;
	}

	/**
	 * @param childStops the childStops to set
	 */
	public void setChildStops(List<Stop> childStops) {
		this.childStops = childStops;
	}
}
