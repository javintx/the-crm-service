package com.javintx.crm.usecase.impl;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.port.out.CustomerWriter;
import com.javintx.crm.usecase.CreateNewCustomer;

public class CreateNewCustomerService implements CreateNewCustomer {

	private final CustomerWriter customerWriter;

	public CreateNewCustomerService(CustomerWriter customerWriter) {
		this.customerWriter = customerWriter;
	}

	@Override
	public Customer with(Customer customer) {
		return customerWriter.writes(customer);
	}
}
