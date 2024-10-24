package com.mypeople.Config;

import com.mypeople.Services.Impl.SecurityCustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //user create and login page using java code with in memory or db service

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails admin =  User.withDefaultPasswordEncoder().username("admin").password("123").roles("ADMIN","USER").build();
//        UserDetails user =  User.withUsername("user").password("user").roles("USER").build();
//
//        var inMemoryUserDetailsManager =  new InMemoryUserDetailsManager(admin,user);
//        return inMemoryUserDetailsManager;
//    }
    @Autowired
    private SecurityCustomUserDetailService userDetailsService;

    //configuration of authentication provider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //user detail service object
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        //password encoder object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //configuration
        //configured which route to be public or private
        httpSecurity.authorizeHttpRequests(authorize->{
            //authorize.requestMatchers("/home","about","services","/register").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        //form default login
        //if we have to change anything then we have to look for form related
        httpSecurity.formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
