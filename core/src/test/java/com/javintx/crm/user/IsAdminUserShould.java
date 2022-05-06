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
				var userReader = mock(UserReader.class);
				var isAdminUser = new IsAdminUserService(userReader);
				when(userReader.readAll()).thenReturn(
						List.of(
								new User("userReference", "user", "user", false),
								new User("adminId", "admin", "admin", true)
						)
				);
				assertThatThrownBy(() -> isAdminUser.isAdmin("userReference")).isExactlyInstanceOf(UserIsNotAdmin.class);
		}

		@Test
		void do_nothing_is_admin_user() {
				var userReader = mock(UserReader.class);
				var isAdminUser = new IsAdminUserService(userReader);
				when(userReader.readAll()).thenReturn(
						List.of(
								new User("userReference", "user", "user", false),
								new User("adminId", "admin", "admin", true)
						)
				);
				assertThatNoException().isThrownBy(() -> isAdminUser.isAdmin("adminId"));
		}
}
