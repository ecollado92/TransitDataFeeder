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


/**
 * @author dirk
 *
 */
public class MultiColumnSortableQuery<E> extends AbstractLastResultEntityQuery<E> {
	private static final String MULTI_COLUMN_SEPARATOR = ",";

	private String multiColumnSort;

	public String getMultiColumnSort() {
		return multiColumnSort;
	}

	public void setMultiColumnSort(String multiColumnSort) {
		this.multiColumnSort = multiColumnSort;
		setOrder(buildMultiColumnSortString(multiColumnSort));
	}

	private String buildMultiColumnSortString(String multiColumnSortString) {
		StringBuffer resultBuffer = new StringBuffer(32);
		String[] columns = multiColumnSortString.split(MULTI_COLUMN_SEPARATOR);
		for (int i = 0; i < columns.length; i++) {
			resultBuffer.append(columns[i].trim());
			if (getOrderDirection() != null) {
				resultBuffer.append(" ");
				resultBuffer.append(getOrderDirection());
			}
			if (i < columns.length - 1) {
				resultBuffer.append(MULTI_COLUMN_SEPARATOR);
			}
		}
		return resultBuffer.toString();
	}

}
