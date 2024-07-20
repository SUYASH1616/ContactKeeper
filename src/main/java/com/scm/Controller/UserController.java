package com.scm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    
    // Dashboard
    @GetMapping("/dashboard")
    public String getDashboard() {
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String getProfile() {
        return "user/profile";
    }
}
