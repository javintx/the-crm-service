package com.javintx.crm.domain;

import com.javintx.crm.user.exception.UserNotValid;

public record User(String identifier, String name, String surname, boolean isAdmin) {
		public User(String identifier, String name, String surname, boolean isAdmin) {
				this.identifier = identifier;
				this.name = name;
				this.surname = surname;
				this.isAdmin = isAdmin;
				verify();
		}

		private void verify() {
				if (identifier == null || name == null || surname == null) {
						throw new UserNotValid(this);
				}
		}

}
