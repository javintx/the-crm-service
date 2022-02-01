package com.javintx.crm.customer.exception;

import com.javintx.crm.domain.Customer;

import static java.lang.String.format;

public class CustomerNotValid extends RuntimeException {
		public CustomerNotValid(final Customer customer) {
				super(format("Invalid customer because missing fields are required: %s", customer.toString()));
		}
}
