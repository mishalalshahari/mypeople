package com.mypeople.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/home")
    public String homePage(Model model) {

        System.out.println("Home page handler");
        //sending data to view
        model.addAttribute("name","MyPeople");
        return "home";
    }

    //about route
    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("About page is loading");
        return "about";
    }

    //services route
    @RequestMapping("/services")
    public String servicePage() {
        System.out.println("Service page is loading");
        return "services";
    }

    //contact route
    @RequestMapping("/contact")
    public String contactPage() {
        System.out.println("Contact page is loading");
        return "contact";
    }

    //login route
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    //register route
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
}
