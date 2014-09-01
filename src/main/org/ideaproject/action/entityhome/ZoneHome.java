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
package org.ideaproject.action.entityhome;

import org.ideaproject.model.FareRule;
import org.ideaproject.model.Station;
import org.ideaproject.model.Stop;
import org.ideaproject.model.Zone;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Seam EntityHome for Zones
 * @author dirk
 * @see org.ideaproject.action.entityhome.AbstractAgencyBasedEntityHome
 */
@Name("zoneHome")
public class ZoneHome extends AbstractAgencyBasedEntityHome<Zone> {

	private static final long serialVersionUID = 1L;

	@In(create = true)
	private FareRuleHome fareRuleHome;

	@In(create = true)
	private StopHome stopHome;

	@In(create = true)
	private StationHome stationHome;

	/**
	 * Sets the current zone by ID; side effect updates the
	 * instance.
	 * @param id the bike option ID
	 */
	public void setZoneZoneId(Integer id) {
		setId(id);
	}

	/**
	 * @return the ID of the current zone.
	 */
	public Integer getZoneZoneId() {
		return (Integer) getId();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Zone createInstance() {
		Zone zone = new Zone();
		return zone;
	}

	/**
	 * Loads the current zone.
	 */
	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	/**
	 * @return whether or not the current zone is "wired"
	 * @see #wire()
	 */
	public boolean isWired() {
		return true;
	}

	/**
	 * Gets the currently defined instance; may return null if the ID for this
	 * EntityHome is not supplied.
	 * @return the currently-defined instance, or null if none is defined.
	 */
	public Zone getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.EntityHome#remove()
	 */
	@Override
	public String remove() {
		Integer originalStopId = stopHome.getStopStopId();
		Integer originalStationId = stationHome.getStopStopId();
		Integer originalFareRuleId = fareRuleHome.getFareRuleFareRuleId();
		try {
			for (FareRule fareRule : getInstance().getOriginFareRules()) {
				fareRuleHome.setInstance(fareRule);
				fareRuleHome.getInstance().setOrigin(null);
				fareRuleHome.update();
			}
			for (FareRule fareRule : getInstance().getDestinationFareRules()) {
				fareRuleHome.setInstance(fareRule);
				fareRuleHome.getInstance().setDestination(null);
				fareRuleHome.update();
			}
			for (FareRule fareRule : getInstance().getContainsFareRules()) {
				fareRuleHome.setInstance(fareRule);
				fareRuleHome.getInstance().setContains(null);
				fareRuleHome.update();
			}
			for (Station station : getInstance().getStations()) {
				stationHome.setInstance(station);
				stationHome.remove();
			}
			// Need to refresh since the station deletes can cascade to the stops, and
			// we don't want to try to remove detached entities
			getEntityManager().refresh(getInstance());
			for (Stop stop : getInstance().getStops()) {
				stopHome.setInstance(stop);
				stopHome.remove();
			}
		} finally {
			fareRuleHome.setFareRuleFareRuleId(originalFareRuleId);
			stationHome.setStopStopId(originalStationId);
			stopHome.setStopStopId(originalStopId);
		}
		return super.remove();
	}
}
