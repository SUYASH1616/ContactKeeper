package com.scm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.Entity.User;
import com.scm.Services.userService;
import com.scm.helper.Helper;

import lombok.Data;

@ControllerAdvice
public class RootController {

    @Autowired
    private  userService userService1;
    @ModelAttribute
    public void addloggedInUser(Model model,Authentication authentication){
        if(authentication==null){
            return;
        }
        String userName=Helper.getEmailOfLoggedInUser(authentication);
        User user=userService1.getEmailByUser(userName);

        System.out.println(user.getEmail());
        System.out.println(user.getName());

        model.addAttribute("loggedInUser", user);

    }

   
}
