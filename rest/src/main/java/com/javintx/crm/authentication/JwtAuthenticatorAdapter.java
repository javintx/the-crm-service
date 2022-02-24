package com.javintx.crm.authentication;

import io.jsonwebtoken.Jwts;
import spark.Request;
import spark.Response;

import java.time.Instant;

import static java.lang.String.format;
import static spark.Spark.halt;

public class JwtAuthenticatorAdapter implements Authenticator {

		private static final String TOKEN_PREFIX = "Bearer ";
		private final String jwtSecretKey;

		public JwtAuthenticatorAdapter(final String jwtSecretKey) {
				this.jwtSecretKey = jwtSecretKey;
		}

		@Override
		public boolean isAuthenticated(final Request request, final Response response) {
				// Request like login, register or whatever you need should not be authenticated
				var authorizationHeader = request.headers("Authorization");
				if (authorizationHeader == null) {
						halt(401, "Missing Authorization header");
				} else if (!validate(tokenFrom(authorizationHeader))) {
						halt(401, format("Invalid token %s", authorizationHeader));
				}
				return true;
		}

		private String tokenFrom(final String authorizationHeader) {
				return authorizationHeader.replace(TOKEN_PREFIX, "");
		}

		private boolean validate(final String token) {
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
