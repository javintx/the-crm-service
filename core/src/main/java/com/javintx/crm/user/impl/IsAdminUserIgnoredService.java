package com.javintx.crm.user.impl;

import com.javintx.crm.user.IsAdminUser;

public class IsAdminUserIgnoredService implements IsAdminUser {
		@Override
		public void isAdmin(final String userId) {
				// Do nothing
				// TODO: This service should be removed after a first admin could be added to the server initialization
		}
}
