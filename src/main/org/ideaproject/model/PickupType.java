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
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * @author dirk
 *
 */
@Entity
@Table(name = "pickup_type")
public class PickupType {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "pickup_type_id", unique = true, nullable = false)
	private int pickupTypeId;

	@Column(name = "pickup_type_description", nullable = false, length = 64)
	@NotNull
	@Length(max = 64)
	private String pickupTypeDescription;

	public PickupType() {
		/* Default Ctor */
	}

	public PickupType(String pickupTypeDescription) {
		this.pickupTypeDescription = pickupTypeDescription;
	}

	/**
	 * @return the pickupTypeId
	 */
	public int getPickupTypeId() {
		return pickupTypeId;
	}

	/**
	 * @param pickupTypeId the pickupTypeId to set
	 */
	public void setPickupTypeId(int pickupTypeId) {
		this.pickupTypeId = pickupTypeId;
	}

	/**
	 * @return the pickupTypeDescription
	 */
	public String getPickupTypeDescription() {
		return pickupTypeDescription;
	}

	/**
	 * @param pickupTypeDescription the pickupTypeDescription to set
	 */
	public void setPickupTypeDescription(String pickupTypeDescription) {
		this.pickupTypeDescription = pickupTypeDescription;
	}


}
