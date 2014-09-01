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
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * @author dirk
 *
 */
@Entity
@Table(name = "address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id", nullable = false, unique = true)
	private int addressId;

	@Column(name = "street_line_1", nullable = false, length = 64)
	@NotNull
	@Length(max = 64)
	private String streetLine1;

	@Column(name = "street_line_2", length = 64)
	@Length(max = 64)
	private String streetLine2;

	@Column(name = "city", nullable = false, length = 64)
	@NotNull
	@Length(max = 64)
	private String city;

	@Column(name = "state_province_code", nullable = false, length = 3)
	@NotNull
	@Length(max = 3)
	private String stateCode;

	@Column(name = "postal_code", nullable = false, length = 10)
	@Length(max = 10)
	private String postalCode;

	@Column(name = "country", length = 64)
	@Length(max = 64)
	private String country;

	/**
	 * @return the addressId
	 */
	public int getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return the streetLine1
	 */
	public String getStreetLine1() {
		return streetLine1;
	}

	/**
	 * @param streetLine1 the streetLine1 to set
	 */
	public void setStreetLine1(String streetLine1) {
		this.streetLine1 = streetLine1;
	}

	/**
	 * @return the streetLine2
	 */
	public String getStreetLine2() {
		return streetLine2;
	}

	/**
	 * @param streetLine2 the streetLine2 to set
	 */
	public void setStreetLine2(String streetLine2) {
		this.streetLine2 = streetLine2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

}
