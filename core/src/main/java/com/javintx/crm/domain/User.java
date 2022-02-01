package com.javintx.crm.domain;

import com.javintx.crm.user.exception.UserNotValid;

import java.util.Objects;

public class User {
		private final String id;
		private final String name;
		private final String surname;

		public User(String id, String name, String surname) {
				this.id = id;
				this.name = name;
				this.surname = surname;
				verify();
		}

		private void verify() {
				if (id == null || name == null || surname == null) {
						throw new UserNotValid(this);
				}
		}

		public String identifier() {
				return id;
		}

		public String name() {
				return name;
		}

		public String surname() {
				return surname;
		}

		@Override
		public boolean equals(Object o) {
				if (this == o) return true;
				if (o == null || getClass() != o.getClass()) return false;
				User user = (User) o;
				return id.equals(user.id) && name.equals(user.name) && surname.equals(user.surname);
		}

		@Override
		public int hashCode() {
				return Objects.hash(id, name, surname);
		}

		@Override
		public String toString() {
				return "User{" +
						"id='" + id + '\'' +
						", name='" + name + '\'' +
						", surname='" + surname + '\'' +
						'}';
		}
}
