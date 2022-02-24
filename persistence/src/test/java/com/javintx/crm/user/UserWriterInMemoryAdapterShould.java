package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserWriterInMemoryAdapterShould {
		private UserWriter userWriter;

		@Mock
		private InMemoryStorage inMemoryStorage;

		@BeforeEach
		public void setUp() {
				userWriter = new UserWriterInMemoryAdapter(inMemoryStorage);
		}

		@Test
		void return_user_write() {
				var user = User.builder().withId("id").withName("name").withSurname("surname").thatIsAdmin(false).build();

				var mapMock = mock(Map.class);
				when(inMemoryStorage.users()).thenReturn(mapMock);

				assertThat(userWriter.writes(user)).isEqualTo(user);
				verify(mapMock).put(user.identifier(), UserDto.from(user));
		}

}
