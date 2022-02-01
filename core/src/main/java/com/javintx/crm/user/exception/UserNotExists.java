package com.javintx.crm.user.exception;

import static java.lang.String.format;

public class UserNotExists extends RuntimeException {

		public UserNotExists(final String userId) {
				super(format("User identified with %s does not exists", userId));
		}

}
