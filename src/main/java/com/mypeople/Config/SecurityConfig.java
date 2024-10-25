package com.mypeople.Config;

import com.mypeople.Services.Impl.SecurityCustomUserDetailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

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
        httpSecurity.formLogin(formLogin->{
            //formLogin.loginPage("/login");
            //formLogin.loginProcessingUrl("/authenticate");
            formLogin.loginPage("/login").loginProcessingUrl("/authenticate").successForwardUrl("/user/dashboard");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
//            formLogin.failureHandler(new AuthenticationFailureHandler() {
//                @Override
//                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//
//                }
//            });
//            formLogin.successHandler(new AuthenticationSuccessHandler() {
//                @Override
//                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//                }
//            });

        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm-> logoutForm.logoutUrl("/logout").logoutSuccessUrl("/login?logout=true"));

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
