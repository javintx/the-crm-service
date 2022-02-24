package com.javintx.crm.inMemoryStorage;

import com.javintx.crm.customer.CustomerDto;
import com.javintx.crm.user.UserDto;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStorage {

		private final Map<String, CustomerDto> customers;
		private final Map<String, UserDto> users;

		public InMemoryStorage() {
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
