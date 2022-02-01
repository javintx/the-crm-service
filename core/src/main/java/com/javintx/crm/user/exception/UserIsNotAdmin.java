package com.javintx.crm.user.exception;

import static java.lang.String.format;

public class UserIsNotAdmin extends RuntimeException {
		public UserIsNotAdmin(final String userId) {
				super(format("User identified with %s is not an admin", userId));
		}
}
