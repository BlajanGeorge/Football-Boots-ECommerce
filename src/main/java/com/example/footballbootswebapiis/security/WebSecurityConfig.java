package com.example.footballbootswebapiis.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/users/registration/customer").permitAll()
                .antMatchers("/users/login").permitAll()
                .antMatchers("/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/boots").hasAnyRole("ADMIN", "CUSTOMER")
                .antMatchers(HttpMethod.GET, "/boots/{id}/{size}").hasAnyRole("ADMIN", "CUSTOMER")
                .antMatchers(HttpMethod.POST, "/boots").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/boots/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/boots/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/boots/{id}/{size}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "boots/filter").hasAnyRole("ADMIN", "CUSTOMER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
    }
}
