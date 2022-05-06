package com.javintx.crm.domain;

import com.javintx.crm.user.exception.UserNotValid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserShould {

		@Test
		void return_valid_user_with_valid_values() {
				assertDoesNotThrow(() -> new User("identifier", "name", "surname", false));
		}

		@Test
		void return_valid_admin_with_valid_values() {
				assertTrue(new User("identifier", "name", "surname", true).isAdmin());
		}

		@Test
		void fail_without_identifier() {
				assertThrows(UserNotValid.class, () -> new User(null, "name", "surname", false));
		}

		@Test
		void fail_with_empty_identifier() {
				assertThrows(UserNotValid.class, () -> new User("", "name", "surname", false));
		}

		@Test
		void fail_with_blank_identifier() {
				assertThrows(UserNotValid.class, () -> new User(" ", "name", "surname", false));
		}

		@Test
		void fail_without_name() {
				assertThrows(UserNotValid.class, () -> new User("identifier", null, "surname", false));
		}

		@Test
		void fail_with_empty_name() {
				assertThrows(UserNotValid.class, () -> new User("identifier", "", "surname", false));
		}

		@Test
		void fail_with_blank_name() {
				assertThrows(UserNotValid.class, () -> new User("identifier", " ", "surname", false));
		}

		@Test
		void fail_without_surname() {
				assertThrows(UserNotValid.class, () -> new User("identifier", "name", null, false));
		}

		@Test
		void fail_with_empty_surname() {
				assertThrows(UserNotValid.class, () -> new User("identifier", "name", "", false));
		}

		@Test
		void fail_with_blank_surname() {
				assertThrows(UserNotValid.class, () -> new User("identifier", "name", " ", false));
		}
}
