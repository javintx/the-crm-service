package com.javintx.crm.user;

public enum UserEndPointsBindNames {
		USER_ID("userReference"),
		ADMIN_ID("adminId");

		public final String bindName;

		UserEndPointsBindNames(final String bindName) {
				this.bindName = bindName;
		}
}
