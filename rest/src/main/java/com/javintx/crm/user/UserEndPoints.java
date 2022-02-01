package com.javintx.crm.user;

import static com.javintx.crm.user.UserEndPointsBindNames.USER_ID;
import static java.lang.String.format;

public enum UserEndPoints {
		USER_PATH("/user/*"),
		LIST_ALL_USERS("/user/all"),
		CREATE_NEW_USER("/user/create"),
		UPDATE_USER("/user/update"),
		DELETE_USER(format("/user/delete/:%s", USER_ID.bindName));

		public final String uri;

		UserEndPoints(final String uri) {
				this.uri = uri;
		}
}
