package com.javintx.crm.customer;

import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.customer.CustomerDeleter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerDeleterInMemoryAdapterShould {
		private CustomerDeleter customerDeleter;

		@Mock
		private InMemoryStorage inMemoryStorage;

		@BeforeEach
		public void setUp() {
				customerDeleter = new CustomerDeleterInMemoryAdapter(inMemoryStorage);
		}

		@Test
		void return_customer_write() {
				final var CUSTOMER_ID = "customerId";

				var mapMock = mock(Map.class);
				when(inMemoryStorage.customers()).thenReturn(mapMock);

				assertDoesNotThrow(() -> customerDeleter.delete(CUSTOMER_ID));
				verify(mapMock).remove(CUSTOMER_ID);
		}

}
