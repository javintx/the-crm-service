package com.javintx.crm.user;

import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserDeleter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDeleterInMemoryAdapterShould {
		private UserDeleter userDeleter;

		@Mock
		private InMemoryStorage inMemoryStorage;

		@BeforeEach
		public void setUp() {
				userDeleter = new UserDeleterInMemoryAdapter(inMemoryStorage);
		}

		@Test
		void return_user_write() {
				final var USER_ID = "userReference";

				var mapMock = mock(Map.class);
				when(inMemoryStorage.users()).thenReturn(mapMock);

				assertDoesNotThrow(() -> userDeleter.delete(USER_ID));
				verify(mapMock).remove(USER_ID);
		}

}
