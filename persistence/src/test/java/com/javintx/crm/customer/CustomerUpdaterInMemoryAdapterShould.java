package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerUpdaterInMemoryAdapterShould {
		private CustomerUpdater userUpdater;

		@Mock
		private InMemoryStorage inMemoryStorage;

		@BeforeEach
		public void setUp() {
				userUpdater = new CustomerUpdaterInMemoryAdapter(inMemoryStorage);
		}

		@Test
		void return_customer_updated() {
				var customer = new Customer("identifier", "name", "surname", "photo", "userReference");
				var customerDto = CustomerDto.from(customer);

				var customers = new HashMap<String, CustomerDto>(1);
				customers.put(customerDto.identifier(), customerDto);

				when(inMemoryStorage.customers()).thenReturn(customers).thenReturn(customers);

				assertThat(userUpdater.update(customer)).isEqualTo(customer);
				verify(inMemoryStorage, times(2)).customers();
		}

}
