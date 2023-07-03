package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurations {

    @Bean // first step in memory authentication
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){

        UserDetails admin = User.withUsername("samantha")
                .password(passwordEncoder.encode("pwd1"))
                .roles("ADMIN","USER")
                .build();

        UserDetails user = User.withUsername("niveda")
                .password(passwordEncoder.encode("pwd2"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin,user);

    }

    @Bean // second step for in memory authentication
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{

        return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("home/welcome","/home/user/add").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/home/**")
                .authenticated().and().formLogin().and().build();

    }

    @Bean // encrypt password
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
