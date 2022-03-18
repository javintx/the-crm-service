package com.javintx.crm.user;

public final class UserEndPoints {
		public static final String USERS_BASE_URL = "/users";
		public static final String USERS_PATH = "/*";

		private UserEndPoints() {
		}

		public static final class BindNames {
				public static final String USER_ID = "userIdentifier";
				public static final String ADMIN_ID = "adminIdentifier";

				private BindNames() {
				}
		}
}
