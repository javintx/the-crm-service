package com.javintx.crm.customer.exception;

import static java.lang.String.format;

public class CustomerAlreadyExists extends RuntimeException {

		public CustomerAlreadyExists(final String customerId) {
				super(format("Customer identified with %s already exists", customerId));
		}

}
