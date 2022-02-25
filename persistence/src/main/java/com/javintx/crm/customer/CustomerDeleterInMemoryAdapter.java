package com.javintx.crm.customer;

import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerDeleter;

public class CustomerDeleterInMemoryAdapter implements CustomerDeleter {

		@Override
		public void delete(final String customerId) {
				InMemoryStorage.INSTANCE.customers().remove(customerId);
		}
}
