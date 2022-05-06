package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.port.out.user.UserReader;
import com.javintx.crm.user.exception.UserIsNotAdmin;
import com.javintx.crm.user.impl.IsAdminUserService;
import org.junit.jupiter.api.BeforeEach;
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

		private final UserReader userReader = mock(UserReader.class);
		private IsAdminUserService isAdminUser;

		@BeforeEach
		void setUp() {
				when(userReader.readAll()).thenReturn(getUserList());
				isAdminUser = new IsAdminUserService(userReader);
		}

		@Test
		void fail_when_user_is_not_admin() {
				assertThatThrownBy(() -> isAdminUser.isAdmin("userReference")).isExactlyInstanceOf(UserIsNotAdmin.class);
		}

		@Test
		void fail_when_user_is_null() {
				assertThatThrownBy(() -> isAdminUser.isAdmin(null)).isExactlyInstanceOf(UserIsNotAdmin.class);
		}

		@Test
		void do_nothing_when_user_is_admin() {
				assertThatNoException().isThrownBy(() -> isAdminUser.isAdmin("adminId"));
		}

		private List<User> getUserList() {
				return List.of(
						new User("userReference", "user", "user", false),
						new User("adminId", "admin", "admin", true)
				);
		}
}
