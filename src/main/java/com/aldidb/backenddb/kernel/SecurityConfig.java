package com.aldidb.backenddb.kernel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aldidb.backenddb.filter.HeaderFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	protected void configure(final HttpSecurity http) throws Exception {
		http
        .csrf().disable()
        .authorizeHttpRequests()
        .antMatchers("**").permitAll();
		
		HeaderFilter headerFilter = new HeaderFilter();
		http.addFilterBefore(headerFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	  @Bean
	  public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(12);
	  }
	  
	  @Override
	  @Bean
	  public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	  }
}
