package com.javintx.crm.customer;

import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerReaderInMemoryAdapterShould {
		private CustomerReader customerReader;

		@Mock
		private InMemoryStorage inMemoryStorage;

		@BeforeEach
		public void setUp() {
				customerReader = new CustomerReaderInMemoryAdapter(inMemoryStorage);
		}

		@Test
		void return_empty_user_list_if_there_are_no_users() {
				assertThat(customerReader.readAll()).isEmpty();
				verify(inMemoryStorage).customers();
		}

		@Test
		void return_user_list_when_have_users() {
				var customerDto = new CustomerDto("identifier", "name", "surname", "photo", "userReference");
				var customers = new HashMap<String, CustomerDto>();
				customers.put(customerDto.identifier(), customerDto);

				when(inMemoryStorage.customers()).thenReturn(customers);

				assertThat(customerReader.readAll().get(0)).isEqualTo(customerDto.toDomain());
				verify(inMemoryStorage).customers();
		}

}
