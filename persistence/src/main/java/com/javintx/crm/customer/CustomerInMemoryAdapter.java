package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.port.out.CustomerReader;

import java.util.ArrayList;
import java.util.List;

public class CustomerInMemoryAdapter implements CustomerReader {
	private final List<Customer> customers;

	public CustomerInMemoryAdapter() {
		customers = new ArrayList<>();
	}

	@Override
	public List<Customer> readAll() {
		return customers;
	}
}
