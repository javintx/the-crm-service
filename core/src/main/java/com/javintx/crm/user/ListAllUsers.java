package com.javintx.crm.user;

import com.javintx.crm.domain.User;

import java.util.List;

public interface ListAllUsers {

		/**
			* Get all {@link User} in a {@link List}.
			*
			* @return List of users.
			*/
		List<User> get();
}
