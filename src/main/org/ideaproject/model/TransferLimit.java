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
 * @author dirk
 *
 */
@Entity
@Table(name = "transfer_limit")
public class TransferLimit implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transfer_limit_id", unique = true, nullable = false)
	private int transferLimitId;

	@Column(name = "transfer_limit_description", nullable = false, length = 128)
	@NotNull
	@Length(max = 128)
	private String transferLimitDescription;

	/**
	 * @return the transferLimitId
	 */
	public int getTransferLimitId() {
		return transferLimitId;
	}

	/**
	 * @param transferLimitId the transferLimitId to set
	 */
	public void setTransferLimitId(int transferLimitId) {
		this.transferLimitId = transferLimitId;
	}

	/**
	 * @return the transferLimitDescription
	 */
	public String getTransferLimitDescription() {
		return transferLimitDescription;
	}

	/**
	 * @param transferLimitDescription the transferLimitDescription to set
	 */
	public void setTransferLimitDescription(String transferLimitDescription) {
		this.transferLimitDescription = transferLimitDescription;
	}

}
