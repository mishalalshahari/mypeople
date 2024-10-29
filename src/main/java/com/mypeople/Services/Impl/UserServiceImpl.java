package com.mypeople.Services.Impl;

import com.mypeople.Entities.User;
import com.mypeople.Exceptions.ResourceNotFoundException;
import com.mypeople.Helpers.AppConstants;
import com.mypeople.Repositories.UserRepo;
import com.mypeople.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        //userid have to be generated
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        //password encode
        //user.setPassword(userId);
        //user.setProfilePic(user.getProfilePic());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //set the role
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        logger.info(user.getProvider().toString());
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User fetchedUser = userRepo.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("User not found!"));
        fetchedUser.setName(user.getName());
        fetchedUser.setEmail(user.getEmail());
        fetchedUser.setPhoneNumber(user.getPhoneNumber());
        fetchedUser.setPassword(user.getPassword());
        fetchedUser.setAbout(user.getAbout());
        fetchedUser.setProfilePic(user.getProfilePic());
        fetchedUser.setEnabled(user.isEnabled());
        fetchedUser.setEmailVerified(user.isEmailVerified());
        fetchedUser.setPhoneNumberVerified(user.isPhoneNumberVerified());
        fetchedUser.setProvider(user.getProvider());
        fetchedUser.setProviderUserId(user.getProviderUserId());

        //save the user in database
        User save = userRepo.save(fetchedUser);
        return Optional.ofNullable(save);

    }

    @Override
    public void deleteUser(String id) {
        User fetchedUser = userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found!"));
        userRepo.delete(fetchedUser);
    }

    @Override
    public boolean isUserExists(String userId) {
        User fetchUser = userRepo.findById(userId).orElse(null);
        return fetchUser != null ? true : false;
    }

    @Override
    public boolean isUserExistsByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
