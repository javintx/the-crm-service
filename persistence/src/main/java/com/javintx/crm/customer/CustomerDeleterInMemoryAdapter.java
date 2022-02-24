package com.javintx.crm.customer;

import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerDeleter;

public class CustomerDeleterInMemoryAdapter implements CustomerDeleter {
		private final InMemoryStorage inMemoryStorage;

		public CustomerDeleterInMemoryAdapter(final InMemoryStorage inMemoryStorage) {
				this.inMemoryStorage = inMemoryStorage;
		}

		@Override
		public void delete(final String customerId) {
				this.inMemoryStorage.customers().remove(customerId);
		}
}
