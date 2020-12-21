package com.app.security;

import java.security.SecureRandom;

import javax.sql.DataSource;

import com.app.service.user.AppUserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
public class AppSecurity extends WebSecurityConfigurerAdapter {
    @Value("${local.development}")
    private boolean localDevelopment;

    @Autowired
    private AuthenticationEntryPoint authEntryPoint;

    @Autowired
    DataSource dataSource;

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (localDevelopment) {
            http.csrf().disable();
        } else {
            http.csrf().ignoringAntMatchers("/dataset")
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        }

        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic().authenticationEntryPoint(authEntryPoint)
                .and().sessionManagement();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /* does nothing */
    }
}