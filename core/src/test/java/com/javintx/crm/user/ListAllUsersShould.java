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
		void return_empty_user_list_if_there_are_no_users() {
				assertThat(listAllUsers.get()).isEmpty();
		}

		@Test
		void return_user_list_if_there_are_users() {
				User userMock = User.buildUser().withId("id").withName("name").withSurname("surname").build();
				when(userReaderMock.readAll()).thenReturn(List.of(userMock));
				assertThat(listAllUsers.get()).isNotEmpty();
		}

}
