package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserReader;

import java.util.List;
import java.util.stream.Collectors;

public class UserReaderInMemoryAdapter implements UserReader {
		private final InMemoryStorage inMemoryStorage;

		public UserReaderInMemoryAdapter(final InMemoryStorage inMemoryStorage) {
				this.inMemoryStorage = inMemoryStorage;
		}

		@Override
		public List<User> readAll() {
				return this.inMemoryStorage.users().values().stream().map(UserDto::toDomain).toList();
		}
}
