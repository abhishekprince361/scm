package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    
    @Value("${defaultProfile.picture}")
    private String pic;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }


    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("home");
        model.addAttribute("name", "Prince");
        model.addAttribute("title", "Singh");
        model.addAttribute("add", "Delhi");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("About page Loading");
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("Services page loading");
        return "services";
    }

    @GetMapping("/contact")
    public String contactPage() {
        System.out.println("Contact page running");
        return "contact";
    }

    @PostMapping("/login")
    public String login() {
        System.out.println("login page running");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        System.out.println("register");
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session) {
        System.out.println("Processing registration");
        System.out.println(userForm);

        if(rBindingResult.hasErrors()){
            return "register";
        }
        
        
        // builder() is not gettting default value form class 
        // User user = User.builder()
        //         .name(userForm.getName())
        //         .email(userForm.getEmail())
        //         .password(userForm.getPassword())
        //         .phoneNumber(userForm.getPhoneNumber())
        //         .about(userForm.getPhoneNumber())
        //         .profilePic(pic)
        //         .build();


        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());
        user.setProfilePic(pic);

        User savedUser = userService.saveUser(user);
        System.out.println("User Saved");

        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();

        session.setAttribute("message", message);

        return "redirect:/register";
    }

}
