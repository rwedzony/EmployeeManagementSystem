package com.rafwedz.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

 @Bean
    public UserDetailsService userDetailsService(){
     UserDetails user= User.withDefaultPasswordEncoder()
             .username("user")
             .password("user")
             .roles("USER")
             .build();

     UserDetails admin= User.withDefaultPasswordEncoder()
             .username("admin")
             .password("admin")
             .roles("ADMIN")
             .build();
     return new InMemoryUserDetailsManager(user,admin);
 }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
     http.authorizeRequests()
                    .anyRequest().hasAnyRole("ADMIN","USER")
                    .and()
                    .formLogin().permitAll()
                    .and()
                    .logout().permitAll();


    }
}