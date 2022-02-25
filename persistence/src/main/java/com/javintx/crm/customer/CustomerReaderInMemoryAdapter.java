package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerReader;

import java.util.List;

public class CustomerReaderInMemoryAdapter implements CustomerReader {

		@Override
		public List<Customer> readAll() {
				return InMemoryStorage.INSTANCE.customers().values().stream().map(CustomerDto::toDomain).toList();
		}
}
