package com.dev.userservice.service;

import com.dev.auth.Auth;
import com.dev.encryption.Encryption;
import com.dev.exception.InvalidAuthException;
import com.dev.response.GeneralResponse;
import com.dev.response.HTTPResult;
import com.dev.user.User;
import com.dev.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
     * @param data
     * @return
     */
    @Transactional
    public GeneralResponse<String> signup(Map<String, Object> data) {
        // check email
        String email = (String) data.get("email");
        Long id = userRepository.getUserIdByEmail(email);
        // if it is existed, return this email has registered
        if (id != null) {
            return HTTPResult.fail("This email has registered!");
        }
        // else encrypt user info
        // get the salt of the user
        String salt = Encryption.saltGenerater();
        // encrypt password
        String newPassword = Encryption.md5((String) data.get("password"), salt);
        // populate the user info and store it
        User user = new User();
        user.setNickname((String) data.get("nickname"));
        user.setEmail(email);
        user.setProfileImg(null);
        user.setJoinTime(new Date((long) data.get("joinTime")));
        user.setSalt(salt);
        user.setPassword(newPassword);
        user.setSubscriptions(null);
        userRepository.save(user);
        return HTTPResult.ok("Thanks for joining us!");
    }

    /**
     * login
     *
     * @param info user login information
     * @return pass, token if verify successfully, otherwise error message
     */
    @Transactional
    public GeneralResponse<String> login(Map<String, String> info) {
        // check token
        try {
            // get token
            String token = info.get("token");
            // verify token, if valid return pass else return 401 message
            Map<String, Object> payload = Auth.verify(token);
            if (payload != null) {
                // check if is a fake token
                Long userId = (Long) payload.get("userId");
                if (userRepository.existsById(userId)) {
                    return HTTPResult.ok("pass");
                }
            }
            throw new InvalidAuthException("Invalid or expired authorization.");
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
}
