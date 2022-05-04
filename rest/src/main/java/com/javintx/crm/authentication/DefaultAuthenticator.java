package com.javintx.crm.authentication;

import io.jsonwebtoken.Jwts;

import java.time.Instant;

public abstract class DefaultAuthenticator implements Authenticator {

		private static final String TOKEN_PREFIX = "Bearer ";
		private final String jwtSecretKey;

		protected DefaultAuthenticator(final String jwtSecretKey) {
				this.jwtSecretKey = jwtSecretKey;
		}

		protected String tokenFrom(final String authorizationHeader) {
				return authorizationHeader.replace(TOKEN_PREFIX, "");
		}

		protected boolean validate(final String token) {
				try {
						return Jwts.parser()
								.setSigningKey(jwtSecretKey)
								.parseClaimsJws(token)
								.getBody()
								.getExpiration()
								.toInstant()
								.isAfter(Instant.now());
				} catch (Exception e) {
						return false;
				}
		}
}
