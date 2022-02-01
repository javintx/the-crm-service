package com.javintx.crm.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserInMemoryAdapterShould {
		private UserInMemoryAdapter userInMemoryAdapter;

		@BeforeEach
		public void setUp() {
				userInMemoryAdapter = new UserInMemoryAdapter();
		}

		@Test
		void return_empty_user_list_if_there_are_no_users() {
				assertThat(userInMemoryAdapter.readAll()).isEmpty();
		}

//		@Test
//		void return_user_list_with_new_created_user() {
//				User userMock = new User("id", "name", "surname");
//				User userCreated = userInMemoryAdapter.writes(userMock);
//				assertThat(userCreated).isNotNull();
//				assertThat(userInMemoryAdapter.readAll()).isNotEmpty();
//				assertThat(userInMemoryAdapter.readAll().get(0)).isEqualTo(userCreated);
//		}
//
//		@Test
//		void return_user_list_with_updated_user() {
//				User existingCustomer = new User("id", "name", "surname");
//				userInMemoryAdapter.writes(existingCustomer);
//
//				User userToUpdate = new User("id", "name_modified", "surname_modified");
//				User updatedCustomer = userInMemoryAdapter.update(userToUpdate);
//
//				assertThat(updatedCustomer).isEqualTo(userToUpdate);
//				assertThat(userInMemoryAdapter.readAll()).isNotEmpty();
//				assertThat(userInMemoryAdapter.readAll().get(0)).isEqualTo(updatedCustomer);
//		}
//
//		@Test
//		void return_user_list_without_deleted_user() {
//				User existingCustomer = new User("id", "name", "surname");
//				userInMemoryAdapter.writes(existingCustomer);
//
//				userInMemoryAdapter.delete("id");
//
//				assertThat(userInMemoryAdapter.readAll()).isEmpty();
//		}
}
