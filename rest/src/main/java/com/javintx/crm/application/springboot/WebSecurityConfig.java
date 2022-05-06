package com.javintx.crm.application.springboot;

import com.javintx.crm.authentication.springboot.AuthenticationFilter;
import com.javintx.crm.authentication.springboot.JwtAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		private final JwtAuthorizationFilter jwtAuthorizationFilter;
		private final AuthenticationFilter authenticationFilter;

		public WebSecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter,
																											AuthenticationFilter authenticationFilter) {
				this.jwtAuthorizationFilter = jwtAuthorizationFilter;
				this.authenticationFilter = authenticationFilter;
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
				http
						.addFilterAfter(authenticationFilter, BasicAuthenticationFilter.class)
						.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
						.and()
						.csrf().disable()
						.cors().configurationSource(corsConfigurationSource())
						.and()
						.authorizeRequests()
						.anyRequest().authenticated()
						.and().addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
		}

		private CorsConfigurationSource corsConfigurationSource() {
				var configuration = new CorsConfiguration();
				configuration.setAllowedMethods(List.of(GET.name(), PUT.name(), POST.name(), DELETE.name()));
				configuration.setAllowedHeaders(
						List.of("Content-Type", "Authorization", "X-Requested-With", "Content-Length", "Accept", "Origin")
				);
				configuration.setAllowCredentials(true);
				configuration.setAllowedOrigins(List.of("*"));

				var source = new UrlBasedCorsConfigurationSource();
				source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
				return source;
		}
}
