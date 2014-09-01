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

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Conversation;
import org.jboss.seam.core.Manager;
import org.jboss.seam.faces.FacesManager;
import org.jboss.seam.log.Log;
import org.jboss.seam.pageflow.Pageflow;

/**
 * @author dirk
 *
 */
@Name("exceptionHandler")
@Scope(ScopeType.EVENT)
public class ExceptionHandler implements Serializable {
	@Logger
	private Log log;

	private String node;

	@Begin(join = true)
	public String getViewId(String defaultErrorView) {
		String retVal;
		HttpServletRequest request = (HttpServletRequest) FacesContext
			.getCurrentInstance().getExternalContext().getRequest();
		if (request.getMethod().equals("GET")) {
			if (Conversation.instance().isNested()) {
				retVal = FacesManager.instance().getParentConversationViewId();
				Conversation.instance().endAndRedirect();
			} else {
				retVal = defaultErrorView;
			}
		} else if (Conversation.instance().getViewId() != null) {
		      if (node != null) {
		          Pageflow.instance().reposition(node);
		          retVal = Pageflow.instance().getPage().getViewId();
		        } else {
		        	retVal = Conversation.instance().getViewId();
		        }
			Conversation.instance().setViewId(null);
		} else {
			Conversation.instance().end();
			retVal = defaultErrorView;
		}

		Manager.instance().unlockConversation();
		return retVal;
	}


	public String getMessage(Throwable e) {
		String result = formatStacktraceForDisplay(e.getStackTrace());
		return result;
	}

	static String formatStacktraceForDisplay(StackTraceElement[] stacktrace) {
		StringBuffer resultBuffer = new StringBuffer(1024);
		resultBuffer.append("<br/>");
		for (StackTraceElement el : stacktrace) {
			resultBuffer.append(el.toString());
			resultBuffer.append("<br/>");
		}
		return resultBuffer.toString();
	}

	static String formatStacktraceForLog(StackTraceElement[] stacktrace) {
		StringBuffer resultBuffer = new StringBuffer(1024);
		resultBuffer.append("\n");
		for (StackTraceElement el : stacktrace) {
			resultBuffer.append(el.toString());
			resultBuffer.append("\n");
		}
		return resultBuffer.toString();
	}

	/**
	 * @return the node
	 */
	public String getNode() {
		return node;
	}


	/**
	 * @param node the node to set
	 */
	public void setNode(String node) {
		this.node = node;
	}
}
