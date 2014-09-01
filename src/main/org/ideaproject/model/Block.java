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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "block")
public class Block extends AbstractAgencyBasedEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "block_id", unique = true, nullable = false)
	private int blockId;

	@Column(name = "block_label", nullable = false, length = 50)
	@NotNull
	@Length(max = 50)
	private String blockLabel;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "block")
	private List<Trip> trips;

	public Block() {
	}

	public Block(int blockId, String blockLabel, Agency agency) {
		this.blockId = blockId;
		this.blockLabel = blockLabel;
		setAgency(agency);
	}

	public int getBlockId() {
		return this.blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	public String getBlockLabel() {
		return this.blockLabel;
	}

	public void setBlockLabel(String blockLabel) {
		this.blockLabel = blockLabel;
	}

	/**
	 * @return the trips
	 */
	public List<Trip> getTrips() {
		return trips;
	}

	/**
	 * @param trips the trips to set
	 */
	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}

	@Transient
	public int getNumberOfTrips() {
		return getTrips() != null ? getTrips().size() : 0;
	}

	public void setNumberOfTrips(int numberOfTrips) {
		/* No-op; number of trips is a calculated field, so setting it makes no sense.  Included for JSF */
	}
}
