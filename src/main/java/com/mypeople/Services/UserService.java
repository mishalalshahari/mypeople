package com.mypeople.Services;

import com.mypeople.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> getUserById(String id);
    User getUserByEmail(String email);
    Optional<User> updateUser(User user);
    void deleteUser(String id);
    boolean isUserExists(String userId);
    boolean isUserExistsByEmail(String email);
    List<User> getAllUsers();

    //add more methods related to user service(logic)
}
