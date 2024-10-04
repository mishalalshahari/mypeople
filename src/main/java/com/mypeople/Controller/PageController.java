package com.mypeople.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    //about route
    @RequestMapping("/services")
    public String servicePage() {
        System.out.println("Service page is loading");
        return "services";
    }
}
