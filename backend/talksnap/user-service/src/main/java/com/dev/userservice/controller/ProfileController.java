package com.dev.userservice.controller;

import com.dev.response.GeneralResponse;
import com.dev.user.Subscription;
import com.dev.userservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/user/profile")
public class ProfileController {


    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Fetch user info.
     *
     * @param auth
     * @return use info {nickname: xxx, email: xxx, profileImg: xxx, bio: xxx, bg_img: xxx, subscriptions: []}
     */
    @GetMapping("/fetchUser")
    public GeneralResponse<Map<String, Object>> fetchUser(@RequestHeader("Authorization") String auth) {
        return profileService.fetchUser(auth);
    }

    /**
     * Update the nickname.
     * need resign a token.
     *
     * @param auth
     * @param data
     * @return new token
     */
    @PutMapping("/edit/name")
    public GeneralResponse<Map<String, String>> editNickname(@RequestHeader("Authorization") String auth, @RequestBody Map<String, String> data) {
        return profileService.editNickname(auth, data);
    }

    /**
     * Update the email.
     *
     * @param auth
     * @param data
     * @return updated email
     */
    @PutMapping("/edit/email")
    public GeneralResponse<Map<String, String>> editEmail(@RequestHeader("Authorization") String auth, @RequestBody Map<String, String> data) {
        return profileService.editEmail(auth, data);
    }

    /**
     * Update the password.
     * {oldPassword:xxx, newPassword: xxx}
     *
     * @param auth
     * @param password
     * @return message
     */
    @PutMapping("edit/password")
    public GeneralResponse<String> editPassword(@RequestHeader("Authorization") String auth, @RequestBody Map<String, String> password) {
        return profileService.editPassword(auth, password);
    }

    /**
     * Update the bio.
     *
     * @param auth
     * @param data
     * @return new bio
     */
    @PutMapping("edit/bio")
    public GeneralResponse<Map<String, String>> editBio(@RequestHeader("Authorization") String auth, @RequestBody Map<String, String> data) {
        return profileService.editBio(auth, data);
    }

    /**
     * Handle user subscribe another user or unsubscribe a user
     * if the user exists in the subscription set
     *
     * @param auth
     * @param data { userId: xxx, nickname: xxx }
     * @return updated subscription set
     */
    @PutMapping("subscribe")
    public GeneralResponse<Map<String, Map<String, Set<Subscription>>>> subscribe(@RequestHeader("Authorization") String auth, @RequestBody Map<String, String> data) {
        return profileService.subscribe(auth, data);
    }

    /**
     * Delete user account.
     *
     * @param auth
     * @return message
     */
    @DeleteMapping("/delete")
    public GeneralResponse<String> deleteAccount(@RequestHeader("Authorization") String auth) {
        return profileService.deleteAccount(auth);
    }

    /**
     * Use for a user search another user,
     * return the queried user's profile.
     * look up username
     * profile will be {id:xxx, name:xxx}
     *
     * @param username
     * @return user's profile
     */
    @GetMapping("/search/{username}")
    public GeneralResponse<List<Map<String, Object>>> searchUser(@RequestHeader("Authorization") String auth, @PathVariable String username) {
        return profileService.searchUser(auth, username);
    }

    /**
     * Get a user profile by its id
     *
     * @param auth
     * @param id
     * @return user profile {nickname: xxx, profile_img: xxx, email: xxx, bio: xxx}
     */
    @GetMapping("/fetch/{id}")
    public GeneralResponse<Map<String, Object>> fetchUserProfileById(@RequestHeader("Authorization") String auth, @PathVariable Long id) {
        return profileService.fetchUserProfileById(auth, id);
    }


}
