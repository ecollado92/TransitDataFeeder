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

import org.ideaproject.model.Frequency;
import org.ideaproject.model.Trip;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Seam EntityHome for Frequencys
 * @author dirk
 * @see org.ideaproject.action.entityhome.AbstractLastModifiedEntityHome
 */
@Name("frequencyHome")
public class FrequencyHome extends AbstractLastModifiedEntityHome<Frequency> {

	private static final long serialVersionUID = 1L;

	/**
	 * The entity home from which we can determine the current instance's
	 * trip association.
	 */
	@In(create = true)
	private TripHome tripHome;

	/**
	 * Sets the current frequency by ID; side effect updates the
	 * instance.
	 * @param id the bike option ID
	 */
	public void setFrequencyFrequencyId(Integer id) {
		setId(id);
	}

	/**
	 * @return the ID of the current frequency.
	 */
	public Integer getFrequencyFrequencyId() {
		return (Integer) getId();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Frequency createInstance() {
		Frequency frequency = new Frequency();
		return frequency;
	}

	/**
	 * Loads the current frequency.
	 */
	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	/**
	 * "Wires" the current instance
	 */
	public void wire() {
		getInstance();
		Trip trip = tripHome.getDefinedInstance();
		if (trip != null) {
			getInstance().setTrip(trip);
		}
	}

	/**
	 * @return whether or not the current frequency is "wired"
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
	public Frequency getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
