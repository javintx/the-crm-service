package com.javintx.crm.usecase.exception;

import com.javintx.crm.domain.Customer;

import static java.lang.String.format;

public class CustomerNotExists extends RuntimeException {

	public CustomerNotExists(final Customer customer) {
		super(format("Customer identified with %s does not exists", customer.identifier()));
	}

}
