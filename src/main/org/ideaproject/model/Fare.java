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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.ideaproject.Constants;
import org.ideaproject.Exportable;
import org.ideaproject.QueryNames;

/**
 * 
 * @author dirk
 *
 */
@Entity
@Table(name = "fare")
@NamedQueries({
	@NamedQuery(name = QueryNames.COUNT_AGENCY_FARES_ALL,
			query = "SELECT COUNT(*) FROM Fare fare WHERE fare.agency = ?1"),
	@NamedQuery(name = QueryNames.COUNT_AGENCY_FARES_UNUSED,
			query = "SELECT COUNT(*) FROM Fare fare WHERE fare.agency = ?1 " +
					"AND NOT EXISTS (SELECT 1 FROM fare.fareRules)")
})
public class Fare extends AbstractAgencyBasedEntity implements java.io.Serializable, Exportable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fare_id", unique = true, nullable = false)
	private int fareId;

	@Column(name = "price", nullable = false, precision = 17, scale = 17)
	@org.hibernate.validator.Min(0)
	private double price;

	@Column(name = "currency_type", nullable = false, length = 3)
	@NotNull
	@Length(max = 3)
	private String currencyType = Constants.DEFAULT_CURRENCY_TYPE;

	@Column(name = "transfer_duration")
	private Integer transferDuration;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "transfers")
	private TransferLimit transferLimit;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "payment_method", nullable = false)
	private PaymentMethod paymentMethod;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fare", cascade = CascadeType.ALL)
	private List<FareRule> fareRules = new ArrayList<FareRule>();

	@org.hibernate.annotations.Formula("(SELECT count(*) FROM fare_rule rule WHERE rule.fare_id = fare_id)")
	private Long ruleCount;

	public Fare() {
	}

	public Fare(int fareId, Agency agency, double price,
			String currencyType, PaymentMethod paymentMethod) {
		this.fareId = fareId;
		this.price = price;
		this.currencyType = currencyType;
		this.paymentMethod = paymentMethod;
		setAgency(agency);
	}

	public Fare(int fareId, Agency agency, double price,
			String currencyType, PaymentMethod paymentMethod, TransferLimit transferLimit,
			Integer transferDuration) {
		this.fareId = fareId;
		this.price = price;
		this.currencyType = currencyType;
		this.paymentMethod = paymentMethod;
		this.transferLimit = transferLimit;
		this.transferDuration = transferDuration;
		setAgency(agency);
	}

	public int getFareId() {
		return this.fareId;
	}

	public void setFareId(int fareId) {
		this.fareId = fareId;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCurrencyType() {
		return this.currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public PaymentMethod getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public TransferLimit getTransferLimit() {
		return this.transferLimit;
	}

	public void setTransferLimit(TransferLimit transferLimit) {
		this.transferLimit = transferLimit;
	}

	public Integer getTransferDuration() {
		return this.transferDuration;
	}

	public void setTransferDuration(Integer transferDuration) {
		this.transferDuration = transferDuration;
	}

	/**
	 * @return the fareRules
	 */
	public List<FareRule> getFareRules() {
		return fareRules;
	}

	/**
	 * @param fareRules the fareRules to set
	 */
	public void setFareRules(List<FareRule> fareRules) {
		this.fareRules = fareRules;
	}

	/**
	 * @return the ruleCount
	 */
	public Long getRuleCount() {
		return ruleCount;
	}

	/**
	 * @param ruleCount the ruleCount to set
	 */
	public void setRuleCount(Long ruleCount) {
		this.ruleCount = ruleCount;
	}

}
