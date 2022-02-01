package com.javintx.crm.user.exception;

import com.javintx.crm.domain.User;

import static java.lang.String.format;

public class UserNotValid extends RuntimeException {
		public UserNotValid(final User user) {
				super(format("Invalid user because missing fields are required: %s", user.toString()));
		}
}
