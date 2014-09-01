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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;
import org.hibernate.validator.NotNull;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "shape_segment")
public class ShapeSegment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shape_segment_id", unique = true, nullable = false)
	private int shapeSegmentId;

	@Column(name = "shape_segment_description")
	private String description;

	@Column(name = "distance", nullable = false)
	@NotNull
	private double distance;

	@ManyToOne
	@JoinColumn(name = "start_coordinate_id")
	private Stop startCoordinate;

	@ManyToOne
	@JoinColumn(name = "end_coordinate_id")
	private Stop endCoordinate;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "shape_id")
	private Shape shape;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "shape_segment_route_map",
			joinColumns = @JoinColumn(name = "shape_segment_id"),
			inverseJoinColumns = @JoinColumn(name = "route_id"))
	private List<Route> routes;

	@OneToMany(mappedBy = "shapeSegment")
	@IndexColumn(name = "shape_pt_sequence")
	private List<ShapePoint> shapePoints;

	public ShapeSegment() {
	}

	public ShapeSegment(double distance) {
		this.distance = distance;
	}

	public ShapeSegment(Stop startCoordinate, Stop endCoordinate, String description,
			double distance) {
		this(distance);
		this.startCoordinate = startCoordinate;
		this.endCoordinate = endCoordinate;
		this.description = description;
	}

	public int getShapeSegmentId() {
		return shapeSegmentId;
	}

	public void setShapeSegmentId(int shapeSegmentId) {
		this.shapeSegmentId = shapeSegmentId;
	}

	public Stop getStartCoordinate() {
		return this.startCoordinate;
	}

	public void setStartCoordinate(Stop startCoordinate) {
		this.startCoordinate = startCoordinate;
	}

	public Stop getEndCoordinate() {
		return this.endCoordinate;
	}

	public void setEndCoordinate(Stop endCoordinate) {
		this.endCoordinate = endCoordinate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getDistance() {
		return this.distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the shape
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * @param shape the shape to set
	 */
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	/**
	 * @return the shapePoints
	 */
	public List<ShapePoint> getShapePoints() {
		return shapePoints;
	}

	/**
	 * @param shapePoints the shapePoints to set
	 */
	public void setShapePoints(List<ShapePoint> shapePoints) {
		this.shapePoints = shapePoints;
	}

	/**
	 * @return the route
	 */
	public List<Route> getRoutes() {
		return routes;
	}

	/**
	 * @param route the route to set
	 */
	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

}
