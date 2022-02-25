package com.javintx.crm.customer;

import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerDeleter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerDeleterInMemoryAdapterShould {
		private CustomerDeleter customerDeleter;

		@BeforeEach
		public void setUp() {
				customerDeleter = new CustomerDeleterInMemoryAdapter();
		}

		@Test
		void delete_customer() {
				final var CUSTOMER_ID = "customerId";

				InMemoryStorage.INSTANCE.customers().put(CUSTOMER_ID, new CustomerDto(CUSTOMER_ID, "name", "surname", "photo", "userReference"));

				assertDoesNotThrow(() -> customerDeleter.delete(CUSTOMER_ID));
				assertTrue(InMemoryStorage.INSTANCE.customers().isEmpty());
		}

}
