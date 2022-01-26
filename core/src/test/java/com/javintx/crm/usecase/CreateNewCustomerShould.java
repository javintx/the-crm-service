package com.javintx.crm.usecase;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.port.out.CustomerWriter;
import com.javintx.crm.usecase.impl.CreateNewCustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateNewCustomerShould {

	@Mock
	private CustomerWriter customerWriterMocked;

	private CreateNewCustomer createNewCustomer;

	@BeforeEach
	void setUp() {
		createNewCustomer = new CreateNewCustomerService(customerWriterMocked);
	}

	@Test
	void return_new_created_customer() {
		Customer customerToCreate = mock(Customer.class);
		Customer expectedCustomer = mock(Customer.class);
		when(customerWriterMocked.writes(any(Customer.class))).thenReturn(expectedCustomer);

		Customer customerCreated = createNewCustomer.with(customerToCreate);

		assertThat(customerCreated).isEqualTo(expectedCustomer);
	}

}
