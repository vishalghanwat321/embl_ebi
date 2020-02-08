package com.org.web.embl_ebi.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote This class is to define/configure security rules.
 * @since 2020
 */

@Configuration
@EnableWebSecurity
public class WebServiceSecurityFilter extends WebSecurityConfigurerAdapter {

	// TODO Keeping this simple so API's will be invoked easily, later we can enhance the rules to handle security and access of API's
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and().authorizeRequests()
                .antMatchers("/person/**", "/swagger**")
                .authenticated()
                .antMatchers("/v2**", "/status/**", "/h2-console/**")
                .permitAll()
                .and()
                .httpBasic()
                .and().csrf().disable();
    }
}
