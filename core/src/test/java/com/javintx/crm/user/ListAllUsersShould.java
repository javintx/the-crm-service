package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.port.out.user.UserReader;
import com.javintx.crm.user.impl.ListAllUsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ListAllUsersShould {

		@Mock
		private UserReader userReaderMock;

		private ListAllUsers listAllUsers;

		@BeforeEach
		void setUp() {
				listAllUsers = new ListAllUsersService(userReaderMock);
		}

		@Test
		void return_empty_customer_list_if_there_are_no_customers() {
				assertThat(listAllUsers.get()).isEmpty();
		}

		@Test
		void return_customer_list_if_there_are_customers() {
				User userMock = new User("id", "name", "surname");
				when(userReaderMock.readAll()).thenReturn(List.of(userMock));
				assertThat(listAllUsers.get()).isNotEmpty();
		}

}
