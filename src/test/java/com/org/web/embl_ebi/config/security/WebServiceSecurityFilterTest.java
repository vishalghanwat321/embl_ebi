package com.org.web.embl_ebi.config.security;

import java.util.Arrays;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
public class WebServiceSecurityFilterTest {

	@Bean
	@Primary
	public UserDetailsService userDetailsService() {
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
		UserDetails userDetails = new User("embl_ebi", "{noop}password", Arrays.asList(grantedAuthority));
		return new InMemoryUserDetailsManager(userDetails);
	}
}