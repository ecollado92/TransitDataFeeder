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
@Table(name = "fare_rule")
public class FareRule extends AbstractAgencyBasedEntity implements java.io.Serializable, Exportable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fare_rule_id", unique = true, nullable = false)
	private int fareRuleId;

	@ManyToOne
	@JoinColumn(name = "origin_id")
	private Zone origin;

	@ManyToOne
	@JoinColumn(name = "destination_id")
	private Zone destination;

	@ManyToOne
	@JoinColumn(name = "contains_id")
	private Zone contains;

	@ManyToOne
	@JoinColumn(name = "fare_id")
	private Fare fare;

	@ManyToOne
	@JoinColumn(name = "route_id")
	private Route route;

	public FareRule() {
	}

	public FareRule(int fareRuleId, Fare fare, Agency agency) {
		this.fareRuleId = fareRuleId;
		this.fare = fare;
		setAgency(agency);
	}

	public FareRule(int fareRuleId, Fare fare, Route route,
			Zone origin, Zone destination, Zone contains,
			Agency agency) {
		this.fareRuleId = fareRuleId;
		this.fare = fare;
		this.route = route;
		this.origin = origin;
		this.destination = destination;
		this.contains = contains;
		setAgency(agency);
	}

	public int getFareRuleId() {
		return this.fareRuleId;
	}

	public void setFareRuleId(int fareRuleId) {
		this.fareRuleId = fareRuleId;
	}

	public Fare getFare() {
		return this.fare;
	}

	public void setFare(Fare fare) {
		this.fare = fare;
	}

	public Route getRoute() {
		return this.route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Zone getOrigin() {
		return this.origin;
	}

	public void setOrigin(Zone origin) {
		this.origin = origin;
	}

	public Zone getDestination() {
		return this.destination;
	}

	public void setDestination(Zone destination) {
		this.destination = destination;
	}

	public Zone getContains() {
		return this.contains;
	}

	public void setContains(Zone contains) {
		this.contains = contains;
	}

}
