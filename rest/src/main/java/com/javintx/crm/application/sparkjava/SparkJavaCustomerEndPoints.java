package com.javintx.crm.application.sparkjava;

import static com.javintx.crm.customer.CustomerEndPoints.BindNames.CUSTOMER_ID;
import static com.javintx.crm.customer.CustomerEndPoints.CUSTOMERS_BASE_URL;

public final class SparkJavaCustomerEndPoints {
		public static final String LIST_ALL_CUSTOMERS = CUSTOMERS_BASE_URL;
		public static final String CREATE_NEW_CUSTOMER = CUSTOMERS_BASE_URL;
		public static final String CUSTOMER_ID_PATH_VARIABLE = ":" + CUSTOMER_ID;
		public static final String UPDATE_CUSTOMER = CUSTOMERS_BASE_URL + "/" + CUSTOMER_ID_PATH_VARIABLE;
		public static final String DELETE_CUSTOMER = CUSTOMERS_BASE_URL + "/" + CUSTOMER_ID_PATH_VARIABLE;
		private SparkJavaCustomerEndPoints() {
		}

}
