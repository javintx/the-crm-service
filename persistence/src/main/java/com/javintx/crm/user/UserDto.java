package com.javintx.crm.user;

import com.javintx.crm.domain.User;

public record UserDto(String identifier, String name, String surname, boolean isAdmin) {

		public static UserDto from(User user) {
				return new UserDto(user.identifier(), user.name(), user.surname(), user.isAdmin());
		}

		public User toDomain() {
				return new User(identifier, name, surname, isAdmin);
		}

}
