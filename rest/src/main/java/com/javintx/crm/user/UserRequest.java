package com.javintx.crm.user;

import com.javintx.crm.domain.User;

public class UserRequest {
		private String id;
		private String name;
		private String surname;

		public void setId(String id) {
				this.id = id;
		}

		public void setName(String name) {
				this.name = name;
		}

		public void setSurname(String surname) {
				this.surname = surname;
		}

		public User toDomain() {
				return new User(id, name, surname);
		}
}
