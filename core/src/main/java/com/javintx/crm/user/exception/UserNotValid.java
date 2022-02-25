package com.javintx.crm.user.exception;

import static java.lang.String.format;

public class UserNotValid extends RuntimeException {
		public UserNotValid(final String userField) {
				super(format("Invalid user because missing fields are required: %s", userField));
		}
}
