package com.javintx.crm.domain;

import com.javintx.crm.customer.exception.CustomerNotValid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerShould {

		@Test
		void return_valid_customer_with_valid_values() {
				assertDoesNotThrow(() -> new Customer("identifier", "name", "surname", "photo", "userReference"));
		}

		@Test
		void fail_without_identifier() {
				assertThrows(CustomerNotValid.class, () -> new Customer(null, "name", "surname", "photo", "userReference"));
		}

		@Test
		void fail_with_empty_identifier() {
				assertThrows(CustomerNotValid.class, () -> new Customer("", "name", "surname", "photo", "userReference"));
		}

		@Test
		void fail_with_blank_identifier() {
				assertThrows(CustomerNotValid.class, () -> new Customer(" ", "name", "surname", "photo", "userReference"));
		}

		@Test
		void fail_without_name() {
				assertThrows(CustomerNotValid.class, () -> new Customer("identifier", null, "surname", "photo", "userReference"));
		}

		@Test
		void fail_with_empty_name() {
				assertThrows(CustomerNotValid.class, () -> new Customer("identifier", "", "surname", "photo", "userReference"));
		}

		@Test
		void fail_with_blank_name() {
				assertThrows(CustomerNotValid.class, () -> new Customer("identifier", " ", "surname", "photo", "userReference"));
		}

		@Test
		void fail_without_surname() {
				assertThrows(CustomerNotValid.class, () -> new Customer("identifier", "name", null, "photo", "userReference"));
		}

		@Test
		void fail_with_empty_surname() {
				assertThrows(CustomerNotValid.class, () -> new Customer("identifier", "name", "", "photo", "userReference"));
		}

		@Test
		void fail_with_blank_surname() {
				assertThrows(CustomerNotValid.class, () -> new Customer("identifier", "name", " ", "photo", "userReference"));
		}

		@Test
		void fail_without_userReference() {
				assertThrows(CustomerNotValid.class, () -> new Customer("identifier", "name", "surname", "photo", null));
		}

		@Test
		void fail_with_empty_userReference() {
				assertThrows(CustomerNotValid.class, () -> new Customer("identifier", "name", "surname", "photo", ""));
		}

		@Test
		void fail_with_blank_userReference() {
				assertThrows(CustomerNotValid.class, () -> new Customer("identifier", "name", "surname", "photo", " "));
		}
}
