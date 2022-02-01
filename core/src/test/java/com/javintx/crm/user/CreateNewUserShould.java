package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.port.out.user.UserReader;
import com.javintx.crm.port.out.user.UserWriter;
import com.javintx.crm.user.exception.UserAlreadyExists;
import com.javintx.crm.user.exception.UserNotValid;
import com.javintx.crm.user.impl.CreateNewUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateNewUserShould {

		@Mock
		private UserWriter userWriterMocked;
		@Mock
		private UserReader userReaderMocked;

		private CreateNewUser createNewUser;

		@BeforeEach
		void setUp() {
				createNewUser = new CreateNewUserService(userWriterMocked, userReaderMocked);
		}

		@Test
		void create_new_user() {
				User userToCreate = new User("id", "name", "surname");
				User userExpected = new User("id", "name", "surname");

				when(userWriterMocked.writes(any(User.class))).thenReturn(userExpected);

				User userCreated = createNewUser.with(userToCreate);

				assertThat(userCreated).isEqualTo(userExpected);
		}

		@Test
		void throw_exception_when_create_new_user_that_exists() {
				User userToCreate = new User("id", "name", "surname");
				User user = new User("id", "name", "surname");

				when(userReaderMocked.readAll()).thenReturn(List.of(user));

				assertThatThrownBy(() -> createNewUser.with(userToCreate)).isExactlyInstanceOf(UserAlreadyExists.class);
		}

}