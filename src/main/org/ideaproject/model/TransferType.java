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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * @author dirk
 *
 */
@Entity
@Table(name = "transfer_type")
public class TransferType {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "transfer_type_id", unique = true, nullable = false)
	private int transferTypeId;
	
	@Column(name = "transfer_type_description", nullable = false, length = 128)
	@NotNull
	@Length(max = 128)
	private String transferTypeDescription; 

	@Column(name = "is_timed", nullable = false)
	private boolean timed;

	public TransferType() {
		/* default ctor for use by framework */
	}

	public TransferType(String transferTypeDescription) {
		this.transferTypeDescription = transferTypeDescription;
	}

	public TransferType(int transferTypeId, String transferTypeDescription) {
		this.transferTypeId = transferTypeId;
		this.transferTypeDescription = transferTypeDescription;
	}

	/**
	 * @return the transferTypeId
	 */
	public int getTransferTypeId() {
		return transferTypeId;
	}

	/**
	 * @param transferTypeId the transferTypeId to set
	 */
	public void setTransferTypeId(int transferTypeId) {
		this.transferTypeId = transferTypeId;
	}

	/**
	 * @return the transferTypeDescription
	 */
	public String getTransferTypeDescription() {
		return transferTypeDescription;
	}

	/**
	 * @param transferTypeDescription the transferTypeDescription to set
	 */
	public void setTransferTypeDescription(String transferTypeDescription) {
		this.transferTypeDescription = transferTypeDescription;
	}

	/**
	 * @return the timed
	 */
	public boolean isTimed() {
		return timed;
	}

	/**
	 * @param timed the timed to set
	 */
	public void setTimed(boolean timed) {
		this.timed = timed;
	}

}
