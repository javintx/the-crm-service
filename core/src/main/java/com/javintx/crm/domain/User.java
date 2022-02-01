package com.javintx.crm.domain;

import com.javintx.crm.user.exception.UserNotValid;

import java.util.Objects;

public final class User {
		private final String id;
		private final String name;
		private final String surname;
		private final boolean isAdmin;

		private User(String id, String name, String surname, boolean isAdmin) {
				this.id = id;
				this.name = name;
				this.surname = surname;
				this.isAdmin = isAdmin;
				verify();
		}

		public static UserBuilder buildUser() {
				return new UserBuilder(false);
		}

		public static UserBuilder buildAdmin() {
				return new UserBuilder(true);
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

		public boolean isAdmin() {
				return isAdmin;
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

		public static class UserBuilder {
				private final boolean isAdmin;
				private String id;
				private String name;
				private String surname;

				private UserBuilder(final boolean isAdmin) {
						this.isAdmin = isAdmin;
				}

				public UserBuilder withId(final String id) {
						this.id = id;
						return this;
				}

				public UserBuilder withName(final String name) {
						this.name = name;
						return this;
				}

				public UserBuilder withSurname(final String surname) {
						this.surname = surname;
						return this;
				}

				public User build() {
						return new User(id, name, surname, isAdmin);
				}
		}
}
