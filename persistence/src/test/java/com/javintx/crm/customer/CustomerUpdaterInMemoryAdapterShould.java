package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.exception.CommandCannotBeExecuted;
import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerUpdaterInMemoryAdapterShould {
		private CustomerUpdater customerUpdater;

		@BeforeEach
		public void setUp() {
				customerUpdater = new CustomerUpdaterInMemoryAdapter();
				InMemoryStorage.INSTANCE.customers().clear();
		}

		@Test
		void return_customer_updated() {
				var customer = new Customer("identifier", "name", "surname", "photo", "userReference");
				var customerDto = CustomerDto.from(customer);

				InMemoryStorage.INSTANCE.customers().put(customerDto.identifier(), customerDto);

				assertThat(customerUpdater.update(customer)).isEqualTo(customer);
		}

		@Test
		void fail_when_customer_not_exists() {
				var customer = new Customer("identifier", "name", "surname", "photo", "userReference");

				assertThatThrownBy(() -> customerUpdater.update(customer)).isExactlyInstanceOf(CommandCannotBeExecuted.class);
		}

}
