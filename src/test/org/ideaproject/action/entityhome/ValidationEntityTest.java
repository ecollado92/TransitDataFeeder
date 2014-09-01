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
package org.ideaproject.action.entityhome;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jboss.seam.mock.SeamTest;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class ValidationEntityTest extends SeamTest {
	private EntityManagerFactory emf;

	@org.testng.annotations.BeforeClass
	public void setup() {
		emf = Persistence.createEntityManagerFactory("datafeeder");
	}

	@org.testng.annotations.AfterClass
	public void teardown() {
		emf.close();
	}


	@Test
	public void testCreate() throws Exception {
		new ComponentTest() {
			@Override
			protected void testComponents() throws Exception {
				BikeOptionHome bikeOptionHome = (BikeOptionHome) getInstance("bikeOptionHome");
				assert bikeOptionHome.isWired();
				assert !bikeOptionHome.isManaged();
				assert bikeOptionHome.getDefinedInstance() == null;
				bikeOptionHome.setBikeOptionBikeOptionId(11111);
				bikeOptionHome.load();
				assert bikeOptionHome.isWired();
				assert bikeOptionHome.isManaged();
				assert bikeOptionHome.getBikeOptionBikeOptionId().intValue() == 11111; 
				bikeOptionHome.clearInstance();
				assert bikeOptionHome.getInstance() != null;

				RouteTypeHome routeTypeHome = (RouteTypeHome) getInstance("routeTypeHome");
				assert routeTypeHome.isWired();
				assert !routeTypeHome.isManaged();
				assert routeTypeHome.getDefinedInstance() == null;
				routeTypeHome.setRouteTypeRouteTypeId(111111);
				routeTypeHome.load();
				assert routeTypeHome.isWired();
				assert routeTypeHome.isManaged();
				assert routeTypeHome.getRouteTypeRouteTypeId() == 111111;
				routeTypeHome.clearInstance();
				assert routeTypeHome.getInstance() != null;
			}
		}.run();
	}
}
