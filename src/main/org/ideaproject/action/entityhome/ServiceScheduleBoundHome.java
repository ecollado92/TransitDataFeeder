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

import org.ideaproject.model.ServiceScheduleBound;
import org.ideaproject.model.ServiceScheduleGroup;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Seam EntityHome for ServiceScheduleBounds
 * @author dirk
 * @see org.ideaproject.action.entityhome.AbstractLastModifiedEntityHome
 */
@Name("serviceScheduleBoundHome")
public class ServiceScheduleBoundHome extends AbstractLastModifiedEntityHome<ServiceScheduleBound> {

	private static final long serialVersionUID = 1L;

	/**
	 * The entity home from which we can determine the current instance's
	 * service schedule group association.
	 */
	@In(create = true)
	ServiceScheduleGroupHome serviceScheduleGroupHome;

	/**
	 * Sets the current service schedule bound by ID; side effect updates the
	 * instance.
	 * @param id the bike option ID
	 */
	public void setServiceScheduleBoundServiceScheduleBoundsId(Integer id) {
		setId(id);
	}

	/**
	 * @return the ID of the current service schedule bound.
	 */
	public Integer getServiceScheduleBoundServiceScheduleBoundsId() {
		return (Integer) getId();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected ServiceScheduleBound createInstance() {
		ServiceScheduleBound serviceScheduleBound = new ServiceScheduleBound();
		return serviceScheduleBound;
	}

	/**
	 * Loads the current service schedule bound.
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
		ServiceScheduleGroup serviceScheduleGroup = serviceScheduleGroupHome
				.getDefinedInstance();
		if (serviceScheduleGroup != null) {
			getInstance().setServiceScheduleGroup(serviceScheduleGroup);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.ideaproject.action.entityhome.AbstractLastModifiedEntityHome#persist()
	 */
	@Override
	public String persist() {
		getInstance().getServiceScheduleGroup().getServiceScheduleBounds().add(getInstance());
		return super.persist();
	}

	/**
	 * @return whether or not the current service schedule bound is "wired"
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
	public ServiceScheduleBound getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
