package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.exception.CommandCannotBeExecuted;
import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerUpdater;

import static java.lang.String.format;

public class CustomerUpdaterInMemoryAdapter implements CustomerUpdater {

		@Override
		public Customer update(final Customer customer) {
				var customerDto = CustomerDto.from(customer);
				var newCustomerDto = InMemoryStorage.INSTANCE.customers().replace(customerDto.identifier(), customerDto);
				if (newCustomerDto == null) {
						throw new CommandCannotBeExecuted(format("Customer %s does not exists", customerDto.identifier()));
				} else {
						return newCustomerDto.toDomain();
				}
		}
}
