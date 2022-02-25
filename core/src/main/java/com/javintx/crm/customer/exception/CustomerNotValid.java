package com.javintx.crm.customer.exception;

import static java.lang.String.format;

public class CustomerNotValid extends RuntimeException {
		public CustomerNotValid(final String customerField) {
				super(format("Invalid customer because missing fields are required: %s", customerField));
		}
}
