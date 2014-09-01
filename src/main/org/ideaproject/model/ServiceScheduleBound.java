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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.NotNull;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "service_schedule_bound")
public class ServiceScheduleBound extends AbstractLastModifiedEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "service_schedule_bound_id", unique = true, nullable = false)
	private int serviceScheduleBoundsId;

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date", nullable = false, length = 13)
	@NotNull
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "end_date", nullable = false, length = 13)
	@NotNull
	private Date endDate;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "service_schedule_group_id")
	private ServiceScheduleGroup serviceScheduleGroup;

	public ServiceScheduleBound() {
	}

	public ServiceScheduleBound(int serviceScheduleBoundsId,
			ServiceScheduleGroup serviceScheduleGroup, Date startDate, Date endDate,
			int agencyId) {
		this.serviceScheduleBoundsId = serviceScheduleBoundsId;
		this.serviceScheduleGroup = serviceScheduleGroup;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getServiceScheduleBoundsId() {
		return this.serviceScheduleBoundsId;
	}

	public void setServiceScheduleBoundsId(int serviceScheduleBoundsId) {
		this.serviceScheduleBoundsId = serviceScheduleBoundsId;
	}

	public ServiceScheduleGroup getServiceScheduleGroup() {
		return this.serviceScheduleGroup;
	}

	public void setServiceScheduleGroup(ServiceScheduleGroup serviceScheduleGroup) {
		this.serviceScheduleGroup = serviceScheduleGroup;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
