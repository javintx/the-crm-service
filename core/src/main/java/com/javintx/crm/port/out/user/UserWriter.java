package com.javintx.crm.port.out.user;

import com.javintx.crm.domain.User;

public interface UserWriter {

		/**
			* Creates a new {@link User}.
			*
			* @param user User to create.
			* @return User created.
			*/
		User writes(final User user);
}
