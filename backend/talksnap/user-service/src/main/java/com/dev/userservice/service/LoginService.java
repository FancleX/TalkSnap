package com.dev.userservice.service;

import com.dev.auth.Auth;
import com.dev.encryption.Encryption;
import com.dev.response.GeneralResponse;
import com.dev.response.HTTPResult;
import com.dev.user.User;
import com.dev.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class LoginService {

    private final UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
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
        if (email != null) {
            return HTTPResult.fail("This email has registered!");
        }
        // else encrypt user info
        // get the salt of the user
        String salt = Encryption.saltGenerater();
        // encrypt password
        String newPassword = Encryption.md5(user.getPassword(), salt);
        // check user profile picture, if doesn't exist give a default img url
        if (user.getProfileImg() == null) {
            user.setProfileImg("default".getBytes());
        }
        // refill user info and store it
        user.setSalt(salt);
        user.setPassword(newPassword);
        userRepository.save(user);
        return HTTPResult.ok("Thanks for joining us!");
    }

    /**
     * login
     *
     * @param info user login information
     * @return pass, token if verify successfully, otherwise error message
     */
    public GeneralResponse<String> login(Map<String, String> info) {
        // check token
        try {
            // get token
            String token = info.get("token");
            // verify token, if valid return pass else return error message
            if (Auth.verify(token) != null) {
                return HTTPResult.ok("pass");
            }
            return HTTPResult.fail("Please login.");
        } catch (NullPointerException e) {
            // if the user doesn't own a token now
            // query email
            String email = info.get("email");
            Long id = userRepository.getUserIdByEmail(email);
            // if id exists
            if (id != null) {
                // else check password
                User user = userRepository.findById(id).get();
                // input password
                String inputPassword = info.get("password");
                // database password
                String password = user.getPassword();
                // get md5 of the input password
                String md5Password = Encryption.md5(inputPassword, user.getSalt());
                // if pass return token
                if (password.equals(md5Password)) {
                    // generate a token for this user
                    String token = Auth.generateToken(id, user.getNickname());
                    return HTTPResult.ok(token);
                }
            }
            // else return error message
            return HTTPResult.fail("The email or password is incorrect.");
        }
    }


    public GeneralResponse<String> editPassword(Map<String, String> name) {
        return null;
    }


}
