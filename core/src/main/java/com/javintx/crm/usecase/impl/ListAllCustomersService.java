package com.javintx.crm.usecase.impl;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.port.out.CustomerReader;
import com.javintx.crm.usecase.ListAllCustomers;

import java.util.List;

public class ListAllCustomersService implements ListAllCustomers {

	private final CustomerReader customerReader;

	public ListAllCustomersService(final CustomerReader customerReader) {
		this.customerReader = customerReader;
	}

	@Override
	public List<Customer> get() {
		return customerReader.getAllCustomers();
	}

}
