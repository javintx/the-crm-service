package com.javintx.crm.in_memory_storage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InMemoryStorageShould {

		@Test
		void ensure_customers_storage() {
				assertNotNull(InMemoryStorage.INSTANCE.customers());
		}

		@Test
		void ensure_users_storage() {
				assertNotNull(InMemoryStorage.INSTANCE.users());
		}
}
