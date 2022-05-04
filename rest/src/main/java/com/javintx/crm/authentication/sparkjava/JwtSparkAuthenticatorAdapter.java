package com.javintx.crm.authentication.sparkjava;

import com.javintx.crm.authentication.DefaultAuthenticator;

import static java.lang.String.format;
import static spark.Spark.halt;

public class JwtSparkAuthenticatorAdapter extends DefaultAuthenticator {

		public JwtSparkAuthenticatorAdapter(final String jwtSecretKey) {
				super(jwtSecretKey);
		}

		@Override
		public boolean isAuthenticated(final String authorizationHeader) {
				// Request like login, register or whatever you need should not be authenticated
				if (authorizationHeader == null) {
						halt(401, "Missing Authorization header");
				} else if (!validate(tokenFrom(authorizationHeader))) {
						halt(401, format("Invalid token %s", authorizationHeader));
				}
				return true;
		}

}
