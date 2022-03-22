package com.javintx.crm.customer;

import java.util.List;

public class CustomerUseCaseHandler {

		private final ListAllCustomers listAllCustomers;
		private final CreateNewCustomer createNewCustomer;
		private final UpdateCustomer updateCustomer;
		private final DeleteCustomer deleteCustomer;

		public CustomerUseCaseHandler(final ListAllCustomers listAllCustomers,
																																final CreateNewCustomer createNewCustomer,
																																final UpdateCustomer updateCustomer,
																																final DeleteCustomer deleteCustomer) {
				this.listAllCustomers = listAllCustomers;
				this.createNewCustomer = createNewCustomer;
				this.updateCustomer = updateCustomer;
				this.deleteCustomer = deleteCustomer;
		}

		public List<CustomerResponse> get() {
				return listAllCustomers.get().stream().map(CustomerResponse::from).toList();
		}

		public CustomerResponse create(final CustomerRequest customer) {
				return CustomerResponse.from(createNewCustomer.with(customer.toDomain()));
		}

		public CustomerResponse update(String userId, final CustomerRequest customer) {
				return CustomerResponse.from(updateCustomer.update(customer.toDomain()));
		}

		public void delete(final String customerId) {
				deleteCustomer.delete(customerId);
		}
}
