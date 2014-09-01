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

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "shape_segment_triproute_assoc")
public class ShapeSegmentTriprouteAssoc implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int shapeSegmentTriprouteAssoc;
	private Integer shapeSegmentId;
	private Integer routeId;
	private Integer tripId;

	public ShapeSegmentTriprouteAssoc() {
	}

	public ShapeSegmentTriprouteAssoc(int shapeSegmentTriprouteAssoc) {
		this.shapeSegmentTriprouteAssoc = shapeSegmentTriprouteAssoc;
	}
	public ShapeSegmentTriprouteAssoc(int shapeSegmentTriprouteAssoc,
			Integer shapeSegmentId, Integer routeId, Integer tripId) {
		this.shapeSegmentTriprouteAssoc = shapeSegmentTriprouteAssoc;
		this.shapeSegmentId = shapeSegmentId;
		this.routeId = routeId;
		this.tripId = tripId;
	}

	@Id
	@Column(name = "shape_segment_triproute_assoc", unique = true, nullable = false)
	public int getShapeSegmentTriprouteAssoc() {
		return this.shapeSegmentTriprouteAssoc;
	}

	public void setShapeSegmentTriprouteAssoc(int shapeSegmentTriprouteAssoc) {
		this.shapeSegmentTriprouteAssoc = shapeSegmentTriprouteAssoc;
	}

	@Column(name = "shape_segment_id")
	public Integer getShapeSegmentId() {
		return this.shapeSegmentId;
	}

	public void setShapeSegmentId(Integer shapeSegmentId) {
		this.shapeSegmentId = shapeSegmentId;
	}

	@Column(name = "route_id")
	public Integer getRouteId() {
		return this.routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	@Column(name = "trip_id")
	public Integer getTripId() {
		return this.tripId;
	}

	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}

}
