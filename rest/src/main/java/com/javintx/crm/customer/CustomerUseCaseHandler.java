package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.usecase.CreateNewCustomer;
import com.javintx.crm.usecase.ListAllCustomers;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerUseCaseHandler {

	private final ListAllCustomers listAllCustomers;
	private final CreateNewCustomer createNewCustomer;

	public CustomerUseCaseHandler(final ListAllCustomers listAllCustomers, final CreateNewCustomer createNewCustomer) {
		this.listAllCustomers = listAllCustomers;
		this.createNewCustomer = createNewCustomer;
	}

	public List<CustomerResponse> get() {
		List<Customer> customersList = listAllCustomers.get();
		return customersList.stream().map(CustomerResponse::from).collect(Collectors.toList());
	}

	public CustomerResponse create(final CustomerRequest customer) {
		return CustomerResponse.from(createNewCustomer.with(customer.toDomain()));
	}
}
