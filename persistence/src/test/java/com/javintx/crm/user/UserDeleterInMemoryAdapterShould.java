package com.javintx.crm.user;

import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserDeleter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDeleterInMemoryAdapterShould {
		private UserDeleter userDeleter;

		@BeforeEach
		public void setUp() {
				userDeleter = new UserDeleterInMemoryAdapter();
		}

		@Test
		void delete_user() {
				final var USER_ID = "userId";

				InMemoryStorage.INSTANCE.users().put(USER_ID, new UserDto(USER_ID, "name", "surname", false));

				assertDoesNotThrow(() -> userDeleter.delete(USER_ID));
				assertTrue(InMemoryStorage.INSTANCE.users().isEmpty());
		}

}
