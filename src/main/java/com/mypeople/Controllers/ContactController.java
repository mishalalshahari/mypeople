package com.mypeople.Controllers;

import com.mypeople.Entities.Contact;
import com.mypeople.Entities.User;
import com.mypeople.Forms.ContactForm;
import com.mypeople.Helpers.AuthenticatedUser;
import com.mypeople.Services.ContactService;
import com.mypeople.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    //add contact page: handler
    public String addContactView(Model model) {

        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        contactForm.setName("John Doe");
        contactForm.setFavorite(true);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@ModelAttribute ContactForm contactForm, Authentication authentication) {
        //process the form data
        String username = AuthenticatedUser.getEmailOfAuthenticatedUser(authentication);

        //validate form
        //to do
        //form --> contact
        User user = userService.getUserByEmail(username);

        Contact contact = new Contact();
        contact.setUser(user);
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setGithubLink(contactForm.getGithubLink());
        contact.setDescription(contactForm.getDescription());
        //process the contact picture
        //contact.setProfilePic();
        contact.setFavorite(contactForm.isFavorite());
        contactService.save(contact);
        System.out.println(contactForm);
        //set a message on view
        return "redirect:/user/contacts/add";
    }
}
