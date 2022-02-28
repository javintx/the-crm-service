package com.javintx.crm.user.exception;

import static java.lang.String.format;

public class UserAlreadyExists extends RuntimeException {

		public UserAlreadyExists(final String userId) {
				super(format("User identified with '%s' already exists", userId));
		}

}
