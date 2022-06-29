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

import java.util.HashMap;
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
            User user = userRepository.findById(id).get();
            Map<String, Object> userProfile = new HashMap<>();
            userProfile.put("nickname", user.getNickname());
            userProfile.put("email", user.getEmail());
            userProfile.put("profile_img", user.getProfileImg());
            userProfile.put("bio", user.getBio());
            userProfile.put("bg_img", user.getBackgroundImg());
            userProfile.put("subscriptions", user.getSubscriptions());
            return HTTPResult.ok(userProfile);
        }
        throw new InvalidAuthException("Invalid or expired authorization.");
    }

    @Transactional
    public GeneralResponse<Map<String, String>> editNickname(String auth, Map<String, String> data) {
        // verify the token
        Map<String, Object> payload = Auth.verify(auth);
        if (payload != null) {
            // get user id
            Long id = (Long) payload.get("userId");
            // parse data
            String name = data.get("nickname");
            userRepository.updateNickname(name, id);
            // update token
            String newToken = Auth.generateToken(id, name);
            Map<String, String> result = new HashMap<>();
            result.put("token", newToken);
            result.put("nickname", name);
            return HTTPResult.ok(result);
        }
        throw new InvalidAuthException("Invalid or expired authorization.");
    }

    @Transactional
    public GeneralResponse<Map<String, String>> editEmail(String auth, Map<String, String> data) {
        // verify the token
        Map<String, Object> payload = Auth.verify(auth);
        if (payload != null) {
            // get user id
            Long id = (Long) payload.get("userId");
            String email = data.get("email");
            userRepository.updateEmail(email, id);
            Map<String, String> result = new HashMap<>();
            result.put("email", email);
            return HTTPResult.ok(result);
        }
        throw new InvalidAuthException("Invalid or expired authorization.");
    }

    @Transactional
    public GeneralResponse<String> editPassword(String auth, Map<String, String> password) {
        // verify the token
        Map<String, Object> payload = Auth.verify(auth);
        if (payload != null) {
            // get user id
            Long id = (Long) payload.get("userId");
            // get the old password of the user
            String oldPassword = userRepository.getPasswordById(id);
            // get md5 of the old password of the input
            // query salt of the user
            String salt = userRepository.getUserSaltById(id);
            String oldPasswordFromInput = password.get("oldPassword");
            oldPasswordFromInput = Encryption.md5(oldPasswordFromInput, salt);
            // check if the old password is correct
            if (oldPassword.equals(oldPasswordFromInput)) {
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
        throw new InvalidAuthException("Invalid or expired authorization.");
    }

    @Transactional
    public GeneralResponse<Map<String, String>> editBio(String auth, Map<String, String> data) {
        // verify the token
        Map<String, Object> payload = Auth.verify(auth);
        if (payload != null) {
            // get user id
            Long id = (Long) payload.get("userId");
            String bio = data.get("bio");
            userRepository.updateBio(bio, id);
            Map<String, String> result = new HashMap<>();
            result.put("bio", bio);
            return HTTPResult.ok(result);
        }
        throw new InvalidAuthException("Invalid or expired authorization.");
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
        throw new InvalidAuthException("Invalid or expired authorization.");
    }

    public GeneralResponse<List<Map<String, Object>>> searchUser(String auth, String username) {
        // verify the token
        Map<String, Object> payload = Auth.verify(auth);
        if (payload != null) {
            // get user id
            Long id = (Long) payload.get("userId");
            // query the name and filter the user self
            List<Map<String, Object>> userList = userRepository.searchUser(id, username);
            return HTTPResult.ok(userList);
        }
        throw new InvalidAuthException("Invalid or expired authorization.");
    }

    public GeneralResponse<Map<String, Object>> fetchUserProfileById(String auth, Long id) {
        // verify the token
        Map<String, Object> payload = Auth.verify(auth);
        if (payload != null) {
            // query the name and filter the user self
            Map<String, Object> userProfile = userRepository.fetchUserProfileById(id);
            return HTTPResult.ok(userProfile);
        }
        throw new InvalidAuthException("Invalid or expired authorization.");
    }

}
