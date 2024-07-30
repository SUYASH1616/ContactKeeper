package com.scm.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.Entity.User;
import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<User,String> {
    // JpaRepository<User,String> <entity,primart ket type>
    // provide several method to dirct intract with table like save ,findId etc.
    
    // it provides large set of queries(default like findById) 
    // this custom finders methods
    // this are extra quries(need not to define implementation spring boot will automaticlly define it once u declared in this interface)
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailToken(String email);
    Optional<User> findByEmailAndPassword(String email,String password);
    
}
