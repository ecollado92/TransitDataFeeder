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

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "shape_point")
public class ShapePoint extends AbstractAgencyBasedEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shape_point_id", unique = true, nullable = false)
	private int shapePointId;

	@Column(name = "shape_dist_traveled")
	private Double shapeDistanceTraveled;

	@Column(name = "shape_pt_lon", nullable = false, precision = 17, scale = 17)
	private double shapePointLongitude;

	@Column(name = "shape_pt_lat", nullable = false, precision = 17, scale = 17)
	private double shapePointLatitude;

	@ManyToOne
	@JoinColumn(name = "shape_id")
	private Shape shape;

	@ManyToOne
	@JoinColumn(name = "shape_segment_id", nullable = false)
	private ShapeSegment shapeSegment;

	public ShapePoint() {
	}

	public ShapePoint(Agency agency, Shape shape,
			ShapeSegment shapeSegment, double shapePtLon, double shapePtLat) {
		setAgency(agency);
		this.shape = shape;
		this.shapeSegment = shapeSegment;
		this.shapePointLongitude = shapePtLon;
		this.shapePointLatitude = shapePtLat;
	}

	public int getShapePointId() {
		return this.shapePointId;
	}

	public void setShapePointId(int shapePointId) {
		this.shapePointId = shapePointId;
	}

	public double getShapePointLongitude() {
		return this.shapePointLongitude;
	}

	public void setShapePointLongitude(double shapePointLongitude) {
		this.shapePointLongitude = shapePointLongitude;
	}

	public double getShapePointLatitude() {
		return this.shapePointLatitude;
	}

	public void setShapePointLatitude(double shapePointLatitude) {
		this.shapePointLatitude = shapePointLatitude;
	}

	public Double getShapeDistanceTraveled() {
		return this.shapeDistanceTraveled;
	}

	public void setShapeDistanceTraveled(Double shapeDistanceTraveled) {
		this.shapeDistanceTraveled = shapeDistanceTraveled;
	}

	/**
	 * @return the shapeSegment
	 */
	public ShapeSegment getShapeSegment() {
		return shapeSegment;
	}

	/**
	 * @param shapeSegment the shapeSegment to set
	 */
	public void setShapeSegment(ShapeSegment shapeSegment) {
		this.shapeSegment = shapeSegment;
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

}
