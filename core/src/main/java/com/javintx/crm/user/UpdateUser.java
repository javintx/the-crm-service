package com.javintx.crm.user;

import com.javintx.crm.domain.User;

public interface UpdateUser {

		/**
			* Update user if exists.
			*
			* @param user {@link User} to update. The user must exist.
			* @return User updated.
			*/
		User update(final User user);
}
