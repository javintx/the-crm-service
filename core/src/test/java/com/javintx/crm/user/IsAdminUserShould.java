package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.port.out.user.UserReader;
import com.javintx.crm.user.exception.UserIsNotAdmin;
import com.javintx.crm.user.impl.IsAdminUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IsAdminUserShould {

		@Test
		void throw_exception_if_is_not_admin_user() {
				UserReader userReader = mock(UserReader.class);
				IsAdminUser isAdminUser = new IsAdminUserService(userReader);
				when(userReader.readAll()).thenReturn(
						List.of(
								User.builder().withId("userId").withName("user").withSurname("user").build(),
								User.builder().withId("adminId").withName("admin").withSurname("admin").thatIsAdmin(true).build()
						)
				);
				assertThatThrownBy(() -> isAdminUser.isAdmin("userId")).isExactlyInstanceOf(UserIsNotAdmin.class);
		}

		@Test
		void do_nothing_is_admin_user() {
				UserReader userReader = mock(UserReader.class);
				IsAdminUser isAdminUser = new IsAdminUserService(userReader);
				when(userReader.readAll()).thenReturn(
						List.of(
								User.builder().withId("userId").withName("user").withSurname("user").build(),
								User.builder().withId("adminId").withName("admin").withSurname("admin").thatIsAdmin(true).build()
						)
				);
				assertThatNoException().isThrownBy(() -> isAdminUser.isAdmin("adminId"));
		}
}
