package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.usecase.CreateNewCustomer;
import com.javintx.crm.usecase.ListAllCustomers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerUseCaseHandlerShould {

	@Mock
	private ListAllCustomers listAllCustomersMock;
	@Mock
	private CreateNewCustomer createNewCustomerMock;

	private CustomerUseCaseHandler customerUseCaseHandler;

	@BeforeEach
	public void setUp() {
		customerUseCaseHandler = new CustomerUseCaseHandler(listAllCustomersMock, createNewCustomerMock);
	}

	@Test
	void return_empty_customer_list_if_there_are_no_customers() {
		assertThat(customerUseCaseHandler.get()).isEmpty();
	}

	@Test
	void return_customer_list_if_there_are_customers() {
		Customer customerMock = new Customer();
		when(listAllCustomersMock.get()).thenReturn(List.of(customerMock));

		List<CustomerResponse> customerResponseList = customerUseCaseHandler.get();

		assertThat(customerResponseList).isNotEmpty();
		assertThat(customerResponseList.get(0)).isInstanceOf(CustomerResponse.class);
	}

	@Test
	void return_new_customer_created() {
		CustomerRequest customerRequestMock = new CustomerRequest();
		Customer customerExpected = new Customer();
		when(createNewCustomerMock.with(any(Customer.class))).thenReturn(customerExpected);

		CustomerResponse customerResponse = customerUseCaseHandler.create(customerRequestMock);

		// TODO: When Customer has values, the verification should be changed
		//assertThat(customerResponse).isSameAs(CustomerResponse.from(customerExpected));
		verify(createNewCustomerMock).with(any(Customer.class));
	}
}
