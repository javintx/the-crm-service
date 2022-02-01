package com.javintx.crm.user;

import com.javintx.crm.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserUseCaseHandler {
		private final ListAllUsers listAllUsers;
		private final CreateNewUser createNewUser;
		private final UpdateUser updateUser;
		private final DeleteUser deleteUser;

		public UserUseCaseHandler(final ListAllUsers listAllUsers,
																												final CreateNewUser createNewUser,
																												final UpdateUser updateUser,
																												final DeleteUser deleteUser) {
				this.listAllUsers = listAllUsers;
				this.createNewUser = createNewUser;
				this.updateUser = updateUser;
				this.deleteUser = deleteUser;
		}

		public List<UserResponse> get() {
				List<User> usersList = listAllUsers.get();
				return usersList.stream().map(UserResponse::from).collect(Collectors.toList());
		}

		public UserResponse create(final UserRequest user) {
				return UserResponse.from(createNewUser.with(user.toDomain()));
		}

		public UserResponse update(final UserRequest user) {
				return UserResponse.from(updateUser.update(user.toDomain()));
		}

		public void delete(final String userId) {
				deleteUser.delete(userId);
		}
}
