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
@Table(name = "bike_option")
public class BikeOption implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "bike_option_id", unique = true, nullable = false)
	private int bikeOptionId;

	@Column(name = "bike_option_description", nullable = false, length = 64)
	@NotNull
	@Length(max = 64)
	private String bikeOptionDescription;

	/**
	 * @return the bikeOptionId
	 */
	public int getBikeOptionId() {
		return bikeOptionId;
	}

	/**
	 * @param bikeOptionId the bikeOptionId to set
	 */
	public void setBikeOptionId(int bikeOptionId) {
		this.bikeOptionId = bikeOptionId;
	}

	/**
	 * @return the bikeOptionDescription
	 */
	public String getBikeOptionDescription() {
		return bikeOptionDescription;
	}

	/**
	 * @param bikeOptionDescription the bikeOptionDescription to set
	 */
	public void setBikeOptionDescription(String bikeOptionDescription) {
		this.bikeOptionDescription = bikeOptionDescription;
	}

}
