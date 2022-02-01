package com.javintx.crm.user.impl;

import com.javintx.crm.domain.User;
import com.javintx.crm.port.out.user.UserReader;
import com.javintx.crm.port.out.user.UserWriter;
import com.javintx.crm.user.CreateNewUser;
import com.javintx.crm.user.exception.UserAlreadyExists;

public class CreateNewUserService implements CreateNewUser {

		private final UserWriter userWriter;
		private final UserReader userReader;

		public CreateNewUserService(final UserWriter userWriter, final UserReader userReader) {
				this.userWriter = userWriter;
				this.userReader = userReader;
		}

		@Override
		public User with(final User user) {
				checkIfExists(user.identifier());
				return userWriter.writes(user);
		}

		private void checkIfExists(final String userId) {
				if (userReader.readAll().stream().anyMatch(user -> user.identifier().equals(userId))) {
						throw new UserAlreadyExists(userId);
				}
		}
}
