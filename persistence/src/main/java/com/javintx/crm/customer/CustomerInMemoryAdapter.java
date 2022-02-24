package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.exception.CommandCannotBeExecuted;
import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerDeleter;
import com.javintx.crm.port.out.customer.CustomerReader;
import com.javintx.crm.port.out.customer.CustomerUpdater;
import com.javintx.crm.port.out.customer.CustomerWriter;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class CustomerInMemoryAdapter implements CustomerReader, CustomerWriter, CustomerUpdater, CustomerDeleter {
		private final InMemoryStorage inMemoryStorage;

		public CustomerInMemoryAdapter(InMemoryStorage inMemoryStorage) {
				this.inMemoryStorage = inMemoryStorage;
		}

		@Override
		public List<Customer> readAll() {
				return this.inMemoryStorage.customers().values().stream().map(CustomerDto::toDomain).collect(Collectors.toList());
		}

		@Override
		public Customer writes(final Customer customer) {
				CustomerDto customerDto = CustomerDto.from(customer);
				this.inMemoryStorage.customers().put(customerDto.id, customerDto);
				return customer;
		}

		@Override
		public Customer update(final Customer customer) {
				CustomerDto customerDto = CustomerDto.from(customer);
				if (this.inMemoryStorage.customers().containsKey(customerDto.id)) {
						return this.inMemoryStorage.customers().replace(customerDto.id, customerDto).toDomain();
				} else {
						throw new CommandCannotBeExecuted(format("Customer %s does not exists", customerDto.id));
				}
		}

		@Override
		public void delete(final String customerId) {
				this.inMemoryStorage.customers().remove(customerId);
		}
}
