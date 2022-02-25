package com.javintx.crm.in_memory_storage;

import com.javintx.crm.customer.CustomerDto;
import com.javintx.crm.user.UserDto;

import java.util.HashMap;
import java.util.Map;

public enum InMemoryStorage {

		INSTANCE;

		private final Map<String, CustomerDto> customers;
		private final Map<String, UserDto> users;

		InMemoryStorage() {
				this.customers = new HashMap<>();
				this.users = new HashMap<>();
		}

		public Map<String, CustomerDto> customers() {
				return customers;
		}

		public Map<String, UserDto> users() {
				return users;
		}

}
