package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerUpdaterInMemoryAdapterShould {
		private CustomerUpdater userUpdater;

		@BeforeEach
		public void setUp() {
				userUpdater = new CustomerUpdaterInMemoryAdapter();
		}

		@Test
		void return_customer_updated() {
				var customer = new Customer("identifier", "name", "surname", "photo", "userReference");
				var customerDto = CustomerDto.from(customer);

				InMemoryStorage.INSTANCE.customers().put(customerDto.identifier(), customerDto);

				assertThat(userUpdater.update(customer)).isEqualTo(customer);
		}

}
