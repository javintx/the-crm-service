package com.javintx.crm.user;

import com.javintx.crm.domain.User;

import java.util.Objects;

public class UserResponse {
		public final String id;
		public final String name;
		public final String surname;

		private UserResponse(final String id, final String name, final String surname) {
				this.id = id;
				this.name = name;
				this.surname = surname;
		}

		public static UserResponse from(final User user) {
				return new UserResponse(user.identifier(), user.name(), user.surname());
		}

		@Override
		public boolean equals(Object o) {
				if (this == o) return true;
				if (o == null || getClass() != o.getClass()) return false;
				UserResponse that = (UserResponse) o;
				return id.equals(that.id) && name.equals(that.name) && surname.equals(that.surname);
		}

		@Override
		public int hashCode() {
				return Objects.hash(id, name, surname);
		}
}
