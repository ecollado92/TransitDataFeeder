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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "agency_group")
public class AgencyGroup extends AbstractLastModifiedEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agency_group_id", unique = true, nullable = false)
	private int agencyGroupId;

	@Column(name = "group_name", nullable = false, length = 40)
	@NotNull
	@Length(max = 40)
	private String groupName;
	
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "agency_group_map", 
//			joinColumns = @JoinColumn(name="agency_group_id"), 
//			inverseJoinColumns = @JoinColumn(name="agency_id"))
	@ManyToMany
	@JoinTable(name = "agency_group_map", 
			joinColumns = @JoinColumn(name="agency_group_id"), 
			inverseJoinColumns = @JoinColumn(name="agency_id"))
	private List<Agency> agencies = new ArrayList<Agency>();

	public AgencyGroup() {
	}

	public AgencyGroup(int agencyGroupId, String groupName) {
		this.agencyGroupId = agencyGroupId;
		this.groupName = groupName;
	}
	public AgencyGroup(int agencyGroupId, String groupName,
			List<Agency> agencies) {
		this.agencyGroupId = agencyGroupId;
		this.groupName = groupName;
		this.agencies = agencies;
	}

	public int getAgencyGroupId() {
		return this.agencyGroupId;
	}

	public void setAgencyGroupId(int agencyGroupId) {
		this.agencyGroupId = agencyGroupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Agency> getAgencies() {
		return this.agencies;
	}

	public void setAgencies(List<Agency> agencies) {
		this.agencies = agencies;
	}

}
