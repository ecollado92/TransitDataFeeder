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
package org.ideaproject.test;

import org.testng.annotations.Test;
import org.jboss.seam.core.Manager;
import org.jboss.seam.mock.SeamTest;
import org.jboss.seam.web.Session;

public class LoginTest extends SeamTest {
	 
	@Test
	public void testCompLogin() throws Exception {
		new ComponentTest() {
			@Override
			protected void testComponents() throws Exception{
				assert getValue("#{identity.loggedIn}").equals(false);
				setValue("#{credentials.username}", "admin");
				setValue("#{credentials.password}", "");
				invokeMethod("#{identity.login}");
				assert getValue("#{authenticatedUser.firstName}").equals("Admin");
				assert getValue("#{authenticatedUser.lastName}").equals("User");
				assert getValue("#{identity.loggedIn}").equals(true);
				invokeMethod("#{identity.logout}");
				assert getValue("#{identity.loggedIn}").equals(false);
				setValue("#{credentials.username}", "admin");
				setValue("#{credentials.password}", "foo");
				invokeMethod("#{identity.login}");
				assert getValue("#{identity.loggedIn}").equals(false);
			}

		}.run();
	}
	 
	@Test
	public void testLoginComponent() throws Exception
	{
		new ComponentTest() {

			@Override
			protected void testComponents() throws Exception
			{
				assert getValue("#{identity.loggedIn}").equals(false);
				setValue("#{credentials.username}", "admin");
				setValue("#{credentials.password}", "");
				invokeMethod("#{identity.login}");
				assert getValue("#{authenticatedUser.firstName}").equals("Admin");
				assert getValue("#{authenticatedUser.lastName}").equals("User");
				assert getValue("#{identity.loggedIn}").equals(true);
				invokeMethod("#{identity.logout}");
				assert getValue("#{identity.loggedIn}").equals(false);
				setValue("#{credentials.username}", "admin");
				setValue("#{credentials.password}", "foo");
				invokeMethod("#{identity.login}");
				assert getValue("#{identity.loggedIn}").equals(false);
			}

		}.run();
	}
	 
	@Test
	public void testLogin() throws Exception
	{

		new FacesRequest() {

			@Override
			protected void invokeApplication()
			{
				assert !isSessionInvalid();
				assert getValue("#{identity.loggedIn}").equals(false);
			}

		}.run();

		new FacesRequest() {

			@Override
			protected void updateModelValues() throws Exception
			{
				assert !isSessionInvalid();
				setValue("#{credentials.username}", "admin");
				setValue("#{credentials.password}", "");
			}

			@Override
			protected void invokeApplication()
			{
				invokeAction("#{identity.login}");
			}

			@Override
			protected void renderResponse()
			{
				assert getValue("#{authenticatedUser.firstName}").equals("Admin");
				assert getValue("#{authenticatedUser.email}").equals("admin");
				assert getValue("#{authenticatedUser.lastName}").equals("User");
				assert !Manager.instance().isLongRunningConversation();
				assert getValue("#{identity.loggedIn}").equals(true);
			}

		}.run();

		new FacesRequest() {

			@Override
			protected void invokeApplication()
			{
				assert !isSessionInvalid();
				assert getValue("#{identity.loggedIn}").equals(true);
			}

		}.run();

		new FacesRequest() {

			@Override
			protected void invokeApplication()
			{
				assert !Manager.instance().isLongRunningConversation();
				assert !isSessionInvalid();
				invokeMethod("#{identity.logout}");
				assert Session.instance().isInvalid();
			}

			@Override
			protected void renderResponse()
			{
				assert getValue("#{identity.loggedIn}").equals(false);
				assert Session.instance().isInvalid();
			}

		}.run();

	}

}
//public class LoginTest extends SeamTest {
//
//	@Test
//	public void test_login() throws Exception {
//		new FacesRequest("/login.xhtml") {
//			@Override
//			protected void updateModelValues() throws Exception {				
//				//set form input to model attributes
//				setValue("#{credentials.username}", "admin");
//				setValue("#{credentials.password}", "");
//			}
//
//			@Override
//			protected void invokeApplication() {
//				//call action methods here
//				invokeMethod("#{identity.login}");
//			}
//
//			@Override
//			protected void renderResponse() {
//				System.out.println(this.getOutcome());
//				//check model attributes if needed
//				Object authUserEmail = getValue("#{authenticatedUser.email}");
//				assert authUserEmail != null;
//				assert authUserEmail.equals("admin");
//			}
//		}.run();
//	}
//}
