package com.scm.Services;
import java.util.*;
import com.scm.Entity.Contact;
import com.scm.Entity.User;

public interface ContactService {
    Contact save(Contact contact);
    Contact updateContact(Contact contact);
    List<Contact>getAll();
    Contact getById(String id);
    void delete(String id);
    List<Contact>search(String name,String email,String phoneNumber);
    List<Contact>getByUserId(String userId);
    List<Contact>getByUser(User user);
}
