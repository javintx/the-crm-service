package com.javintx.crm.customer;

import com.javintx.crm.domain.Customer;
import com.javintx.crm.usecase.CreateNewCustomer;
import com.javintx.crm.usecase.ListAllCustomers;
import com.javintx.crm.usecase.UpdateCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerUseCaseHandlerShould {

	@Mock
	private ListAllCustomers listAllCustomersMock;
	@Mock
	private CreateNewCustomer createNewCustomerMock;
	@Mock
	private UpdateCustomer updateCustomerMock;

	private CustomerUseCaseHandler customerUseCaseHandler;

	@BeforeEach
	public void setUp() {
		customerUseCaseHandler = new CustomerUseCaseHandler(listAllCustomersMock, createNewCustomerMock, updateCustomerMock);
	}

	@Test
	void return_empty_customer_list_if_there_are_no_customers() {
		assertThat(customerUseCaseHandler.get()).isEmpty();
	}

	@Test
	void return_customer_list_if_there_are_customers() {
		Customer customerMock = new Customer("id", "name", "surname");
		when(listAllCustomersMock.get()).thenReturn(List.of(customerMock));

		List<CustomerResponse> customerResponseList = customerUseCaseHandler.get();

		assertThat(customerResponseList).isNotEmpty();
		assertThat(customerResponseList.get(0)).isInstanceOf(CustomerResponse.class);
		assertThat(customerResponseList).containsExactly(CustomerResponse.from(customerMock));
	}

	@Test
	void return_new_customer_created() {
		CustomerRequest customerRequestMock = new CustomerRequest();
		Customer customerExpected = new Customer("id", "name", "surname");
		when(createNewCustomerMock.with(any(Customer.class))).thenReturn(customerExpected);

		CustomerResponse customerResponse = customerUseCaseHandler.create(customerRequestMock);

		assertThat(customerResponse).isEqualTo(CustomerResponse.from(customerExpected));
	}

	@Test
	void return_customer_updated_when_updates_user_and_exists() {
		CustomerRequest customerRequestMock = new CustomerRequest();
		customerRequestMock.setId("id");
		customerRequestMock.setName("name");
		customerRequestMock.setSurname("name");
		customerRequestMock.setPhoto("photo");

		Customer customerExpected = new Customer("id", "name", "surname", "photo");
		when(updateCustomerMock.update(any(Customer.class))).thenReturn(customerExpected);

		CustomerResponse customerResponse = customerUseCaseHandler.update(customerRequestMock);

		assertThat(customerResponse).isEqualTo(CustomerResponse.from(customerExpected));
	}
}
