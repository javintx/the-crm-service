package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomerInMemoryAdapterShould {
		private CustomerInMemoryAdapter customerInMemoryAdapter;

		@BeforeEach
		public void setUp() {
				customerInMemoryAdapter = new CustomerInMemoryAdapter();
		}

		@Test
		void return_empty_customer_list_if_there_are_no_customers() {
				assertThat(customerInMemoryAdapter.readAll()).isEmpty();
		}

		@Test
		void return_customer_list_with_new_created_customer() {
				Customer customerMock = new Customer("id", "name", "surname");
				Customer customerCreated = customerInMemoryAdapter.writes(customerMock);
				assertThat(customerCreated).isNotNull();
				assertThat(customerInMemoryAdapter.readAll()).isNotEmpty();
				assertThat(customerInMemoryAdapter.readAll().get(0)).isEqualTo(customerCreated);
		}

		@Test
		void return_customer_list_with_updated_customer() {
				Customer existingCustomer = new Customer("id", "name", "surname");
				customerInMemoryAdapter.writes(existingCustomer);

				Customer customerToUpdate = new Customer("id", "name_modified", "surname_modified");
				Customer updatedCustomer = customerInMemoryAdapter.update(customerToUpdate);

				assertThat(updatedCustomer).isEqualTo(customerToUpdate);
				assertThat(customerInMemoryAdapter.readAll()).isNotEmpty();
				assertThat(customerInMemoryAdapter.readAll().get(0)).isEqualTo(updatedCustomer);
		}

		@Test
		void return_customer_list_without_deleted_customer() {
				Customer existingCustomer = new Customer("id", "name", "surname");
				customerInMemoryAdapter.writes(existingCustomer);

				customerInMemoryAdapter.delete("id");

				assertThat(customerInMemoryAdapter.readAll()).isEmpty();
		}
}
