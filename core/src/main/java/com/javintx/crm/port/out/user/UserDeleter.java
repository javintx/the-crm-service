package com.javintx.crm.port.out.user;

import com.javintx.crm.domain.User;

public interface UserDeleter {

		/**
			* Delete the {@link User}.
			*
			* @param userId User ID to delete.
			*/
		void delete(final String userId);
}
