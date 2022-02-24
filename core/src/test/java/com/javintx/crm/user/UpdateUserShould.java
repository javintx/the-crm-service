package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.port.out.user.UserReader;
import com.javintx.crm.port.out.user.UserUpdater;
import com.javintx.crm.user.exception.UserNotExists;
import com.javintx.crm.user.impl.UpdateUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserShould {

		@Mock
		private UserReader userReaderMock;
		@Mock
		private UserUpdater userUpdaterMock;

		private UpdateUser updateUser;

		@BeforeEach
		void setUp() {
				updateUser = new UpdateUserService(userReaderMock, userUpdaterMock);
		}

		@Test
		void return_update_user_if_user_exists() {
				User existingUser = User.builder().withId("id1").withName("name1").withSurname("surname1").build();
				User updatedUser = User.builder().withId("id2").withName("name2").withSurname("surname2").build();
				User userExpected = User.builder().withId("id2").withName("name2").withSurname("surname2").build();

				when(userReaderMock.readAll()).thenReturn(List.of(existingUser));
				when(userUpdaterMock.update(existingUser)).thenReturn(updatedUser);

				User returnedUser = updateUser.update(existingUser);

				assertThat(returnedUser).isEqualTo(userExpected);
				assertThat(returnedUser.identifier()).isEqualTo(userExpected.identifier());
				assertThat(returnedUser.name()).isEqualTo(userExpected.name());
				assertThat(returnedUser.surname()).isEqualTo(userExpected.surname());
		}

		@Test
		void throw_exception_if_user_not_exists() {
				User user = User.builder().withId("id").withName("name").withSurname("surname").build();

				when(userReaderMock.readAll()).thenReturn(Collections.emptyList());

				assertThatThrownBy(() -> updateUser.update(user)).isExactlyInstanceOf(UserNotExists.class);
		}
}
