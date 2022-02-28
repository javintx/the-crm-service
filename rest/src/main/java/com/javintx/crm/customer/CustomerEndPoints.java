package com.javintx.crm.customer;

import static com.javintx.crm.customer.CustomerEndPoints.BindNames.CUSTOMER_ID;

public final class CustomerEndPoints {
		public static final String LIST_ALL_CUSTOMERS = "/customer/all";
		public static final String CREATE_NEW_CUSTOMER = "/customer/create";
		public static final String UPDATE_CUSTOMER = "/customer/update";
		public static final String DELETE_CUSTOMER = "/customer/delete/:" + CUSTOMER_ID;

		private CustomerEndPoints() {
		}

		public static final class BindNames {
				public static final String CUSTOMER_ID = "customerIdentifier";

				private BindNames() {
				}
		}
}
