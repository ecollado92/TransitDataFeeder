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
package org.ideaproject.jsf.functions;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;


/**
 * Adapter for representing general <code>Collection</code> objects (e.g., <code>Set</code>s) as lists
 * since JSF seems to want to work with Lists only.
 * 
 * @author dirk
 */
public class CollectionListAdapter {
	/**
	 * prefix for "get" methods within a class
	 */
	private static final String GETTER_METHOD_PREFIX = "get";

	/**
	 * Gets the List representation of a given collection, bounded by size, ordered by the
	 * comparison field.
	 * 
	 * @param <T> the type of the objects contained within the list/collection.
	 * @param c the collection to return.
	 * @param size the requested list size.
	 * @param comparisonField the field by which to sort the list.
	 * @return the List representation of a given class, bounded by size, ordered by the
	 * comparison field.
	 */
	public static<T> List<T> asList(Collection<T> c, int size, String comparisonField) {
		if (c instanceof List<?> && c instanceof RandomAccess)
			return (List<T>) c;
		
		if (c.isEmpty())
			return Collections.emptyList();
		
		return new ListImpl<T>(c, size, comparisonField);
	}
	
	/**
	 * Gets the List representation of a given collection, with all elements, ordered
	 * by the comparison field.
	 * 
	 * @param <T> the type of the objects contained within the list/collection.
	 * @param c the collection to return.
	 * @param comparisonField the field by which to sort the list.
	 * @return the List representation of a given collection, with all elements, ordered
	 * by the comparison field.
	 */
	public static<T> List<T> asList(Collection<T> c, String comparisonField) {
		return asList(c, c.size(), comparisonField);
	}
	
	/**
	 * Gets the list representation of a given collection, with all elements, with
	 * the collection's default ordering.
	 * @param <T> the type of the objects contained within the list/collection.
	 * @param c the collection to return.
	 * @return the list representation of a given collection, with all elements, with
	 * the collection's default ordering
	 */
	public static<T> List<T> asList(Collection<T> c) {
		return asList(c, null);
	}
	
	/**
	 * Extension of <code>AbstractList</code> supporting the needed functionality for this adapter.
	 * @author dirk
	 *
	 * @param <T> the type of the objects contained within the list.
	 */
	static private class ListImpl<T> extends AbstractList<T> {
		
		private final Collection<T> collection;
		private final int bufferSize;
		private final int collectionSize;
		private int offset;
		private List<T> buffer;
		private Comparator<T> comparator = null;

		public ListImpl(Collection<T> c, int size, String comparisonField) {
			collection = c;
			collectionSize = c.size();
			bufferSize = size == 0 ? collectionSize : Math.min(size, collectionSize);
			buffer = new ArrayList<T>(bufferSize);
			offset = -1;
			if (comparisonField != null) {
				initComparator(comparisonField);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.AbstractList#get(int)
		 */
		@Override
		public T get(int index) {
			if (index < 0 || index >= collectionSize) 
				throw new IndexOutOfBoundsException();
			
			int localOffset = (index / bufferSize) * bufferSize;
			if (localOffset != offset) {
				_loadBuffer(localOffset);
				offset = localOffset;
			}
			return buffer.get(index  - offset);
		}

		/**
		 * Loads the buffer from the collection, starting at index offset.
		 * @param offset the index from which to start the buffer loading.
		 */
		private void _loadBuffer(int offset) {
			Iterator<T> it = collection.iterator();
			int i = 0;
			
			while (i < offset) {
				assert it.hasNext();
				it.next();
				i++;
			}
			
			buffer.clear();
			
			int count = 0;
			while (count < bufferSize && i < collectionSize) {
				assert it.hasNext();
				buffer.add(it.next());
				i++;
				count++;
			}

			if (comparator != null) {
				Collections.sort(buffer, comparator);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.AbstractCollection#size()
		 */
		@Override
		public int size() {
			return collectionSize;
		}

		/**
		 * Initializes the comparator by which to sort the list.
		 * @param fieldName the field to pass to the comparator for sorting.
		 */
		private void initComparator(final String fieldName) {
			comparator = new Comparator<T>() {

				/*
				 * (non-Javadoc)
				 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
				 */
				@Override
				public int compare(T o1, T o2) {
					Class<?> objectClass = o1.getClass();
					
					Object val1 = getValue(objectClass, fieldName, o1);
					Object val2 = getValue(objectClass, fieldName, o2);
					
					if (val1 == null) {
						if (val2 == null) {
							return 0;
						} else {
							return -1;
						}
					} else if (val2 == null) {
						return 1;
					}
					Class<?> valueClass = val1.getClass();
					if (Integer.class.isAssignableFrom(valueClass)) {
						return doCompare((Integer) val1, (Integer) val2);
					} else if (String.class.isAssignableFrom(valueClass)) {
						return doCompare((String) val1, (String) val2);
					} else if (Date.class.isAssignableFrom(valueClass)) {
						return doCompare((Date) val1, (Date) val2);
					} else if (Boolean.class.isAssignableFrom(valueClass)) {
						return doCompare((Boolean) val1, (Boolean) val2);
					}
					return 0;
				}

				/**
				 * Concrete implementation of the compare operation for integers.
				 * @param i1
				 * @param i2
				 * @return the result of Integer.compareTo(Integer)
				 */
				private int doCompare(Integer i1, Integer i2) {
					return i1.compareTo(i2);
				}

				/**
				 * Concrete implementation of the compare operation for strings.
				 * @param i1
				 * @param i2
				 * @return the result of String.compareTo(String)
				 */
				private int doCompare(String s1, String s2) {
					return s1.compareTo(s2);
				}

				/**
				 * Concrete implementation of the compare operation for dates.
				 * @param i1
				 * @param i2
				 * @return the result of Date.compareTo(Date)
				 */
				private int doCompare(Date d1, Date d2) {
					return d1.compareTo(d2);
				}

				/**
				 * Concrete implementation of the compare operation for booleans.
				 * @param i1
				 * @param i2
				 * @return the result of Boolean.compareTo(Boolean)
				 */
				private int doCompare(Boolean b1, Boolean b2) {
					return b1.compareTo(b2);
				}

				/**
				 * Gets the Object's value for a given fieldName using the provided
				 * class as a method template.  Tries first to get it by field, then
				 * falls back to looking up by getter method.
				 *  
				 * @param clazz
				 * @param fieldName
				 * @param obj
				 * @return
				 */
				private Object getValue(Class<?> clazz, String fieldName, T obj) {
					Object result = getValueByField(clazz, fieldName, obj);
					if (result == null) {
						result = getValueByGetterMethod(clazz, fieldName, obj);
					}
					return result;
				}

				private Object getValueByGetterMethod(Class<?> clazz, String fieldName, T obj) {
					Object result = null;
					Method m;
					try {
						m = clazz.getMethod(GETTER_METHOD_PREFIX + initCap(fieldName), new Class<?>[] {});
						result = m.invoke(obj, new Object[] {});
					} catch (SecurityException e) {
						/** ignored; simply return null **/
					} catch (NoSuchMethodException e) {
						/** ignored; simply return null **/
					} catch (IllegalArgumentException e) {
						/** ignored; simply return null **/
					} catch (IllegalAccessException e) {
						/** ignored; simply return null **/
					} catch (InvocationTargetException e) {
						/** ignored; simply return null **/
					}
					return result;
				}

				private Object getValueByField(Class<?> clazz, String fieldName, T obj) {
					Object result = null;
					try {
						Field f = clazz.getField(fieldName);
						result = f.get(obj);
					} catch (SecurityException e) {
						/** ignored; simply return null **/
					} catch (NoSuchFieldException e) {
						/** ignored; simply return null **/
					} catch (IllegalArgumentException e) {
						/** ignored; simply return null **/
					} catch (IllegalAccessException e) {
						/** ignored; simply return null **/
					}
					return result;
				}
			};
			
		}
	}
	
	/**
	 * @param s the string to capitalize
	 * @return the original string with the first letter set to uppercase.
	 */
	private static String initCap(String s) {
		String result = s;
		if (!Character.isUpperCase(s.charAt(0))) {
			StringBuffer buff = new StringBuffer(s.length());
			buff.append(Character.toUpperCase(s.charAt(0)));
			buff.append(s.substring(1));
			result = buff.toString();
		}
		return result;
	}
}
