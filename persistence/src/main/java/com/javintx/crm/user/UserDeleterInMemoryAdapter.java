package com.javintx.crm.user;

import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserDeleter;

public class UserDeleterInMemoryAdapter implements UserDeleter {
		private final InMemoryStorage inMemoryStorage;

		public UserDeleterInMemoryAdapter(final InMemoryStorage inMemoryStorage) {
				this.inMemoryStorage = inMemoryStorage;
		}

		@Override
		public void delete(final String userId) {
				this.inMemoryStorage.users().remove(userId);
		}
}
