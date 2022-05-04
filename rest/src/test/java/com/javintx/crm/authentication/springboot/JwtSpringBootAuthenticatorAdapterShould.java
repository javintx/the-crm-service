package com.javintx.crm.authentication.springboot;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class JwtSpringBootAuthenticatorAdapterShould {

		@Test
		void return_true_when_request_is_authenticated() {
				var authenticator = new JwtSpringBootAuthenticatorAdapter("secret");

				var token = "Bearer " + Jwts.builder()
						.setClaims(new DefaultClaims())
						.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
						.signWith(SignatureAlgorithm.HS512, "secret")
						.compact();

				assertTrue(authenticator.isAuthenticated(token));
		}

		@Test
		void fail_when_request_not_contains_authorization_header() {
				var authenticator = new JwtSpringBootAuthenticatorAdapter("secret");

				assertFalse(authenticator.isAuthenticated(null));
		}

		@Test
		void fail_when_request_contains_invalid_authorization_token() {
				var authenticator = new JwtSpringBootAuthenticatorAdapter("secret");

				assertFalse(authenticator.isAuthenticated("invalid"));
		}

		@Test
		void fail_when_request_contains_expired_authorization_token() {
				var authenticator = new JwtSpringBootAuthenticatorAdapter("secret");

				var token = "Bearer " + Jwts.builder()
						.setClaims(new DefaultClaims())
						.setExpiration(new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(10)))
						.signWith(SignatureAlgorithm.HS512, "secret")
						.compact();

				assertFalse(authenticator.isAuthenticated(token));
		}
}
