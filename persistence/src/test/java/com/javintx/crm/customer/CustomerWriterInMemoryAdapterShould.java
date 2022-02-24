package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerWriterInMemoryAdapterShould {
		private CustomerWriter customerWriter;

		@Mock
		private InMemoryStorage inMemoryStorage;

		@BeforeEach
		public void setUp() {
				customerWriter = new CustomerWriterInMemoryAdapter(inMemoryStorage);
		}

		@Test
		void return_customer_write() {
				var customer = new Customer("id", "name", "surname", "photo", "userReference");

				var mapMock = mock(Map.class);
				when(inMemoryStorage.customers()).thenReturn(mapMock);

				assertThat(customerWriter.writes(customer)).isEqualTo(customer);
				verify(mapMock).put(customer.identifier(), CustomerDto.from(customer));
		}

}
