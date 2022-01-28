package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.usecase.ListAllCustomers;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerUseCaseHandler {

	private final ListAllCustomers listAllCustomers;

	public CustomerUseCaseHandler(ListAllCustomers listAllCustomers) {
		this.listAllCustomers = listAllCustomers;
	}

	public List<CustomerResponse> get() {
		List<Customer> customersList = listAllCustomers.get();
		return customersList.stream().map(CustomerResponse::from).collect(Collectors.toList());
	}
}
