package com.javintx.crm.customer;

public final class CustomerEndPoints {
		public static final String CUSTOMERS_BASE_URL = "/customers";
		public static final String CUSTOMERS_PATH = "/*";

		private CustomerEndPoints() {
		}

		public static final class BindNames {
				public static final String CUSTOMER_ID = "customerIdentifier";

				private BindNames() {
				}
		}
}
