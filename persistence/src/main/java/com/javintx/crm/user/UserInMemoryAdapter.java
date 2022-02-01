package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.port.out.user.UserReader;
import com.javintx.crm.port.out.user.UserWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInMemoryAdapter implements UserReader, UserWriter {
		private final Map<String, User> users;

		public UserInMemoryAdapter() {
				users = new HashMap<>();
		}

		@Override
		public List<User> readAll() {
				return new ArrayList<>(users.values());
		}

		@Override
		public User writes(final User user) {
				users.put(user.identifier(), user);
				return user;
		}

//		@Override
//		public User update(final User user) {
//				return users.replace(user.identifier(), user);
//		}
//
//		@Override
//		public void delete(final String userId) {
//				users.remove(userId);
//		}
}
