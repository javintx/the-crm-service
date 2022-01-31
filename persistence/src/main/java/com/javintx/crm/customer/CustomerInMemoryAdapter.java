package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.port.out.CustomerReader;
import com.javintx.crm.port.out.CustomerUpdater;
import com.javintx.crm.port.out.CustomerWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerInMemoryAdapter implements CustomerReader, CustomerWriter, CustomerUpdater {
	private final Map<String, Customer> customers;

	public CustomerInMemoryAdapter() {
		customers = new HashMap<>();
	}

	@Override
	public List<Customer> readAll() {
		return new ArrayList<>(customers.values());
	}

	@Override
	public Customer writes(final Customer customer) {
		customers.put(customer.identifier(), customer);
		return customer;
	}

	@Override
	public Customer update(final Customer customer) {
		return customers.replace(customer.identifier(), customer);
	}
}
