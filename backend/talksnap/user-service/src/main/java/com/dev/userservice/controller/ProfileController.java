package com.dev.userservice.controller;

import com.dev.response.GeneralResponse;
import com.dev.userservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/profile")
@CrossOrigin
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
     * @return use info {nickname: xxx, email: xxx, profileImg: xxx}
     */
    @GetMapping("fetchUser")
    public GeneralResponse<Map<String, Object>> fetchUser(@RequestHeader("Authorization") String auth) {
        return profileService.fetchUser(auth);
    }

    /**
     * Update the nickname.
     * need resign a token.
     *
     * @param auth
     * @param name
     * @return new token
     */
    @PutMapping("/edit/name")
    public GeneralResponse<String> editNickname(@RequestHeader("Authorization") String auth, @RequestBody String name) {
        return profileService.editNickname(auth, name);
    }

    /**
     * Update the email.
     *
     * @param auth
     * @param email
     * @return updated email
     */
    @PutMapping("/edit/email")
    public GeneralResponse<String> editEmail(@RequestHeader("Authorization") String auth, @RequestBody String email) {
        return profileService.editEmail(auth, email);
    }

    /**
     * Update the password.
     * {oldPassword:xxx, newPassword}
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
     * profile will be  {id:xxx, name:xxx, profileImg:xxx}
     *
     * @param username
     * @return user's profile
     */
    @GetMapping("/search/{username}")
    public GeneralResponse<List<Map<String, Object>>> searchUser(@PathVariable String username) {
        return profileService.searchUser(username);
    }



}
