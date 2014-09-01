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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "service_schedule_group")
public class ServiceScheduleGroup extends AbstractAgencyBasedEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "service_schedule_group_id", unique = true, nullable = false)
	private int serviceScheduleGroupId;

	@Column(name = "service_schedule_group_label", nullable = false, length = 50)
	@NotNull
	@Length(max = 50)
	private String serviceScheduleGroupLabel;

	@OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.REMOVE}, mappedBy = "serviceScheduleGroup")
	private Set<ServiceScheduleBound> serviceScheduleBounds = new HashSet<ServiceScheduleBound>();

	@OneToMany(fetch = FetchType.LAZY, cascade={CascadeType.REMOVE}, mappedBy = "serviceScheduleGroup")
	private List<Calendar> calendars = new ArrayList<Calendar>();

	@org.hibernate.annotations.Formula("exists (select 1 from service_schedule_bound b where b.service_schedule_group_id = "+
			"service_schedule_group_id and now() between b.start_date and b.end_date)")
	private boolean current;

	public ServiceScheduleGroup() {
	}

	public ServiceScheduleGroup(int serviceScheduleGroupId,
			String serviceScheduleGroupLabel, Agency agency) {
		this.serviceScheduleGroupId = serviceScheduleGroupId;
		this.serviceScheduleGroupLabel = serviceScheduleGroupLabel;
		setAgency(agency);
	}

	public int getServiceScheduleGroupId() {
		return this.serviceScheduleGroupId;
	}

	public void setServiceScheduleGroupId(int serviceScheduleGroupId) {
		this.serviceScheduleGroupId = serviceScheduleGroupId;
	}

	public String getServiceScheduleGroupLabel() {
		return this.serviceScheduleGroupLabel;
	}

	public void setServiceScheduleGroupLabel(String serviceScheduleGroupLabel) {
		this.serviceScheduleGroupLabel = serviceScheduleGroupLabel;
	}

	/**
	 * @return the serviceScheduleBounds
	 */
	public Set<ServiceScheduleBound> getServiceScheduleBounds() {
		return serviceScheduleBounds;
	}

	/**
	 * @param serviceScheduleBounds the serviceScheduleBounds to set
	 */
	public void setServiceScheduleBounds(
			Set<ServiceScheduleBound> serviceScheduleBounds) {
		this.serviceScheduleBounds = serviceScheduleBounds;
	}

	/**
	 * @return the calendars
	 */
	public List<Calendar> getCalendars() {
		return calendars;
	}

	/**
	 * @param calendars the calendars to set
	 */
	public void setCalendars(List<Calendar> calendars) {
		this.calendars = calendars;
	}

	/**
	 * @return the current
	 */
	public boolean isCurrent() {
		return current;
	}

	/**
	 * @param current the current to set
	 */
	public void setCurrent(boolean current) {
		this.current = current;
	}

}
