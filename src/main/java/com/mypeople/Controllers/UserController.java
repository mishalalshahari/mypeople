package com.mypeople.Controllers;

import com.mypeople.Entities.User;
import com.mypeople.Helpers.AuthenticatedUser;
import com.mypeople.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    //user dashboard
    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }
    //user profile page
    @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication) {

        return "user/profile";
    }
    //user add contacts page
    //user view contacts
    //user edit contact
    //user delete contact
}
