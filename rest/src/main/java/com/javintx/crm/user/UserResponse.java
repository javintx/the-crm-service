package com.javintx.crm.user;

import com.javintx.crm.domain.User;

public record UserResponse(String identifier, String name, String surname) {

		public static UserResponse from(final User user) {
				return new UserResponse(user.identifier(), user.name(), user.surname());
		}

}
