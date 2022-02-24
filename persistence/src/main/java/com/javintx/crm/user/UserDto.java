package com.javintx.crm.user;

import com.javintx.crm.domain.User;

import java.util.Objects;

public class UserDto {
		public final String id;
		public final String name;
		public final String surname;
		public final boolean isAdmin;

		public UserDto(String id, String name, String surname, boolean isAdmin) {
				this.id = id;
				this.name = name;
				this.surname = surname;
				this.isAdmin = isAdmin;
		}

		public static UserDto from(User user) {
				return new UserDto(user.identifier(), user.name(), user.surname(), user.isAdmin());
		}

		public User toDomain() {
				return User.builder().withId(id).withName(name).withSurname(surname).thatIsAdmin(isAdmin).build();
		}

		@Override
		public boolean equals(Object o) {
				if (this == o) return true;
				if (o == null || getClass() != o.getClass()) return false;
				UserDto userDto = (UserDto) o;
				return isAdmin == userDto.isAdmin && id.equals(userDto.id) && name.equals(userDto.name) && surname.equals(userDto.surname);
		}

		@Override
		public int hashCode() {
				return Objects.hash(id, name, surname, isAdmin);
		}
}
