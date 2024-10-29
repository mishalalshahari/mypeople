package com.mypeople.Controllers;

import com.mypeople.Entities.User;
import com.mypeople.Helpers.AuthenticatedUser;
import com.mypeople.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

    private Logger logger = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void loggedInUserInformation(Model model, Authentication authentication) {
        if(authentication == null){
            return;
        }
        logger.info("Adding logged in user information to model");
        String username = AuthenticatedUser.getEmailOfAuthenticatedUser(authentication);
        //fetch data from db i.e. email, name, and all
        User user = userService.getUserByEmail(username);
        logger.info("User logged in email: " + user.getEmail());
        logger.info("User logged in name: " + user.getName());

        model.addAttribute("loggedInUser", user);
    }
}
