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
@Table(name = "shape_segments_assoc")
public class ShapeSegmentsAssoc implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int shapeSegmentAssocId;
	private Integer shapeSegmentId;
	private Integer shapeId;
	private Short segmentSequence;

	public ShapeSegmentsAssoc() {
	}

	public ShapeSegmentsAssoc(int shapeSegmentAssocId) {
		this.shapeSegmentAssocId = shapeSegmentAssocId;
	}
	public ShapeSegmentsAssoc(int shapeSegmentAssocId, Integer shapeSegmentId,
			Integer shapeId, Short segmentSequence) {
		this.shapeSegmentAssocId = shapeSegmentAssocId;
		this.shapeSegmentId = shapeSegmentId;
		this.shapeId = shapeId;
		this.segmentSequence = segmentSequence;
	}

	@Id
	@Column(name = "shape_segment_assoc_id", unique = true, nullable = false)
	public int getShapeSegmentAssocId() {
		return this.shapeSegmentAssocId;
	}

	public void setShapeSegmentAssocId(int shapeSegmentAssocId) {
		this.shapeSegmentAssocId = shapeSegmentAssocId;
	}

	@Column(name = "shape_segment_id")
	public Integer getShapeSegmentId() {
		return this.shapeSegmentId;
	}

	public void setShapeSegmentId(Integer shapeSegmentId) {
		this.shapeSegmentId = shapeSegmentId;
	}

	@Column(name = "shape_id")
	public Integer getShapeId() {
		return this.shapeId;
	}

	public void setShapeId(Integer shapeId) {
		this.shapeId = shapeId;
	}

	@Column(name = "segment_sequence")
	public Short getSegmentSequence() {
		return this.segmentSequence;
	}

	public void setSegmentSequence(Short segmentSequence) {
		this.segmentSequence = segmentSequence;
	}

}
