package com.javintx.crm.domain;

import com.javintx.crm.user.exception.UserNotValid;

public record User(String identifier, String name, String surname, boolean isAdmin) {
		public User(String identifier, String name, String surname, boolean isAdmin) {
				this.identifier = verify(identifier, "identifier");
				this.name = verify(name, "name");
				this.surname = verify(surname, "surname");
				this.isAdmin = isAdmin;
		}

		private String verify(final String value, final String field) {
				if (value == null) {
						throw new UserNotValid(field);
				}
				return value;
		}

}
