package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.port.out.user.UserDeleter;
import com.javintx.crm.port.out.user.UserReader;
import com.javintx.crm.user.exception.UserNotExists;
import com.javintx.crm.user.impl.DeleteUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteUserShould {
		@Mock
		private UserReader userReaderMock;
		@Mock
		private UserDeleter userDeleterMock;

		private DeleteUser deleteUser;

		@BeforeEach
		void setUp() {
				deleteUser = new DeleteUserService(userReaderMock, userDeleterMock);
		}

		@Test
		void delete_user_if_user_exists() {
				User existingUser = new User("identifier", "name", "surname", false);

				when(userReaderMock.readAll()).thenReturn(List.of(existingUser));

				deleteUser.delete("identifier");

				verify(userDeleterMock).delete("identifier");
		}

		@Test
		void throw_exception_if_user_not_exists() {
				when(userReaderMock.readAll()).thenReturn(Collections.emptyList());

				assertThatThrownBy(() -> deleteUser.delete("identifier")).isExactlyInstanceOf(UserNotExists.class);
		}
}
