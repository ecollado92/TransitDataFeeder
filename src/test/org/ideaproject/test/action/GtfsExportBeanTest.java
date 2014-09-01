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
package org.ideaproject.test.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;

import org.ideaproject.action.GtfsExportBean;
import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.test.ObjectTreeBuildingSeamTest;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.mock.DelegatingServletOutputStream;
import org.jboss.seam.mock.EnhancedMockHttpServletRequest;
import org.jboss.seam.mock.EnhancedMockHttpServletResponse;
import org.testng.annotations.Test;

/**
 * @author dirk
 *
 */
public class GtfsExportBeanTest extends ObjectTreeBuildingSeamTest {

	@Test
	@Transactional
	public void testDeleteAgencyGroup() throws Exception {
		loginAdmin();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				AgencyHome agencyHome = (AgencyHome) getInstance("agencyHome");
				GtfsExportBean gtfsExportBean = (GtfsExportBean) getInstance("gtfsExportBean");

				createObjectTree();

				assert agencyHome.getDefinedInstance() != null;
				assert agencyHome.isManaged();
				File testFile = File.createTempFile("test", ".txt");
				FileOutputStream outstream = new FileOutputStream(testFile);
				gtfsExportBean.processActionExport(FacesContext.getCurrentInstance(), new TestMockHttpServletRequest(),
						new TestMockHttpServletResponse(outstream));
			}
		}.run();
	}

	private static final class TestMockHttpServletRequest extends EnhancedMockHttpServletRequest {
		
	}

	private static final class TestMockHttpServletResponse extends EnhancedMockHttpServletResponse {
		private final DelegatingServletOutputStream outputStream;

		public TestMockHttpServletResponse(OutputStream outStream) {
			this.outputStream = new DelegatingServletOutputStream(outStream);
		}

		@Override
		public ServletOutputStream getOutputStream() {
			return outputStream;
		}
	}
}
