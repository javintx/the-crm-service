package com.javintx.crm.user;

import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserDeleter;

public class UserDeleterInMemoryAdapter implements UserDeleter {

		@Override
		public void delete(final String userId) {
				InMemoryStorage.INSTANCE.users().remove(userId);
		}
}
