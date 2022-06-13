package com.dev.userservice.service;

import com.dev.encryption.Encryption;
import com.dev.response.GeneralResponse;
import com.dev.response.HTTPResult;
import com.dev.user.User;
import com.dev.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * signup
     *
     * @param user
     * @return
     */
    @Transactional
    public GeneralResponse<String> signup(User user) {
        // check email
        Long email = userRepository.getUserIdByEmail(user.getEmail());
        // if it is existed, return this email has registered
        if (email == null) {
            return HTTPResult.fail("This email has registered!");
        }
        // else encrypt user info
        // get the salt of the user
        String salt = Encryption.saltGenerater();
        // encrypt password
        String newPassword = Encryption.md5(user.getPassword(), salt);
        // check user profile picture, if doesn't exist give a default img url
        if (user.getProfilePicture() == null) {
            user.setProfilePicture("default");
        }
        // refill user info and store it
        user.setSalt(salt);
        user.setPassword(newPassword);
        userRepository.save(user);
        return HTTPResult.ok("Thanks for joining us!");
    }

    public GeneralResponse<String> login(String email, String password) {
        return null;
    }


}
