package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserWriter;

public class UserWriterInMemoryAdapter implements UserWriter {
		private final InMemoryStorage inMemoryStorage;

		public UserWriterInMemoryAdapter(final InMemoryStorage inMemoryStorage) {
				this.inMemoryStorage = inMemoryStorage;
		}

		@Override
		public User writes(final User user) {
				var userDto = UserDto.from(user);
				this.inMemoryStorage.users().put(userDto.id, userDto);
				return userDto.toDomain();
		}
}
