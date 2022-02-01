package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseHandlerShould {

		@Mock
		private ListAllUsers listAllUsersMock;
		@Mock
		private CreateNewUser createNewUserMock;
		@Mock
		private UpdateUser updateUserMock;
		@Mock
		private DeleteUser deleteUserMock;

		private UserUseCaseHandler userUseCaseHandler;

		@BeforeEach
		public void setUp() {
				userUseCaseHandler = new UserUseCaseHandler(listAllUsersMock, createNewUserMock, updateUserMock, deleteUserMock);
		}

		@Test
		void return_empty_user_list_if_there_are_no_users() {
				assertThat(userUseCaseHandler.get()).isEmpty();
		}

		@Test
		void return_user_list_if_there_are_users() {
				User userMock = new User("id", "name", "surname");
				when(listAllUsersMock.get()).thenReturn(List.of(userMock));

				List<UserResponse> userResponseList = userUseCaseHandler.get();

				assertThat(userResponseList).isNotEmpty();
				assertThat(userResponseList.get(0)).isInstanceOf(UserResponse.class);
				assertThat(userResponseList).containsExactly(UserResponse.from(userMock));
		}

		@Test
		void return_new_user_created() {
				UserRequest userRequestMock = new UserRequest();
				User userExpected = new User("id", "name", "surname");
				when(createNewUserMock.with(any(User.class))).thenReturn(userExpected);

				UserResponse userResponse = userUseCaseHandler.create(userRequestMock);

				assertThat(userResponse).isEqualTo(UserResponse.from(userExpected));
		}

		@Test
		void return_user_updated_when_updates_user_and_exists() {
				UserRequest userRequestMock = new UserRequest();
				userRequestMock.setId("id");
				userRequestMock.setName("name");
				userRequestMock.setSurname("name");

				User userExpected = new User("id", "name", "surname");
				when(updateUserMock.update(any(User.class))).thenReturn(userExpected);

				UserResponse userResponse = userUseCaseHandler.update(userRequestMock);

				assertThat(userResponse).isEqualTo(UserResponse.from(userExpected));
		}

		@Test
		void verify_deleted_user() {
				userUseCaseHandler.delete("userId");
				verify(deleteUserMock).delete("userId");
		}
}
