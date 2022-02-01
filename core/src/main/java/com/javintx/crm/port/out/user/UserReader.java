package com.javintx.crm.port.out.user;

import com.javintx.crm.domain.User;

import java.util.List;

public interface UserReader {

		/**
			* Get all {@link User} in a {@link List}.
			*
			* @return List of users.
			*/
		List<User> readAll();
}
