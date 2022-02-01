package com.javintx.crm.customer.impl;

import com.javintx.crm.port.out.customer.CustomerDeleter;
import com.javintx.crm.port.out.customer.CustomerReader;
import com.javintx.crm.customer.DeleteCustomer;
import com.javintx.crm.customer.exception.CustomerNotExists;

public class DeleteCustomerService implements DeleteCustomer {

		private final CustomerReader customerReader;
		private final CustomerDeleter customerDeleter;

		public DeleteCustomerService(final CustomerReader customerReader, final CustomerDeleter customerDeleter) {
				this.customerReader = customerReader;
				this.customerDeleter = customerDeleter;
		}

		@Override
		public void delete(final String customerId) {
				checkIfExists(customerId);
				customerDeleter.delete(customerId);
		}

		private void checkIfExists(final String customerId) {
				if (customerReader.readAll().stream().noneMatch(customer -> customer.identifier().equals(customerId))) {
						throw new CustomerNotExists(customerId);
				}
		}
}
