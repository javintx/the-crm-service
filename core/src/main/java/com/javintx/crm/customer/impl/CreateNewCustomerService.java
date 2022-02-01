package com.javintx.crm.customer.impl;

import com.javintx.crm.customer.CreateNewCustomer;
import com.javintx.crm.customer.exception.CustomerAlreadyExists;
import com.javintx.crm.domain.Customer;
import com.javintx.crm.port.out.customer.CustomerReader;
import com.javintx.crm.port.out.customer.CustomerWriter;

public class CreateNewCustomerService implements CreateNewCustomer {

		private final CustomerWriter customerWriter;
		private final CustomerReader customerReader;

		public CreateNewCustomerService(final CustomerWriter customerWriter, final CustomerReader customerReader) {
				this.customerWriter = customerWriter;
				this.customerReader = customerReader;
		}

		@Override
		public Customer with(final Customer customer) {
				checkIfExists(customer.identifier());
				return customerWriter.writes(customer);
		}

		private void checkIfExists(final String customerId) {
				if (customerReader.readAll().stream().anyMatch(customer -> customer.identifier().equals(customerId))) {
						throw new CustomerAlreadyExists(customerId);
				}
		}
}
