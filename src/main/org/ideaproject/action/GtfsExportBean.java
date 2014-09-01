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
package org.ideaproject.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ideaproject.action.entityhome.AgencyHome;
import org.ideaproject.action.entityquery.FullAgencyList;
import org.ideaproject.export.AbstractExporterService;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * @author dirk
 *
 */
@Name("gtfsExportBean")
public class GtfsExportBean {
	private final static String FILENAME_PARTS_SEPARATOR = "-";
	@In
	AgencyHome agencyHome;

	@In("#{gtfsExporterService}")
	AbstractExporterService gtfsExporterService;

	@In(create = true)
	FullAgencyList fullAgencyList;

	public void processActionExport() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
		HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
		processActionExport(fc, request, response);
	}

	public void processActionExport(FacesContext fc, HttpServletRequest request, HttpServletResponse response) {
		String fileName = buildExportFileName();

		String contentType = "application/force-download";
		try {
			if (response != null) {
				if (request.isSecure()) {
					response.setHeader("Cache-Control", "private");
					response.setHeader("Pragma", "private");
				}
				response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
				response.setContentType(contentType);
				OutputStream responseOut = response.getOutputStream();
				
//				gtfsExporterService.doExport(responseOut, agencyHome.getInstance());
				fullAgencyList.setAgencyId(agencyHome.getAgencyAgencyId());
				gtfsExporterService.doExport(responseOut, fullAgencyList.getSingleResult());
				responseOut.close();
			}
		} catch (IOException e) {
//			log.error("Error exporting roster", e);
//			String message = "Error uploading roster: " + e.getMessage();
//			fc.addMessage("uploadRoster", new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
		} finally {
			fc.responseComplete();
		}
	}

//	public void processActionTimeFiveExports() {
//		ExportTimer timer = new ExportTimer();
//		for (int i=0; i < 5; i++) {
//			timer.reset();
//			processActionExport();
//			timer.print("Time for iteration " + i);
//		}
//	}

	private String buildExportFileName() {
		StringBuffer resultBuffer = new StringBuffer();
		resultBuffer.append("export");
		resultBuffer.append(FILENAME_PARTS_SEPARATOR);
		resultBuffer.append(agencyHome.getInstance().getAgencyId());
		resultBuffer.append(FILENAME_PARTS_SEPARATOR);
		resultBuffer.append(System.currentTimeMillis() / 1000);
		resultBuffer.append(".zip");
		return resultBuffer.toString();
	}

//	private class ExportTimer {
//		long t;
//		ExportTimer() {
//			reset();
//		}
//
//		void reset() {
//			t = System.currentTimeMillis();
//		}
//
//		public long elapsed() {
//			return System.currentTimeMillis() - t;
//		}
//		
//		void print(String s) {
//			System.out.println(s + ": " + elapsed());
//		}
//	}
}
