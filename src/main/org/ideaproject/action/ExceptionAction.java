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

import javax.el.ELException;

import org.jboss.seam.Component;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.Decision;

/**
 * @author dirk
 *
 */
public class ExceptionAction implements ActionHandler {

	@Logger
	private Log log;
	
	/* (non-Javadoc)
	 * @see org.jbpm.graph.def.ActionHandler#execute(org.jbpm.graph.exe.ExecutionContext)
	 */
	public void execute(ExecutionContext executionContext) throws Exception {
		Throwable e = executionContext.getException();
		if (ELException.class.isAssignableFrom(e.getClass()) && e.getCause() != null
				&& e.getCause() instanceof Exception) {
			e = e.getCause();
		}

		/*
		 * if this is a transition, we memorize the source, throw an exception and
		 * reposition the flow from within seam.
		 */
		Node source = executionContext.getTransitionSource();
		if (source != null) {
			/*
			 * if the source of the transition is a decision, handle the failure of
			 * the transition like the failure of the decision.
			 */
			while (source instanceof Decision) {
				Decision d = (Decision) source;
				source = d.getLeavingTransition("failure").getTo();
			}
			ExceptionHandler handler = (ExceptionHandler) Component
				.getInstance(ExceptionHandler.class);
			handler.setNode(source.getName());
			throw new RuntimeException("caught in transition", e);
		} else {
			log.error(ExceptionHandler.formatStacktraceForLog(e.getStackTrace()));
		}

	}

}
