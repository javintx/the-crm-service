package com.javintx.crm.inMemoryStorage;

import com.javintx.crm.customer.CustomerDto;
import com.javintx.crm.user.UserDto;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStorage {

		private static InMemoryStorage INSTANCE;

		private final Map<String, CustomerDto> customers;
		private final Map<String, UserDto> users;

		private InMemoryStorage() {
				this.customers = new HashMap<>();
				this.users = new HashMap<>();
		}

		public static InMemoryStorage getInstance() {
				if (INSTANCE == null) {
						INSTANCE = new InMemoryStorage();
				}
				return INSTANCE;
		}

		public Map<String, CustomerDto> customers() {
				return customers;
		}

		public Map<String, UserDto> users() {
				return users;
		}

}
