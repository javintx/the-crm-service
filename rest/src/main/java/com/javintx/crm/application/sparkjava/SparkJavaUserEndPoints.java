package com.javintx.crm.application.sparkjava;

import static com.javintx.crm.user.UserEndPoints.BindNames.USER_ID;
import static com.javintx.crm.user.UserEndPoints.USERS_BASE_URL;

public final class SparkJavaUserEndPoints {
		public static final String LIST_ALL_USERS = USERS_BASE_URL;
		public static final String CREATE_NEW_USER = USERS_BASE_URL;
		public static final String USER_ID_PATH_VARIABLE = ":" + USER_ID;
		public static final String UPDATE_USER = USERS_BASE_URL + "/" + USER_ID_PATH_VARIABLE;
		public static final String DELETE_USER = USERS_BASE_URL + "/" + USER_ID_PATH_VARIABLE;

}
