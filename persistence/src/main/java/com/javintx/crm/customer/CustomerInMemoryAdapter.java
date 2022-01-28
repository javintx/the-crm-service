package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.port.out.CustomerReader;
import com.javintx.crm.port.out.CustomerWriter;

import java.util.ArrayList;
import java.util.List;

public class CustomerInMemoryAdapter implements CustomerReader, CustomerWriter {
	private final List<Customer> customers;

	public CustomerInMemoryAdapter() {
		customers = new ArrayList<>();
	}

	@Override
	public List<Customer> readAll() {
		return customers;
	}

	@Override
	public Customer writes(final Customer customer) {
		customers.add(customer);
		return customer;
	}
}
