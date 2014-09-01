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

import org.ideaproject.model.Direction;
import org.jboss.seam.annotations.Name;

@Name("directionList")
public class DirectionList extends AbstractLastResultEntityQuery<Direction> {

	private static final String EJBQL = "select direction from Direction direction";

	private static final String[] RESTRICTIONS = {
		"lower(direction.directionLabel) like lower(concat(#{directionList.direction.directionLabel},'%'))",
		"direction.agency = #{agencyHome.definedInstance}",
	};

	private Direction direction = new Direction();

	public DirectionList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Direction getDirection() {
		return direction;
	}

}
