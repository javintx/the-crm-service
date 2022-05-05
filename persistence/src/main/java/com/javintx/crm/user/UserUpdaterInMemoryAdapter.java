package com.javintx.crm.user;

import com.javintx.crm.domain.User;
import com.javintx.crm.exception.CommandCannotBeExecuted;
import com.javintx.crm.in_memory_storage.InMemoryStorage;
import com.javintx.crm.port.out.user.UserUpdater;

import static java.lang.String.format;

public class UserUpdaterInMemoryAdapter implements UserUpdater {

		@Override
		public User update(final User user) {
				var userDto = UserDto.from(user);
				var newUserDto = InMemoryStorage.INSTANCE.users().replace(userDto.identifier(), userDto);
				if (newUserDto == null) {
						throw new CommandCannotBeExecuted(format("User %s does not exists", userDto.identifier()));
				} else {
						return newUserDto.toDomain();
				}
		}
}
