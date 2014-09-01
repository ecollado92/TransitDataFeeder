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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.ideaproject.model.BikeOption;
import org.ideaproject.model.CalendarExceptionType;
import org.ideaproject.model.PaymentMethod;
import org.ideaproject.model.PickupType;
import org.ideaproject.model.RouteType;
import org.ideaproject.model.TransferLimit;
import org.ideaproject.model.TransferType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * @author dirk
 *
 */
@Name("validationTableBean")
//@Scope(ScopeType.APPLICATION)
public class ValidationTableBean {
	@In
	private EntityManager entityManager;

	private List<CalendarExceptionType> calendarExceptionTypes = null;
	private List<PaymentMethod> paymentMethods = null;
	private List<PickupType> pickupTypes = null;
	private List<RouteType> routeTypes = null;
	private List<BikeOption> bikeOptions = null;
	private List<TransferLimit> transferLimits = null;
	private List<TransferType> transferTypes = null;

	/**
	 * @return the calendarExceptionTypes
	 */
	public List<CalendarExceptionType> getCalendarExceptionTypes() {
		if (calendarExceptionTypes == null) {
			List<?> untypedResultList = 
				entityManager.createQuery("from CalendarExceptionType calendarExceptionType")
					.getResultList();
			calendarExceptionTypes = coerceUntypedList(CalendarExceptionType.class, untypedResultList);
		}
		return calendarExceptionTypes;
	}

	/**
	 * @return the pickupTypes
	 */
	public List<PaymentMethod> getPaymentMethods() {
		if (paymentMethods == null) {
			List<?> untypedResultList = 
				entityManager.createQuery("from PaymentMethod paymentMethod")
					.getResultList();
			paymentMethods = coerceUntypedList(PaymentMethod.class, untypedResultList);
		}
		return paymentMethods;
	}

	/**
	 * @return the pickupTypes
	 */
	public List<PickupType> getPickupTypes() {
		if (pickupTypes == null) {
			List<?> untypedResultList = 
				entityManager.createQuery("from PickupType pickupType")
					.getResultList();
			pickupTypes = coerceUntypedList(PickupType.class, untypedResultList);
		}
		return pickupTypes;
	}

	/**
	 * @return the routeTypes
	 */
	public List<RouteType> getRouteTypes() {
		if (routeTypes == null) {
			List<?> untypedResultList = 
				entityManager.createQuery("from RouteType routeType")
					.getResultList();
			routeTypes = coerceUntypedList(RouteType.class, untypedResultList);
		}
		return routeTypes;
	}

	/**
	 * @return the bikeOptions
	 */
	public List<BikeOption> getBikeOptions() {
		if (bikeOptions == null) {
			List<?> untypedResultList = 
				entityManager.createQuery("from BikeOption bikeOptions")
					.getResultList();
			bikeOptions = coerceUntypedList(BikeOption.class, untypedResultList);
		}
		return bikeOptions;
	}

	/**
	 * @return the transferLimits
	 */
	public List<TransferLimit> getTransferLimits() {
		if (transferLimits == null) {
			List<?> untypedResultList = 
				entityManager.createQuery("from TransferLimit transferLimit")
					.getResultList();
			transferLimits = coerceUntypedList(TransferLimit.class, untypedResultList);
		}
		return transferLimits;
	}

	/**
	 * @return the transferTypes
	 */
	public List<TransferType> getTransferTypes() {
		if (transferTypes == null) {
			List<?> untypedResultList = 
				entityManager.createQuery("from TransferType transferType")
					.getResultList();
			transferTypes = coerceUntypedList(TransferType.class, untypedResultList);
		}
		return transferTypes;
	}

	private final static <T> List<T> coerceUntypedList(Class<T> clazz, List<?> untypedList) {
		List<T> result = new ArrayList<T>();
		for (Object obj : untypedList) {
			result.add(clazz.cast(obj));
		}
		return result;
	}
}
