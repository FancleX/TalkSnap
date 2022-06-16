package com.dev.userservice.service;

import com.dev.auth.Auth;
import com.dev.encryption.Encryption;
import com.dev.response.GeneralResponse;
import com.dev.response.HTTPResult;
import com.dev.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    @Autowired
    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public GeneralResponse<Map<String, Object>> fetchUser(String token) {
        // verify the token
        Map<String, Object> payload = Auth.verify(token);
        if (payload != null) {
            // get id
            Long id = (Long) payload.get("userId");
            // query the user
            Map<String, Object> userProfile = userRepository.getUserProfileById(id);
            return HTTPResult.ok(userProfile);
        }
        return HTTPResult.fail("Please login.");
    }

    public GeneralResponse<String> editNickname(String auth, String name) {
        // verify the token
        Map<String, Object> payload = Auth.verify(auth);
        if (payload != null) {
            // get user id
            Long id = (Long) payload.get("userId");
            userRepository.updateNickname(name, id);
            // update token
            String newToken = Auth.generateToken(id, name);
            return HTTPResult.ok(newToken);
        }
        return HTTPResult.fail("Please login.");
    }

    public GeneralResponse<String> editEmail(String auth, String email) {
        // verify the token
        Map<String, Object> payload = Auth.verify(auth);
        if (payload != null) {
            // get user id
            Long id = (Long) payload.get("userId");
            userRepository.updateEmail(email, id);
            return HTTPResult.ok(email);
        }
        return HTTPResult.fail("Please login.");
    }

    public GeneralResponse<String> editPassword(String auth, Map<String, String> password) {
        // verify the token
        Map<String, Object> payload = Auth.verify(auth);
        if (payload != null) {
            // get user id
            Long id = (Long) payload.get("userId");
            // get the old password of the user
            String oldPassword = userRepository.getPasswordById(id);
            // check if the old password is correct
            String oldPasswordFromInput = password.get("oldPassword");
            if (oldPassword.equals(oldPasswordFromInput)) {
                // query salt of the user
                String salt = userRepository.getUserSaltById(id);
                // encrypt new password
                String newPassword = password.get("newPassword");
                String md5Password = Encryption.md5(newPassword, salt);
                // update the password
                userRepository.updatePassword(md5Password, id);
                return HTTPResult.ok("Done.");
            }
            // if the old password is incorrect
            return HTTPResult.fail("The password is incorrect.");
        }
        return HTTPResult.fail("Please login.");
    }

    public GeneralResponse<String> deleteAccount(String auth) {
        // verify the token
        Map<String, Object> payload = Auth.verify(auth);
        if (payload != null) {
            // get user id
            Long id = (Long) payload.get("userId");
            userRepository.deleteById(id);
            return HTTPResult.ok("Your account has been deleted.");
        }
        return HTTPResult.fail("Please login.");
    }

    public GeneralResponse<List<Map<String, Object>>> searchUser(String username) {
        // query the name
        List<Map<String, Object>> userList = userRepository.searchUser(username);
        return HTTPResult.ok(userList);
    }

}
