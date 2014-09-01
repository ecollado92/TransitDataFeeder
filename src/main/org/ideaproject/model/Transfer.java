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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ideaproject.Exportable;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "transfer")
public class Transfer extends AbstractAgencyBasedEntity implements java.io.Serializable, Exportable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transfer_id", unique = true, nullable = false)
	private int transferId;

	@Column(name = "min_transfer_time")
	private Integer minTransferTime;

	@ManyToOne
	@JoinColumn(name = "from_stop_id")
	private Location fromLocation;

	@ManyToOne
	@JoinColumn(name = "to_stop_id")
	private Location toLocation;

	@ManyToOne
	@JoinColumn(name = "transfer_type")
	private TransferType transferType;

	public Transfer() {
	}

	public Transfer(int transferId, Location fromLocation, Location toLocation,
			TransferType transferType, Agency agency) {
		this.transferId = transferId;
		this.fromLocation = fromLocation;
		this.toLocation = toLocation;
		this.transferType = transferType;
		setAgency(agency);
	}
	public Transfer(int transferId, Location fromLocation, Location toLocation,
			TransferType transferType, Integer minTransferTime, Agency agency) {
		this.transferId = transferId;
		this.fromLocation = fromLocation;
		this.toLocation = toLocation;
		this.transferType = transferType;
		this.minTransferTime = minTransferTime;
		setAgency(agency);
	}

	public int getTransferId() {
		return this.transferId;
	}

	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}

	public Location getFromLocation() {
		return this.fromLocation;
	}

	public void setFromLocation(Location fromLocation) {
		this.fromLocation = fromLocation;
	}

	public Location getToLocation() {
		return this.toLocation;
	}

	public void setToLocation(Location toLocation) {
		this.toLocation = toLocation;
	}

	public TransferType getTransferType() {
		return this.transferType;
	}

	public void setTransferType(TransferType transferType) {
		this.transferType = transferType;
		if (!this.transferType.isTimed()) {
			this.minTransferTime = null;
		}
	}

	public Integer getMinTransferTime() {
		return this.minTransferTime;
	}

	public void setMinTransferTime(Integer minTransferTime) {
		this.minTransferTime = minTransferTime;
	}

}
