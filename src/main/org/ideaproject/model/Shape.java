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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "shape")
public class Shape extends AbstractAgencyBasedEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shape_id", unique = true, nullable = false)
	private int shapeId;

	@Column(name = "shape_description", nullable = false, length = 200)
	@NotNull
	@Length(max = 200)
	private String description;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "shape_segment_map",
			joinColumns = @JoinColumn(name = "shape_id"),
			inverseJoinColumns = @JoinColumn(name = "shape_segment_id")
	)
	@IndexColumn(name = "segment_sequence")
	private List<ShapeSegment> shapeSegments;

	public Shape() {
	}

	public Shape(String description, Agency agency) {
		this.description = description;
		setAgency(agency);
	}

	public int getShapeId() {
		return shapeId;
	}

	public void setShapeId(int shapeId) {
		this.shapeId = shapeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the shapeSegments
	 */
	public List<ShapeSegment> getShapeSegments() {
		return shapeSegments;
	}

	/**
	 * @param shapeSegments the shapeSegments to set
	 */
	public void setShapeSegments(List<ShapeSegment> shapeSegments) {
		this.shapeSegments = shapeSegments;
	}

}
