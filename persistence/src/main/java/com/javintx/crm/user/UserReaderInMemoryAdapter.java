package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserReader;

import java.util.List;

public class UserReaderInMemoryAdapter implements UserReader {

		@Override
		public List<User> readAll() {
				return InMemoryStorage.INSTANCE.users().values().stream().map(UserDto::toDomain).toList();
		}
}
