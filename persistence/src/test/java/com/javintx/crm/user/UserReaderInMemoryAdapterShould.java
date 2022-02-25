package com.javintx.crm.user;

import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserReaderInMemoryAdapterShould {
		private UserReader userReader;

		@Mock
		private InMemoryStorage inMemoryStorage;

		@BeforeEach
		public void setUp() {
				userReader = new UserReaderInMemoryAdapter(inMemoryStorage);
		}

		@Test
		void return_empty_user_list_if_there_are_no_users() {
				assertThat(userReader.readAll()).isEmpty();
				verify(inMemoryStorage).users();
		}

		@Test
		void return_user_list_when_have_users() {
				var userDto = new UserDto("identifier", "name", "surname", false);
				var users = new HashMap<String, UserDto>();
				users.put(userDto.identifier(), userDto);

				when(inMemoryStorage.users()).thenReturn(users);

				assertThat(userReader.readAll().get(0)).isEqualTo(userDto.toDomain());
				verify(inMemoryStorage).users();
		}

}
