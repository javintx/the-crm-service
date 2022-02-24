package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerReader;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerReaderInMemoryAdapter implements CustomerReader {
		private final InMemoryStorage inMemoryStorage;

		public CustomerReaderInMemoryAdapter(final InMemoryStorage inMemoryStorage) {
				this.inMemoryStorage = inMemoryStorage;
		}

		@Override
		public List<Customer> readAll() {
				return this.inMemoryStorage.customers().values().stream().map(CustomerDto::toDomain).collect(Collectors.toList());
		}
}
