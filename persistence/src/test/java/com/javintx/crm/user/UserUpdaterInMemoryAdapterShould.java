package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserUpdaterInMemoryAdapterShould {
		private UserUpdater userUpdater;

		@Mock
		private InMemoryStorage inMemoryStorage;

		@BeforeEach
		public void setUp() {
				userUpdater = new UserUpdaterInMemoryAdapter(inMemoryStorage);
		}

		@Test
		void return_user_updated() {
				var user = new User("id", "name", "surname", false);
				var userDto = UserDto.from(user);

				var users = new HashMap<String, UserDto>(1);
				users.put(userDto.identifier(), userDto);

				when(inMemoryStorage.users()).thenReturn(users).thenReturn(users);

				assertThat(userUpdater.update(user)).isEqualTo(user);
				verify(inMemoryStorage, times(2)).users();
		}

}
