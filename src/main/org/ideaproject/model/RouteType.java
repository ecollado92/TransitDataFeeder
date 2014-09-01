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
@Table(name = "route_type")
public class RouteType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "route_type_id", unique = true, nullable = false)
	private int routeTypeId;
	
	@Column(name = "route_type_description", nullable = false, length = 24)
	@NotNull
	@Length(max = 24)
	private String routeTypeDescription; 

	public RouteType() {
		/* default ctor for use by framework */
	}

	public RouteType(String routeTypeDescription) {
		this.routeTypeDescription = routeTypeDescription;
	}

	public RouteType(int routeTypeId, String routeTypeDescription) {
		this.routeTypeId = routeTypeId;
		this.routeTypeDescription = routeTypeDescription;
	}

	/**
	 * @return the routeTypeId
	 */
	public int getRouteTypeId() {
		return routeTypeId;
	}

	/**
	 * @param routeTypeId the routeTypeId to set
	 */
	public void setRouteTypeId(int routeTypeId) {
		this.routeTypeId = routeTypeId;
	}

	/**
	 * @return the routeTypeDescription
	 */
	public String getRouteTypeDescription() {
		return routeTypeDescription;
	}

	/**
	 * @param routeTypeDescription the routeTypeDescription to set
	 */
	public void setRouteTypeDescription(String routeTypeDescription) {
		this.routeTypeDescription = routeTypeDescription;
	}
}
