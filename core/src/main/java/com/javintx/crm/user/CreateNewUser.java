package com.javintx.crm.user;

import com.javintx.crm.domain.User;

public interface CreateNewUser {

		/**
			* Create new {@link User};
			*
			* @param user User to creates.
			* @return User created.
			*/
		User with(final User user);
}
