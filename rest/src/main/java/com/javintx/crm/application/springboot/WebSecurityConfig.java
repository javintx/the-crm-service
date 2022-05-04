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
						.csrf().disable()
						.cors().configurationSource(corsConfigurationSource())
						.and()
						.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
						.and()
						.authorizeRequests().antMatchers("/login").permitAll()
						.anyRequest().authenticated()
						.and().addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
		}

		private CorsConfigurationSource corsConfigurationSource() {
				final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

				CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
				source.registerCorsConfiguration("/**", corsConfiguration);

				return source;
		}
}
