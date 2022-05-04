package com.javintx.crm.authentication.springboot;

import com.javintx.crm.user.UserUseCaseHandler;
import com.javintx.crm.user.exception.UserIsNotAdmin;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.javintx.crm.user.UserEndPoints.BindNames.ADMIN_ID;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
		private final UserUseCaseHandler userUseCaseHandler;

		public AuthenticationFilter(UserUseCaseHandler userUseCaseHandler) {
				this.userUseCaseHandler = userUseCaseHandler;
		}

		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
				try {
						userUseCaseHandler.isAdmin(request.getHeader(ADMIN_ID));
						filterChain.doFilter(request, response);
				} catch (UserIsNotAdmin ex) {
						response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}
		}
}
