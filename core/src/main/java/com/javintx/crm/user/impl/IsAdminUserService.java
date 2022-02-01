package com.javintx.crm.user.impl;

import com.javintx.crm.port.out.user.UserReader;
import com.javintx.crm.user.IsAdminUser;
import com.javintx.crm.user.exception.UserIsNotAdmin;

public class IsAdminUserService implements IsAdminUser {
		private final UserReader userReader;

		public IsAdminUserService(final UserReader userReader) {
				this.userReader = userReader;
		}

		@Override
		public void isAdmin(String userId) {
				if (userReader.readAll().stream().noneMatch(user -> user.identifier().equals(userId) && user.isAdmin())) {
						throw new UserIsNotAdmin(userId);
				}
		}
}
