package com.mypeople.Config;

import com.mypeople.Entities.Providers;
import com.mypeople.Entities.User;
import com.mypeople.Helpers.AppConstants;
import com.mypeople.Repositories.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");

        //Identify the provider
        var oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;

        String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        logger.info(authorizedClientRegistrationId);

        var oAuth2User = (DefaultOAuth2User)authentication.getPrincipal();
        oAuth2User.getAttributes().forEach((key, value)->{
            logger.info(key + " : " + value);
        });

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword("dummy");
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);

        //Google
        //Google Attributes
        if(authorizedClientRegistrationId.equalsIgnoreCase("google")) {
            user.setEmail(oAuth2User.getAttribute("email").toString());
            user.setName(oAuth2User.getAttribute("name").toString());
            user.setProfilePic(oAuth2User.getAttribute("picture").toString());
            user.setProviderUserId(oAuth2User.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This account is created by Google.");
        }
        //Github
        //Github Attributes
        else if(authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            String email = oAuth2User.getAttribute("email").toString() != null ? oAuth2User.getAttribute("email").toString() : oAuth2User.getAttribute("login").toString()+"@github.com";
            user.setEmail(email);
            user.setName(oAuth2User.getAttribute("name").toString());
            user.setProfilePic(oAuth2User.getAttribute("avatar_url").toString());
            user.setProviderUserId(oAuth2User.getAttribute("login").toString());
            user.setProvider(Providers.GITHUB);
            user.setAbout("This account is created by Github.");
        }
        //if nothing from above
        else {
            logger.info("OAuthAuthenticationSuccessHandler: Unknown Provider");
        }

        //save the user to db
        User fetchedUser = userRepo.findByEmail(user.getEmail()).orElse(null);
        if(fetchedUser == null) {
            userRepo.save(user);
            System.out.println("User " +user.getEmail()+ " saved to database.");
        }
/*
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
//        logger.info(user.getName());
//
//        user.getAttributes().forEach((key, value)->{
//            logger.info(key+" : "+value);
//        });
//
//        logger.info(user.getAuthorities().toString());
        String email = oAuth2User.getAttribute("email").toString();
        String name = oAuth2User.getAttribute("name").toString();
        String picture = oAuth2User.getAttribute("picture").toString();

        //create user and save in database
        User user  = new User();
        user.setEmail(email);
        user.setName(name);
        user.setProfilePic(picture);
        user.setPassword("password");
        user.setUserId(UUID.randomUUID().toString());
        user.setProvider(Providers.GOOGLE);
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setProviderUserId(user.getName());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setAbout("This accout is created using Google.");

        //save data to database
        User fetchedUser = userRepo.findByEmail(email).orElse(null);
        if(fetchedUser == null) {
            userRepo.save(user);
            logger.info("User saved: " + email);
        }

        //response.sendRedirect("/home");
 */
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}
