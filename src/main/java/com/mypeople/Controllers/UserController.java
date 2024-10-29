package com.mypeople.Controllers;

import com.mypeople.Helpers.AuthenticatedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    //user dashboard
    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }
    //user profile page
    @RequestMapping(value = "/profile")
    public String userProfile(Authentication authentication) {
        String username = AuthenticatedUser.getEmailOfAuthenticatedUser(authentication);
        logger.info("User logged in: " + username);
        //fetch data from db i.e. email, name, and all
        return "user/profile";
    }
    //user add contacts page
    //user view contacts
    //user edit contact
    //user delete contact
}
