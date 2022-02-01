package com.javintx.crm.port.out.user;

import com.javintx.crm.domain.User;

public interface UserUpdater {

		/**
			* Updates an existing {@link User}.
			*
			* @param user User to uptade.
			* @return User updated.
			*/
		User update(final User user);

}
