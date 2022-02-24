package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.exception.CommandCannotBeExecuted;
import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerUpdater;

import static java.lang.String.format;

public class CustomerUpdaterInMemoryAdapter implements CustomerUpdater {
		private final InMemoryStorage inMemoryStorage;

		public CustomerUpdaterInMemoryAdapter(final InMemoryStorage inMemoryStorage) {
				this.inMemoryStorage = inMemoryStorage;
		}

		@Override
		public Customer update(final Customer customer) {
				var customerDto = CustomerDto.from(customer);
				if (this.inMemoryStorage.customers().containsKey(customerDto.identifier())) {
						return this.inMemoryStorage.customers().replace(customerDto.identifier(), customerDto).toDomain();
				} else {
						throw new CommandCannotBeExecuted(format("Customer %s does not exists", customerDto.identifier()));
				}
		}
}
