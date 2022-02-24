package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerWriter;

public class CustomerWriterInMemoryAdapter implements CustomerWriter {
		private final InMemoryStorage inMemoryStorage;

		public CustomerWriterInMemoryAdapter(final InMemoryStorage inMemoryStorage) {
				this.inMemoryStorage = inMemoryStorage;
		}

		@Override
		public Customer writes(final Customer customer) {
				var customerDto = CustomerDto.from(customer);
				this.inMemoryStorage.customers().put(customerDto.identifier(), customerDto);
				return customer;
		}
}
