package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserWriterInMemoryAdapterShould {
		private UserWriter userWriter;

		@BeforeEach
		public void setUp() {
				userWriter = new UserWriterInMemoryAdapter();
		}

		@Test
		void return_user_write() {
				var user = new User("identifier", "name", "surname", false);
				var userDto = UserDto.from(user);

				InMemoryStorage.INSTANCE.users().put(userDto.identifier(), userDto);

				assertThat(userWriter.writes(user)).isEqualTo(user);
		}

}
