package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("admin/signup", "admin/signin", "contact/contact").permitAll()
						.anyRequest().authenticated())

				.formLogin(form -> form
						.loginPage("/admin/signin")
						.loginProcessingUrl("/admin/signin")
						.usernameParameter("email")
						.passwordParameter("password")
						.defaultSuccessUrl("/admin/contacts", true)
						.failureUrl("/admin/signin?error")
						.permitAll()
						)
				.logout(logout -> logout
						.logoutRequestMatcher(AntPathRequestMatcher.antMatcher("/admin/signout"))
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
						.logoutSuccessUrl("/admin/signin")
						);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
