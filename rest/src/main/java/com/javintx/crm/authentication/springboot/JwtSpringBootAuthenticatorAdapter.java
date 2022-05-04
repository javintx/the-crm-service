package com.javintx.crm.authentication.springboot;

import com.javintx.crm.authentication.DefaultAuthenticator;

public class JwtSpringBootAuthenticatorAdapter extends DefaultAuthenticator {

		public JwtSpringBootAuthenticatorAdapter(final String jwtSecretKey) {
				super(jwtSecretKey);
		}

		@Override
		public boolean isAuthenticated(final String authorizationHeader) {
				// Request like login, register or whatever you need should not be authenticated
				return authorizationHeader != null && validate(tokenFrom(authorizationHeader));
		}

}
