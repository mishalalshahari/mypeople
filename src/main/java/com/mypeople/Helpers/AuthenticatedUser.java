package com.mypeople.Helpers;

import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.security.Principal;

public class AuthenticatedUser {

    public static String getEmailOfAuthenticatedUser(Authentication authentication) {

        if(authentication instanceof OAuth2AuthenticationToken) {

            var oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
            String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oAuth2User = (DefaultOAuth2User)authentication.getPrincipal();
            String username = "";

            //logged in via google
            if(authorizedClientRegistrationId.equalsIgnoreCase("google")) {
                System.out.println("Getting email from google client");
                username = oAuth2User.getAttribute("email").toString();
            }
            //logged in via github
            else if(authorizedClientRegistrationId.equalsIgnoreCase("github")) {
                System.out.println("Getting email from github client");
                username = oAuth2User.getAttribute("email").toString() != null ? oAuth2User.getAttribute("email").toString() : oAuth2User.getAttribute("login").toString()+"@github.com";
            }
            return username;
        } else {
            //self logged in user
            System.out.println("Getting email from local database");
            return authentication.getName();
        }
    }
}
