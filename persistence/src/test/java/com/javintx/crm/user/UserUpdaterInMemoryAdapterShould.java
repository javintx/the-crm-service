package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserUpdaterInMemoryAdapterShould {
		private UserUpdater userUpdater;

		@BeforeEach
		public void setUp() {
				userUpdater = new UserUpdaterInMemoryAdapter();
		}

		@Test
		void return_user_updated() {
				var user = new User("identifier", "name", "surname", false);
				var userDto = UserDto.from(user);

				InMemoryStorage.INSTANCE.users().put(userDto.identifier(), userDto);

				assertThat(userUpdater.update(user)).isEqualTo(user);
		}

}
