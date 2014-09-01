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

import org.ideaproject.model.Transfer;
import org.jboss.seam.annotations.Name;

/**
 * Seam EntityHome for Transfers
 * @author dirk
 * @see org.ideaproject.action.entityhome.AbstractAgencyBasedEntityHome
 */
@Name("transfersHome")
public class TransferHome extends AbstractAgencyBasedEntityHome<Transfer> {

	private static final long serialVersionUID = 1L;

	/**
	 * Sets the current transfer by ID; side effect updates the
	 * instance.
	 * @param id the bike option ID
	 */
	public void setTransfersTransferId(Integer id) {
		setId(id);
	}

	/**
	 * @return the ID of the current transfer.
	 */
	public Integer getTransfersTransferId() {
		return (Integer) getId();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.seam.framework.Home#createInstance()
	 */
	@Override
	protected Transfer createInstance() {
		Transfer transfer = new Transfer();
		return transfer;
	}

	/**
	 * Loads the current transfer.
	 */
	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	/**
	 * @return whether or not the current transfer is "wired"
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
	public Transfer getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
