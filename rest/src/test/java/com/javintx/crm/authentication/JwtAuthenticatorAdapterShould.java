package com.javintx.crm.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticatorAdapterShould {

		@Test
		void return_true_when_request_is_authenticated() {
				var authenticator = new JwtAuthenticatorAdapter("secret");

				var request = mock(Request.class);
				var response = mock(Response.class);

				var token = "Bearer " + Jwts.builder()
						.setClaims(new DefaultClaims())
						.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)))
						.signWith(SignatureAlgorithm.HS512, "secret")
						.compact();

				when(request.headers("Authorization")).thenReturn(token);

				assertTrue(authenticator.isAuthenticated(request, response));
		}

		@Test
		void return_false_when_request_not_contains_authorization_header() {
				var authenticator = new JwtAuthenticatorAdapter("secret");

				var request = mock(Request.class);
				var response = mock(Response.class);

				assertThrows(spark.HaltException.class, () -> authenticator.isAuthenticated(request, response));
		}

		@Test
		void return_exception_when_request_contains_invalid_authorization_token() {
				var authenticator = new JwtAuthenticatorAdapter("secret");

				var request = mock(Request.class);
				var response = mock(Response.class);

				when(request.headers("Authorization")).thenReturn("invalid");

				assertThrows(spark.HaltException.class, () -> authenticator.isAuthenticated(request, response));
		}

		@Test
		void return_exception_when_request_contains_expired_authorization_token() {
				var authenticator = new JwtAuthenticatorAdapter("secret");

				var request = mock(Request.class);
				var response = mock(Response.class);

				var token = "Bearer " + Jwts.builder()
						.setClaims(new DefaultClaims())
						.setExpiration(new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(10)))
						.signWith(SignatureAlgorithm.HS512, "secret")
						.compact();

				when(request.headers("Authorization")).thenReturn(token);

				assertThrows(spark.HaltException.class, () -> authenticator.isAuthenticated(request, response));
		}
}
