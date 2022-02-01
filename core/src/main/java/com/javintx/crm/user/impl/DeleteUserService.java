package com.javintx.crm.user.impl;

import com.javintx.crm.port.out.user.UserDeleter;
import com.javintx.crm.port.out.user.UserReader;
import com.javintx.crm.user.DeleteUser;
import com.javintx.crm.user.exception.UserNotExists;

public class DeleteUserService implements DeleteUser {

		private final UserReader userReader;
		private final UserDeleter userDeleter;

		public DeleteUserService(final UserReader userReader, final UserDeleter userDeleter) {
				this.userReader = userReader;
				this.userDeleter = userDeleter;
		}

		@Override
		public void delete(final String userId) {
				checkIfExists(userId);
				userDeleter.delete(userId);
		}

		private void checkIfExists(final String userId) {
				if (userReader.readAll().stream().noneMatch(user -> user.identifier().equals(userId))) {
						throw new UserNotExists(userId);
				}
		}
}
