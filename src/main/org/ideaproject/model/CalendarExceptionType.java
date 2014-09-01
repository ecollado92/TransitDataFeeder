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

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * @author dirk
 *
 */
@Entity
@Table(name = "calendar_exception_type")
public class CalendarExceptionType {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "calendar_exception_type_id", unique = true, nullable = false)
	private int exceptionTypeId;
	
	@Column(name = "calendar_exception_type_description", nullable = false, length = 64)
	@NotNull
	@Length(max = 64)
	private String exceptionTypeDescription; 

	public CalendarExceptionType() {
		/* default ctor for use by framework */
	}

	public CalendarExceptionType(String exceptionTypeDescription) {
		this.exceptionTypeDescription = exceptionTypeDescription;
	}

	public CalendarExceptionType(int exceptionTypeId, String exceptionTypeDescription) {
		this.exceptionTypeId = exceptionTypeId;
		this.exceptionTypeDescription = exceptionTypeDescription;
	}

	/**
	 * @return the exceptionTypeId
	 */
	public int getExceptionTypeId() {
		return exceptionTypeId;
	}

	/**
	 * @param exceptionTypeId the exceptionTypeId to set
	 */
	public void setExceptionTypeId(int exceptionTypeId) {
		this.exceptionTypeId = exceptionTypeId;
	}

	/**
	 * @return the exceptionTypeDescription
	 */
	public String getExceptionTypeDescription() {
		return exceptionTypeDescription;
	}

	/**
	 * @param exceptionTypeDescription the exceptionTypeDescription to set
	 */
	public void setExceptionTypeDescription(String exceptionTypeDescription) {
		this.exceptionTypeDescription = exceptionTypeDescription;
	}
}
