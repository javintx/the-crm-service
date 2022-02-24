package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.exception.CommandCannotBeExecuted;
import com.javintx.crm.inMemoryStorage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserUpdater;

import static java.lang.String.format;

public class UserUpdaterInMemoryAdapter implements UserUpdater {
		private final InMemoryStorage inMemoryStorage;

		public UserUpdaterInMemoryAdapter(final InMemoryStorage inMemoryStorage) {
				this.inMemoryStorage = inMemoryStorage;
		}

		@Override
		public User update(final User user) {
				var userDto = UserDto.from(user);
				if (this.inMemoryStorage.users().containsKey(userDto.id)) {
						return this.inMemoryStorage.users().replace(userDto.id, userDto).toDomain();
				} else {
						throw new CommandCannotBeExecuted(format("User %s does not exists", userDto.id));
				}
		}
}
