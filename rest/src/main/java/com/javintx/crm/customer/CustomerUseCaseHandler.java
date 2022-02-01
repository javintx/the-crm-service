package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.usecase.CreateNewCustomer;
import com.javintx.crm.usecase.DeleteCustomer;
import com.javintx.crm.usecase.ListAllCustomers;
import com.javintx.crm.usecase.UpdateCustomer;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerUseCaseHandler {

	private final ListAllCustomers listAllCustomers;
	private final CreateNewCustomer createNewCustomer;
	private final UpdateCustomer updateCustomer;
	private final DeleteCustomer deleteCustomer;

	public CustomerUseCaseHandler(
		final ListAllCustomers listAllCustomers,
		final CreateNewCustomer createNewCustomer,
		final UpdateCustomer updateCustomer,
		final DeleteCustomer deleteCustomer) {
		this.listAllCustomers = listAllCustomers;
		this.createNewCustomer = createNewCustomer;
		this.updateCustomer = updateCustomer;
		this.deleteCustomer = deleteCustomer;
	}

	public List<CustomerResponse> get() {
		List<Customer> customersList = listAllCustomers.get();
		return customersList.stream().map(CustomerResponse::from).collect(Collectors.toList());
	}

	public CustomerResponse create(final CustomerRequest customer) {
		return CustomerResponse.from(createNewCustomer.with(customer.toDomain()));
	}

	public CustomerResponse update(final CustomerRequest customer) {
		return CustomerResponse.from(updateCustomer.update(customer.toDomain()));
	}

	public void delete(final String customerId) {
		deleteCustomer.delete(customerId);
	}
}
