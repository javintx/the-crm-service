package com.javintx.crm.customer;

import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerReaderInMemoryAdapterShould {
		private CustomerReader customerReader;

		@BeforeEach
		public void setUp() {
				customerReader = new CustomerReaderInMemoryAdapter();
		}

		@Test
		void return_empty_customer_list_if_there_are_no_customers() {
				assertTrue(customerReader.readAll().isEmpty());
		}

		@Test
		void return_customer_list_when_have_customers() {
				var customerDto = new CustomerDto("identifier", "name", "surname", "photo", "userReference");
				InMemoryStorage.INSTANCE.customers().put(customerDto.identifier(), customerDto);

				assertFalse(customerReader.readAll().isEmpty());
				assertThat(customerReader.readAll().get(0)).isEqualTo(customerDto.toDomain());
		}

}
