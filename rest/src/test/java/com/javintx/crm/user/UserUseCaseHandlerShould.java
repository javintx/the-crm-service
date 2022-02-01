package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.user.exception.UserNotValid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
		@Mock
		private IsAdminUser isAdminUserMock;

		private UserUseCaseHandler userUseCaseHandler;

		@BeforeEach
		public void setUp() {
				userUseCaseHandler = new UserUseCaseHandler(listAllUsersMock, createNewUserMock, updateUserMock, deleteUserMock, isAdminUserMock);
		}

		@Test
		void return_empty_user_list_if_there_are_no_users() {
				assertThat(userUseCaseHandler.get()).isEmpty();
		}

		@Test
		void return_user_list_if_there_are_users() {
				User user = User.buildUser().withId("id").withName("name").withSurname("surname").build();
				when(listAllUsersMock.get()).thenReturn(List.of(user));

				List<UserResponse> userResponseList = userUseCaseHandler.get();

				assertThat(userResponseList).isNotEmpty();
				assertThat(userResponseList.get(0)).isInstanceOf(UserResponse.class);
				assertThat(userResponseList).containsExactly(UserResponse.from(user));
		}

		@Test
		void return_new_user_created() {
				UserRequest userRequest = new UserRequest();
				userRequest.setId("id");
				userRequest.setName("name");
				userRequest.setSurname("name");

				User userExpected = User.buildUser().withId("id").withName("name").withSurname("surname").build();
				when(createNewUserMock.with(any(User.class))).thenReturn(userExpected);

				UserResponse userResponse = userUseCaseHandler.create(userRequest);

				assertThat(userResponse).isEqualTo(UserResponse.from(userExpected));
		}

		@Test
		void return_user_updated_when_updates_user_and_exists() {
				UserRequest userRequest = new UserRequest();
				userRequest.setId("id");
				userRequest.setName("name");
				userRequest.setSurname("name");

				User userExpected = User.buildUser().withId("id").withName("name").withSurname("surname").build();
				when(updateUserMock.update(any(User.class))).thenReturn(userExpected);

				UserResponse userResponse = userUseCaseHandler.update(userRequest);

				assertThat(userResponse).isEqualTo(UserResponse.from(userExpected));
		}

		@Test
		void verify_deleted_user() {
				userUseCaseHandler.delete("userId");
				verify(deleteUserMock).delete("userId");
		}

		@Test
		void throw_exception_when_create_new_user_without_mandatory_field() {
				UserRequest userRequest = new UserRequest();
				userRequest.setId(null);
				userRequest.setName("name");
				userRequest.setSurname("surname");

				assertThatThrownBy(
						() -> userUseCaseHandler.create(userRequest)
				).isExactlyInstanceOf(UserNotValid.class);

				userRequest.setId("id");
				userRequest.setName(null);
				assertThatThrownBy(
						() -> userUseCaseHandler.create(userRequest)
				).isExactlyInstanceOf(UserNotValid.class);

				userRequest.setName("name");
				userRequest.setSurname(null);
				assertThatThrownBy(
						() -> userUseCaseHandler.create(userRequest)
				).isExactlyInstanceOf(UserNotValid.class);
		}

		@Test
		void verify_is_admin_user() {
				userUseCaseHandler.isAdmin("userId");
				verify(isAdminUserMock).isAdmin("userId");
		}
}
