package com.javintx.crm.user;

import com.javintx.crm.domain.User;

public record UserRequest(String identifier, String name, String surname, boolean isAdmin) {

		public User toDomain() {
				return new User(identifier, name, surname, isAdmin);
		}
}
