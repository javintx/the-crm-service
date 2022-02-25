package com.javintx.crm.user;

import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserReaderInMemoryAdapterShould {
		private UserReader userReader;

		@BeforeEach
		public void setUp() {
				userReader = new UserReaderInMemoryAdapter();
		}

		@Test
		void return_empty_user_list_if_there_are_no_users() {
				assertTrue(userReader.readAll().isEmpty());
		}

		@Test
		void return_user_list_when_have_users() {
				var userDto = new UserDto("identifier", "name", "surname", false);
				InMemoryStorage.INSTANCE.users().put(userDto.identifier(), userDto);

				assertFalse(userReader.readAll().isEmpty());
				assertThat(userReader.readAll().get(0)).isEqualTo(userDto.toDomain());
		}

}
