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

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author dirk
 *
 */
@MappedSuperclass
public class AbstractLastModifiedEntity {
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_last_modified")
	private Date dateLastModified;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_last_modified")
	private User userLastModified;

	/**
	 * @return the dateLastModified
	 */
	public Date getDateLastModified() {
		return dateLastModified;
	}

	/**
	 * @param dateLastModified the dateLastModified to set
	 */
	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}

	/**
	 * @return the userLastModified
	 */
	public User getUserLastModified() {
		return userLastModified;
	}

	/**
	 * @param userLastModified the userLastModified to set
	 */
	public void setUserLastModified(User userLastModified) {
		this.userLastModified = userLastModified;
	}
}
