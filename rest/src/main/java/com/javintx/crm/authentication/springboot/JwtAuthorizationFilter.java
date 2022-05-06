package com.javintx.crm.authentication.springboot;

import com.javintx.crm.authentication.Authenticator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

		private static final String HEADER_AUTHORIZATION = "Authorization";
		private final Authenticator jwtService;

		public JwtAuthorizationFilter(Authenticator jwtService) {
				this.jwtService = jwtService;
		}

		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
				if (jwtService.isAuthenticated(request.getHeader(HEADER_AUTHORIZATION))) {
						var userDetails = User.builder().passwordEncoder(new BCryptPasswordEncoder()::encode).username("user").password("pass").roles("USER").build();
						var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(authentication);
						filterChain.doFilter(request, response);
				} else {
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}
		}
}
