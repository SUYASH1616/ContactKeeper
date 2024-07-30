package com.scm.Repositories;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.Entity.Contact;
import com.scm.Entity.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact,String> {
    // find the contact by user
    // custom finder method
    List<Contact> findByUser(User user);

    // custom query method
    // The method takes a String parameter annotated with @Param("userId"). 
    // This annotation maps the method parameter userId to the :userId placeholder in the query.
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);
}
