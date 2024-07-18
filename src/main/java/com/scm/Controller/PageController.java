package com.scm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.Entity.User;
import com.scm.Forms.UserForm;
import com.scm.Services.userService;
import com.scm.helper.Message;
import com.scm.helper.MessageType;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;



// says it is controller
@Controller
public class PageController {

    @Autowired
    private userService userService1;

    //to specify url
   @RequestMapping("/about")

// org.springframework.ui.Model;
   public String aboutPage(Model model){

        // to pass values in front end i.e to view
      //   model.addAttribute("name", "Substring Technologies");
        
        // return should be same as html page to be render
        return "about";
   }
   
   @RequestMapping("/services")

   public String servicePage(Model model){
      return "services";
   }

   @RequestMapping("/home")
   
   public String homePage() {
       return "home";
   }

   @RequestMapping("/register")
   
   public String registerPage(Model model) {
        UserForm userForm=new UserForm();
        model.addAttribute("userForm", userForm);
       return "register";
   }

   @RequestMapping("/login")
   
   public String loginPage() {
       return "login";
   }

   @GetMapping("/contact")
   
   public String contactPage() {
       return "contact";
   }

   @RequestMapping(value = "/do-register", method=RequestMethod.POST)
   public String processRegister(@ModelAttribute UserForm userForm,HttpSession session) {
    
    // Binds request parameters to a method parameter
        // User user=User.builder()
        //     .name(userForm.getName())
        //     .email(userForm.getEmail())
        //     .password(userForm.getPassword())
        //     .about(userForm.getAbout())
        //     .phoneNumber(userForm.getPhoneNumber())
        //     .profilePic("https://wallpapersafari.com/boy-profile-wallpapers/")
        //     .build();
        User user=new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://wallpapersafari.com/boy-profile-wallpapers/");

        
        User savedUser=userService1.saveUser(user);
        System.out.println("Saved");
        Message message=Message.builder().content("Registration successfully").type(MessageType.green).build();
        session.setAttribute("message", message);
       return "redirect:/register";
   }
   

   

}
