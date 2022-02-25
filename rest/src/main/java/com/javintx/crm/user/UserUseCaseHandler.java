package com.javintx.crm.user;

import java.util.List;

public class UserUseCaseHandler {
		private final ListAllUsers listAllUsers;
		private final CreateNewUser createNewUser;
		private final UpdateUser updateUser;
		private final DeleteUser deleteUser;
		private final IsAdminUser isAdminUser;

		public UserUseCaseHandler(final ListAllUsers listAllUsers,
																												final CreateNewUser createNewUser,
																												final UpdateUser updateUser,
																												final DeleteUser deleteUser,
																												final IsAdminUser isAdminUser) {
				this.listAllUsers = listAllUsers;
				this.createNewUser = createNewUser;
				this.updateUser = updateUser;
				this.deleteUser = deleteUser;
				this.isAdminUser = isAdminUser;
		}

		public List<UserResponse> get() {
				return listAllUsers.get().stream().map(UserResponse::from).toList();
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

		public void isAdmin(final String userId) {
				isAdminUser.isAdmin(userId);
		}
}
