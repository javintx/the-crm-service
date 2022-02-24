package com.javintx.crm.user;

import com.javintx.crm.domain.User;

public class UserRequest {
		private String id;
		private String name;
		private String surname;
		private boolean isAdmin;

		public void setId(String id) {
				this.id = id;
		}

		public void setName(String name) {
				this.name = name;
		}

		public void setSurname(String surname) {
				this.surname = surname;
		}

		public void setIsAdmin(boolean isAdmin) {
				this.isAdmin = isAdmin;
		}

		public User toDomain() {
				return User.builder()
						.withId(id)
						.withName(name)
						.withSurname(surname)
						.thatIsAdmin(isAdmin)
						.build();
		}
}
