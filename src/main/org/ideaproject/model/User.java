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
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.security.management.UserEnabled;
import org.jboss.seam.annotations.security.management.UserFirstName;
import org.jboss.seam.annotations.security.management.UserLastName;
import org.jboss.seam.annotations.security.management.UserPassword;
import org.jboss.seam.annotations.security.management.UserPrincipal;
import org.jboss.seam.annotations.security.management.UserRoles;

/**
 * User entity.  This is the core "User" object against which JPA authentication
 * is checked/maintained.
 */
@Entity
@Table(name = "dfuser", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	// Primary key for table
	private int userId;

	@Column(name = "email", unique = true, nullable = false, length = 40)
	@NotNull
	@Length(max = 40)
	@UserPrincipal
	@org.hibernate.validator.Email
	// Email address; also, the "username" for logins
	private String email;

	@Column(name = "pass", nullable = false, length = 40)
	@NotNull
	@Length(max = 40)
	@UserPassword(hash = "SHA")
	// User password, hashed via SHA encryption.  Note that this means that
	// the password is NOT recoverable
	private String shaHashedPass;

	@Column(name = "first_name", nullable = false, length = 15)
	@NotNull
	@Length(max = 15)
	@UserFirstName
	// User first name
	private String firstName = "";

	@Column(name = "last_name", nullable = false, length = 30)
	@NotNull
	@Length(max = 30)
	@UserLastName
	// User last name
	private String lastName = "";

	@Column(name = "active", length = 32)
	@Length(max = 32)
	// Legacy (with respect to Trillium app) "active" flag; currenly, unused save
	// for migration into "enabled" field (see below).
	private String active;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registration_date", nullable = false, length = 29)
	@NotNull
	// Registration date for user
	private Date registrationDate = new Date();

	@UserEnabled
	@Column(name = "enabled", nullable = false)
	// Enabled; whether the user is allowed to log in or not
	private boolean enabled;

//	@OneToMany
//	@JoinTable(name = "user_agency_map", 
//			joinColumns = @JoinColumn(name="user_id"), 
//			inverseJoinColumns = @JoinColumn(name="agency_id"))
	@ManyToMany(mappedBy = "agencyUsers")
	// List of agencies to which this user has access
	private List<Agency> agencyAffiliations = new ArrayList<Agency>();

	@UserRoles
	@ManyToMany
	@JoinTable(name = "user_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	// Set of roles this user has
	private Set<UserRole> roles;

	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn(name = "address_id")
	// User address: currently unused, but established for expected future development
	private Address address;

	/**
	 * Default Constructor
	 */
	public User() {
		/* Default ctor */
	}

	/**
	 * Constructor
	 * @param userId
	 * @param email
	 * @param shaHashedPass
	 * @param firstName
	 * @param lastName
	 * @param registrationDate
	 * @param enabled
	 */
	public User(int userId, String email, String shaHashedPass, String firstName,
			String lastName, Date registrationDate, boolean enabled) {
		this.userId = userId;
		this.email = email;
		this.shaHashedPass = shaHashedPass;
		this.firstName = firstName;
		this.lastName = lastName;
		this.registrationDate = registrationDate;
		this.enabled = enabled;
	}

	public User(int userId, String email, String shaHashedPass, String firstName,
			String lastName, String active, Date registrationDate,
			boolean enabled) {
		this.userId = userId;
		this.email = email;
		this.shaHashedPass = shaHashedPass;
		this.firstName = firstName;
		this.lastName = lastName;
		this.active = active;
		this.registrationDate = registrationDate;
		this.enabled = enabled;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getShaHashedPass() {
		return this.shaHashedPass;
	}

	public void setShaHashedPass(String shaHashedPass) {
		this.shaHashedPass = shaHashedPass;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * @return the agencyAffiliations
	 */
	public List<Agency> getAgencyAffiliations() {
		return agencyAffiliations;
	}

	/**
	 * @param agencyAffiliations the agencyAffiliations to set
	 */
	public void setAgencyAffiliations(List<Agency> agencyAffiliations) {
		this.agencyAffiliations = agencyAffiliations;
	}

	/**
	 * @return the roles
	 */
	public Set<UserRole> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Transient
	public int getAgencyAffiliationCount() {
		return (agencyAffiliations == null) ? 0 : agencyAffiliations.size();
	}
}
