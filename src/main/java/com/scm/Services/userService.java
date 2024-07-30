package com.scm.Services;
import java.util.*;
import java.util.Optional;

import com.scm.Entity.User;

public interface userService {
    User saveUser(User user);
    // optinal automatically handels if there is empty list
    Optional<User> getUserById(String id);
    Optional<User> updateUser(User user);
    void deleteUser(String id);
    boolean isUserExist(String id);
    boolean isUserExistByEmail(String email);
    List<User>getAllUsers();
    User getEmailByUser(String email);

}
