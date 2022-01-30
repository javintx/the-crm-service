package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;

public class CustomerRequest {
	public Customer toDomain() {
		return new Customer();
	}
}
