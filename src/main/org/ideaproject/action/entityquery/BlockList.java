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

import org.ideaproject.model.Block;
import org.jboss.seam.annotations.Name;

@Name("blockList")
public class BlockList extends AbstractLastResultEntityQuery<Block> {

	private static final String EJBQL = "select block from Block block";

	private static final String[] RESTRICTIONS = {
		"block.agency = #{agencyHome.definedInstance}",
		"lower(block.blockLabel) like lower(concat(#{blockList.block.blockLabel},'%'))",
	};

	private Block block = new Block();

	public BlockList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Block getBlock() {
		return block;
	}
}
