package com.javintx.crm.user;

import com.javintx.crm.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserUseCaseHandler {
		private final ListAllUsers listAllUsers;

		public UserUseCaseHandler(final ListAllUsers listAllUsers) {
				this.listAllUsers = listAllUsers;
		}

		public List<UserResponse> get() {
				List<User> usersList = listAllUsers.get();
				return usersList.stream().map(UserResponse::from).collect(Collectors.toList());
		}
}
