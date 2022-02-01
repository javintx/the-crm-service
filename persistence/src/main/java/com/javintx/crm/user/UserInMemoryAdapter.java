package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.port.out.user.UserReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInMemoryAdapter implements UserReader {
		private final Map<String, User> users;

		public UserInMemoryAdapter() {
				users = new HashMap<>();
		}

		@Override
		public List<User> readAll() {
				return new ArrayList<>(users.values());
		}
}
