package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserWriter;

public class UserWriterInMemoryAdapter implements UserWriter {

		@Override
		public User writes(final User user) {
				var userDto = UserDto.from(user);
				InMemoryStorage.INSTANCE.users().put(userDto.identifier(), userDto);
				return userDto.toDomain();
		}
}
