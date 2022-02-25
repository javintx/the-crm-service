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
				var customer = new Customer("identifier", "name", "surname", "photo", "userReference");
				when(listAllCustomersMock.get()).thenReturn(List.of(customer));

				var customerResponseList = customerUseCaseHandler.get();

				assertThat(customerResponseList).isNotEmpty();
				assertThat(customerResponseList.get(0)).isInstanceOf(CustomerResponse.class);
				assertThat(customerResponseList).containsExactly(CustomerResponse.from(customer));
		}

		@Test
		void return_new_customer_created() {
				var customerRequest = new CustomerRequest("identifier", "surname", "name", "photo", "userReference");
				var customerExpected = new Customer("identifier", "name", "surname", "photo", "userReference");
				when(createNewCustomerMock.with(any(Customer.class))).thenReturn(customerExpected);

				var customerResponse = customerUseCaseHandler.create(customerRequest);

				assertThat(customerResponse).isEqualTo(CustomerResponse.from(customerExpected));
		}

		@Test
		void return_customer_updated_when_updates_user_and_exists() {
				var customerRequest = new CustomerRequest("identifier", "surname", "name", "photo", "userReference");
				var customerExpected = new Customer("identifier", "name", "surname", "photo", "userReference");
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
				var customerRequestWithoutId = new CustomerRequest(null, "name", "surname", null, "userReference");
				assertThatThrownBy(
						() -> customerUseCaseHandler.create(customerRequestWithoutId)
				).isExactlyInstanceOf(CustomerNotValid.class);

				var customerRequestWithoutName = new CustomerRequest("identifier", null, "surname", null, "userReference");
				assertThatThrownBy(
						() -> customerUseCaseHandler.create(customerRequestWithoutName)
				).isExactlyInstanceOf(CustomerNotValid.class);

				var customerRequestWithoutSurname = new CustomerRequest("identifier", "name", null, null, "userReference");
				assertThatThrownBy(
						() -> customerUseCaseHandler.create(customerRequestWithoutSurname)
				).isExactlyInstanceOf(CustomerNotValid.class);

				var customerRequestWithoutUserReference = new CustomerRequest("identifier", "name", "surname", null, null);
				assertThatThrownBy(
						() -> customerUseCaseHandler.create(customerRequestWithoutUserReference)
				).isExactlyInstanceOf(CustomerNotValid.class);
		}
}
