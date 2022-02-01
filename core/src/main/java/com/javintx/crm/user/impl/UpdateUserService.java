package com.javintx.crm.user.impl;

import com.javintx.crm.domain.User;
import com.javintx.crm.port.out.user.UserReader;
import com.javintx.crm.port.out.user.UserUpdater;
import com.javintx.crm.user.UpdateUser;
import com.javintx.crm.user.exception.UserNotExists;

public class UpdateUserService implements UpdateUser {

		private final UserReader userReader;
		private final UserUpdater userUpdater;

		public UpdateUserService(final UserReader userReader, final UserUpdater userUpdater) {
				this.userReader = userReader;
				this.userUpdater = userUpdater;
		}

		@Override
		public User update(final User user) {
				checkIfExists(user);
				return userUpdater.update(user);
		}

		private void checkIfExists(final User user) {
				if (!userReader.readAll().contains(user)) {
						throw new UserNotExists(user.identifier());
				}
		}
}
