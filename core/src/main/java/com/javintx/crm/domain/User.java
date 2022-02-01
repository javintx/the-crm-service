package com.javintx.crm.domain;

import java.util.Objects;

public class User {
		private final String id;
		private final String name;
		private final String surname;

		public User(String id, String name, String surname) {
				this.id = id;
				this.name = name;
				this.surname = surname;
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
				return id.equals(user.id);
		}

		@Override
		public int hashCode() {
				return Objects.hash(id);
		}
}
