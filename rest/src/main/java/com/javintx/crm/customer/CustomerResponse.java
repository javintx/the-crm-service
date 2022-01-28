package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;

public class CustomerResponse {

	private CustomerResponse() {
	}

	public static CustomerResponse from(final Customer customer) {
		return new CustomerResponse();
	}
}
