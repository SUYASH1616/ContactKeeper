package com.scm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.Entity.Contact;
import com.scm.Services.ContactService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class ApiController {
    
    @Autowired
    private ContactService contactService;

    @GetMapping("/contacts/{contactId}")
     public Contact geContact(@PathVariable String contactId){
        return contactService.getById(contactId);
     }
}