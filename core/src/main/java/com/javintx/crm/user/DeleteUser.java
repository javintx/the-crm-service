package com.javintx.crm.user;

import com.javintx.crm.domain.User;

public interface DeleteUser {

		/**
			* Delete the {@link User}.
			*
			* @param userId User ID to delete.
			*/
		void delete(final String userId);
}
