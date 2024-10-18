package com.mypeople.Controllers;

import com.mypeople.Entities.User;
import com.mypeople.Forms.UserForm;
import com.mypeople.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

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
    public String login() {
        return "login";
    }

    //register route
    @GetMapping("/register")
    public String register(Model model) {
        UserForm userForm = new UserForm();
        //we can also add default data
//        userForm.setName("Mishal Al");
//        userForm.setAbout("main mishal hu");
        model.addAttribute("userForm", userForm);
        return "register";
    }

    //processing register
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@ModelAttribute UserForm userForm) {
        System.out.println("processing register");
        //fetch form data
        System.out.println(userForm);
        //form
        //validate form data
        //save to database
        //userservice
//        User user = User.builder()
//                .name(userForm.getName())
//                .email(userForm.getEmail())
//                .phoneNumber(userForm.getPhoneNumber())
//                .password(userForm.getPassword())
//                .about(userForm.getAbout())
//                .profilePic("https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?t=st=1729242374~exp=1729245974~hmac=00cd75cd094754dc8db4a0b421753bfafc4986ec1899c1b1a0c3e5f80bb807f4&w=740")
//                .build();
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setProfilePic("https://img.freepik.com/free-vector/businessman-character-avatar-isolated_24877-60111.jpg?t=st=1729242374~exp=1729245974~hmac=00cd75cd094754dc8db4a0b421753bfafc4986ec1899c1b1a0c3e5f80bb807f4&w=740");

        User savedUser = userService.saveUser(user);
        System.out.println("saved user: "+savedUser);
        //message = "registration successful"
        //redirect to login page
        return "redirect:/register";
    }
}
