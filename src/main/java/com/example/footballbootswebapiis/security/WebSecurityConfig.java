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

    private static final String ADMIN = "ADMIN";
    private static final String CUSTOMER = "CUSTOMER";

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/users/registration/customer").permitAll()
                .antMatchers("/users/login").permitAll()
                .antMatchers("/favorites").hasAnyRole(ADMIN, CUSTOMER)
                .antMatchers("/favorites/**").hasAnyRole(ADMIN, CUSTOMER)
                .antMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole(ADMIN, CUSTOMER)
                .antMatchers(HttpMethod.PUT, "/users/{id}").hasAnyRole(ADMIN, CUSTOMER)
                .antMatchers(HttpMethod.POST, "/basket/**").hasAnyRole(ADMIN, CUSTOMER)
                .antMatchers(HttpMethod.GET, "/users/order/{email}").hasAnyRole(ADMIN, CUSTOMER)
                .antMatchers("/users/**").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, "/boots").hasAnyRole(ADMIN, CUSTOMER)
                .antMatchers(HttpMethod.GET, "/boots/{id}/{size}").hasAnyRole(ADMIN, CUSTOMER)
                .antMatchers(HttpMethod.POST, "/boots").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, "/boots/{id}").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, "/boots/{id}").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, "/boots/{id}/{size}").hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, "boots/filter").hasAnyRole(ADMIN, CUSTOMER)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
    }
}
