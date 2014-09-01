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
package org.ideaproject.action.entityquery;

import java.util.Arrays;

import javax.persistence.NoResultException;

import org.ideaproject.model.User;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

@Name("userList")
public class UserList extends EntityQuery<User> {

	private static final String EJBQL = "select user from User user";

	private static final String[] RESTRICTIONS = {
			"lower(user.active) like lower(concat(#{userList.user.active},'%'))",
			"lower(user.email) like lower(concat(#{userList.user.email},'%'))",
			"user.email = #{userList.userName}",
			"lower(user.firstName) like lower(concat(#{userList.user.firstName},'%'))",
			"lower(user.lastName) like lower(concat(#{userList.user.lastName},'%'))",
			"lower(user.shaHashedPass) like lower(concat(#{userList.user.shaHashedPass},'%'))",};

	private User user = new User();
	private String userName;

	public UserList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public User getUser() {
		return user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserIdForUsername(String userName) {
		Integer result = null;
		try {
			setUserName(userName);
			User user = getSingleResult();
			if (user != null) {
				result = user.getUserId();
			}
			return result;
		} catch (NoResultException e) {
			return null;
		} finally {
			setUserName(null);
		}
	}
}
