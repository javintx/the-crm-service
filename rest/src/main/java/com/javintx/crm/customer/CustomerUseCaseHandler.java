package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.usecase.ListAllCustomers;

import java.util.List;

public class CustomerUseCaseHandler {

	private final ListAllCustomers listAllCustomers;

	public CustomerUseCaseHandler(ListAllCustomers listAllCustomers) {
		this.listAllCustomers = listAllCustomers;
	}

	public List<Customer> get() {
		return listAllCustomers.get();
	}
}
