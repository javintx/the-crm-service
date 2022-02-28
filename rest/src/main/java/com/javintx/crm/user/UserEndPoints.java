package com.javintx.crm.user;

import static com.javintx.crm.user.UserEndPoints.BindNames.USER_ID;
import static java.lang.String.format;

public final class UserEndPoints {
		public static final String USER_PATH = "/user/*";
		public static final String LIST_ALL_USERS = "/user/all";
		public static final String CREATE_NEW_USER = "/user/create";
		public static final String UPDATE_USER = "/user/update";
		public static final String DELETE_USER = "/user/delete/:" + USER_ID;

		private UserEndPoints() {
		}

		public static final class BindNames {
				public static final String USER_ID = "userIdentifier";
				public static final String ADMIN_ID = "adminIdentifier";

				private BindNames() {
				}
		}
}
