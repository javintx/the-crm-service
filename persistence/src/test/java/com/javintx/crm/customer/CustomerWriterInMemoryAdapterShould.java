package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerWriterInMemoryAdapterShould {
		private CustomerWriter customerWriter;

		@BeforeEach
		public void setUp() {
				customerWriter = new CustomerWriterInMemoryAdapter();
		}

		@Test
		void return_customer_write() {
				var customer = new Customer("identifier", "name", "surname", "photo", "userReference");
				var customerDto = CustomerDto.from(customer);

				InMemoryStorage.INSTANCE.customers().put(customerDto.identifier(), customerDto);

				assertThat(customerWriter.writes(customer)).isEqualTo(customer);
		}

}
