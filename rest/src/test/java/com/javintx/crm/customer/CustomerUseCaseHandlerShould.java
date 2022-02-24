package com.javintx.crm.customer;

import com.javintx.crm.customer.exception.CustomerNotValid;
import com.javintx.crm.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerUseCaseHandlerShould {

		@Mock
		private ListAllCustomers listAllCustomersMock;
		@Mock
		private CreateNewCustomer createNewCustomerMock;
		@Mock
		private UpdateCustomer updateCustomerMock;
		@Mock
		private DeleteCustomer deleteCustomerMock;

		private CustomerUseCaseHandler customerUseCaseHandler;

		@BeforeEach
		public void setUp() {
				customerUseCaseHandler = new CustomerUseCaseHandler(listAllCustomersMock, createNewCustomerMock, updateCustomerMock, deleteCustomerMock);
		}

		@Test
		void return_empty_customer_list_if_there_are_no_customers() {
				assertThat(customerUseCaseHandler.get()).isEmpty();
		}

		@Test
		void return_customer_list_if_there_are_customers() {
				var customer = new Customer("id", "name", "surname", "photo", "userId");
				when(listAllCustomersMock.get()).thenReturn(List.of(customer));

				var customerResponseList = customerUseCaseHandler.get();

				assertThat(customerResponseList).isNotEmpty();
				assertThat(customerResponseList.get(0)).isInstanceOf(CustomerResponse.class);
				assertThat(customerResponseList).containsExactly(CustomerResponse.from(customer));
		}

		@Test
		void return_new_customer_created() {
				var customerRequest = new CustomerRequest();
				customerRequest.setId("id");
				customerRequest.setName("name");
				customerRequest.setSurname("name");
				customerRequest.setPhoto("photo");
				customerRequest.setUserId("userId");

				var customerExpected = new Customer("id", "name", "surname", "photo", "userId");
				when(createNewCustomerMock.with(any(Customer.class))).thenReturn(customerExpected);

				var customerResponse = customerUseCaseHandler.create(customerRequest);

				assertThat(customerResponse).isEqualTo(CustomerResponse.from(customerExpected));
		}

		@Test
		void return_customer_updated_when_updates_user_and_exists() {
				var customerRequest = new CustomerRequest();
				customerRequest.setId("id");
				customerRequest.setName("name");
				customerRequest.setSurname("name");
				customerRequest.setPhoto("photo");
				customerRequest.setUserId("userId");

				var customerExpected = new Customer("id", "name", "surname", "photo", "userId");
				when(updateCustomerMock.update(any(Customer.class))).thenReturn(customerExpected);

				var customerResponse = customerUseCaseHandler.update(customerRequest);

				assertThat(customerResponse).isEqualTo(CustomerResponse.from(customerExpected));
		}

		@Test
		void verify_deleted_customer() {
				customerUseCaseHandler.delete("customerId");
				verify(deleteCustomerMock).delete("customerId");
		}

		@Test
		void throw_exception_when_create_new_customer_without_mandatory_field() {
				var customerRequest = new CustomerRequest();
				customerRequest.setId(null);
				customerRequest.setName("name");
				customerRequest.setSurname("surname");
				customerRequest.setUserId("userId");

				assertThatThrownBy(
						() -> customerUseCaseHandler.create(customerRequest)
				).isExactlyInstanceOf(CustomerNotValid.class);

				customerRequest.setId("id");
				customerRequest.setName(null);
				assertThatThrownBy(
						() -> customerUseCaseHandler.create(customerRequest)
				).isExactlyInstanceOf(CustomerNotValid.class);

				customerRequest.setName("name");
				customerRequest.setSurname(null);
				assertThatThrownBy(
						() -> customerUseCaseHandler.create(customerRequest)
				).isExactlyInstanceOf(CustomerNotValid.class);

				customerRequest.setSurname("surname");
				customerRequest.setUserId(null);
				assertThatThrownBy(
						() -> customerUseCaseHandler.create(customerRequest)
				).isExactlyInstanceOf(CustomerNotValid.class);
		}
}
