package com.scm.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;

import com.scm.Entity.User;
import com.scm.Services.userService;
import com.scm.helper.Helper;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private userService userService1;

    @ModelAttribute
    public void addloggedInUser(Model model,Authentication authentication){
        String userName=Helper.getEmailOfLoggedInUser(authentication);
        User user=userService1.getEmailByUser(userName);

        System.out.println(user.getEmail());
        System.out.println(user.getName());

        model.addAttribute("loggedInUser", user);

    }
    
    // Dashboard
    @GetMapping("/dashboard")
    public String getDashboard() {
        return "user/dashboard";
    }

    

    @GetMapping("/profile")
    public String getProfile(Model model,Authentication authentication) {
        // here each provider returns differnt principal(Authentication<-(implements)<-OAuth2AuthenticatedPrincipal<-(implements)<x`-principal) so we need to find it out.
       
        return "user/profile";
    }
}
