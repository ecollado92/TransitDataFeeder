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
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "direction")
public class Direction extends AbstractAgencyBasedEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "direction_id", unique = true, nullable = false)
	private int directionId;

	@Column(name = "direction_label", nullable = false, length = 35)
	@NotNull
	@Length(max = 35)
	private String directionLabel;

	@Column(name = "is_inbound")
	private Boolean inboundFlag;

	public Direction() {
	}

	public Direction(int directionId, Agency agency, String directionLabel) {
		this.directionId = directionId;
		setAgency(agency);
		this.directionLabel = directionLabel;
	}
	public Direction(int directionId, Agency agency, String directionLabel,
			Boolean inboundFlag) {
		this.directionId = directionId;
		setAgency(agency);
		this.directionLabel = directionLabel;
		this.inboundFlag = inboundFlag;
	}

	public int getDirectionId() {
		return this.directionId;
	}

	public void setDirectionId(int directionId) {
		this.directionId = directionId;
	}

	public String getDirectionLabel() {
		return this.directionLabel;
	}

	public void setDirectionLabel(String directionLabel) {
		this.directionLabel = directionLabel;
	}

	public Boolean getInboundFlag() {
		return this.inboundFlag;
	}

	/**
	 * Sets this direction's inbound flag.  Possible values are Boolean.TRUE (inbound), Boolean.FALSE
	 * (outbound), or null (unknown).
	 * 
	 * @param inboundFlag
	 */
	public void setInboundFlag(Boolean inboundFlag) {
		this.inboundFlag = inboundFlag;
	}

}
