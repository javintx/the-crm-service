package com.javintx.crm.usecase.impl;

import com.javintx.crm.port.out.CustomerDeleter;
import com.javintx.crm.port.out.CustomerReader;
import com.javintx.crm.usecase.DeleteCustomer;
import com.javintx.crm.usecase.exception.CustomerNotExists;

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
