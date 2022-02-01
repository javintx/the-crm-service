package com.javintx.crm.user.impl;

import com.javintx.crm.domain.User;
import com.javintx.crm.port.out.user.UserReader;
import com.javintx.crm.user.ListAllUsers;

import java.util.List;

public class ListAllUsersService implements ListAllUsers {

		private final UserReader userReader;

		public ListAllUsersService(final UserReader userReader) {
				this.userReader = userReader;
		}

		@Override
		public List<User> get() {
				return userReader.readAll();
		}
}
