package com.scm.Controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.Entity.Contact;
import com.scm.Entity.User;
import com.scm.Forms.ContactForm;
import com.scm.Services.ContactService;
import com.scm.Services.ImageService;
import com.scm.Services.userService;
import com.scm.helper.AppConstants;
import com.scm.helper.Helper;
import com.scm.helper.Message;
import com.scm.helper.MessageType;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private userService userService1;
    @Autowired
    private ContactService contactService;
    @Autowired
    private ImageService imageService;

    @RequestMapping("/add")
    public String requestMethodName(Model model) {

        ContactForm contactForm=new ContactForm();
        // contactForm.setFavorite(true);
        // contactForm.setName("Suyash");
        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    @PostMapping("/add")
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult result,Authentication authentication,HttpSession session) {
        
        // System.out.println(contactForm);

        if (result.hasErrors()) {
            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }
        // System.out.println(contactForm.getProFileImag());
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService1.getEmailByUser(username);
        String fileName=UUID.randomUUID().toString();
        String fileURL=imageService.uploadImage(contactForm.getProFileImag(),fileName);
        Contact contact = new Contact();

        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebSiteLink(contactForm.getWebSiteLink());
        contact.setPicture(fileURL);
        contact.setCloudinary(fileName);
        contactService.save(contact);

        session.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new contact")
                        .type(MessageType.green)
                        .build());
                        
        return "redirect:/user/contacts/add";
    }
    
     @RequestMapping
    public String viewContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
           
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
            Authentication authentication) {

        // load all the user contacts
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService1.getEmailByUser(username);

        List<Contact> contacts=contactService.getByUser(user);

        // Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("contacts", contacts);
        // model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        // model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "user/contacts";
    }

    // search handler

    @RequestMapping("/delete/{id}")
    // @PathVariable(xyz) if name id not common
    public String deleteContact(@PathVariable String id,HttpSession session){
        contactService.delete(id);
        System.out.println(id+"data being deleted");
         Message message = Message.builder().content("Deleted successfully").type(MessageType.green).build();
        session.setAttribute("message", message);
        return "redirect:/user/contacts";
    }
   
    //while updating show view so that user can update which ever he wants 
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(
            @PathVariable("contactId") String contactId,
            Model model) {

        var contact = contactService.getById(contactId);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebSiteLink(contact.getWebSiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());
        ;
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";
    }

    @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
            @Valid @ModelAttribute ContactForm contactForm,
            BindingResult bindingResult,
            Model model) {

        // update the contact
        if (bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }

        var con = contactService.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setWebSiteLink(contactForm.getWebSiteLink());
        con.setLinkedInLink(contactForm.getLinkedInLink());

        // process image:

        if (contactForm.getProFileImag() != null && !contactForm.getProFileImag().isEmpty()) {
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getProFileImag(), fileName);
            con.setCloudinary(fileName);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);

        } else {
            System.out.println("file is empty");
        }

        var updateCon = contactService.updateContact(con);
        
        model.addAttribute("message", Message.builder().content("Contact Updated !!").type(MessageType.green).build());

        return "redirect:/user/contacts/view/" + contactId;
    }


}
