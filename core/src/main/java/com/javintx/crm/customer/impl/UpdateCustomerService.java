package com.javintx.crm.customer.impl;

import com.javintx.crm.customer.UpdateCustomer;
import com.javintx.crm.customer.exception.CustomerNotExists;
import com.javintx.crm.domain.Customer;
import com.javintx.crm.port.out.customer.CustomerReader;
import com.javintx.crm.port.out.customer.CustomerUpdater;

public class UpdateCustomerService implements UpdateCustomer {

		private final CustomerReader customerReader;
		private final CustomerUpdater customerUpdater;

		public UpdateCustomerService(final CustomerReader customerReader, final CustomerUpdater customerUpdater) {
				this.customerReader = customerReader;
				this.customerUpdater = customerUpdater;
		}

		@Override
		public Customer update(final Customer customer) {
				checkIfExists(customer);
				return customerUpdater.update(customer);
		}

		private void checkIfExists(final Customer customer) {
				if (customerReader.readAll().stream().noneMatch(readCustomer -> readCustomer.identifier().equals(customer.identifier()))) {
						throw new CustomerNotExists(customer.identifier());
				}
		}
}
