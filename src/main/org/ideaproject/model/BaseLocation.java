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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Mapped Superclass sitting in object hierarchy between {@link org.ideaproject.model.Location}
 * and its concrete subclasses.  Provided so that the many-to-one references can be traced from
 * the referring classes, since Entity/Inheritance does not seem to make said references available
 * to subclasses for Hibernate mapping purposes.
 * 
 * Note that since multiple inheritance is also not supported, we pull in the agency from
 * {@link org.ideaproject.model.AbstractAgencyBasedEntity} and the last modified user/date from
 * {@link org.ideaproject.model.AbstractLastModifiedEntity} so that these fields are accessible
 * (and therefore persistable) as well.
 * 
 * @author dirk
 * @see org.ideaproject.model.AbstractLastModifiedEntity
 * @see org.ideaproject.model.AbstractAgencyBasedEntity
 * @see org.ideaproject.model.Location
 * @see org.ideaproject.model.Station
 * @see org.ideaproject.model.Stop
 */
@MappedSuperclass
public abstract class BaseLocation extends Location {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "agency_id")
	private Agency agency;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "direction_id")
	private Direction direction;

	@ManyToOne
	@JoinColumn(name = "zone_id")
	private Zone zone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_last_modified")
	private Date dateLastModified;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_last_modified")
	private User userLastModified;

	public BaseLocation() {
		super();
	}

	public BaseLocation(int stopId, String stopName, String stopDesc,
			Zone zone, Direction direction) {
		super(stopId, stopName, stopDesc);
		this.zone = zone;
		this.direction = direction;
	}

	public BaseLocation(int stopId, String stopName, String stopDesc,
			String stopCode, String stopComments,
			Double stopLat, Double stopLon, String stopUrl,
			Zone zone, Direction direction) {
		super(stopId, stopName, stopDesc, stopCode, stopComments, stopLat, stopLon, stopUrl);
		this.zone = zone;
		this.direction = direction;
	}

	/**
	 * @return the agency this entity is associated with.
	 */
	public Agency getAgency() {
		return agency;
	}

	/**
	 * @param agency the agency to which to associate this entity
	 */
	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public Zone getZone() {
		return this.zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public Direction getDirection() {
		return this.direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * @return the dateLastModified
	 */
	public Date getDateLastModified() {
		return dateLastModified;
	}

	/**
	 * @param dateLastModified the dateLastModified to set
	 */
	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}

	/**
	 * @return the userLastModified
	 */
	public User getUserLastModified() {
		return userLastModified;
	}

	/**
	 * @param userLastModified the userLastModified to set
	 */
	public void setUserLastModified(User userLastModified) {
		this.userLastModified = userLastModified;
	}

}
