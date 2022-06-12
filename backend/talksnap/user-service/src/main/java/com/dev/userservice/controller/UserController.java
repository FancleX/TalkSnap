package com.dev.userservice.controller;

import com.dev.response.GeneralResponse;
import com.dev.response.HTTPResult;
import com.dev.user.User;
import com.dev.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // signup
    // check if the user is existed by email
    // if not existed store in database
    // otherwise give error message
    @RequestMapping("/signup")
    public GeneralResponse<String> signup() {
        return null;
    }

    // login
    // if first login, check if the email exited,
    // if yes, check password md5 hex code
    // if no, return email not found
    // if password is incorrect, return incorrect password
    // if all are correct, return token
    @RequestMapping("/login")
    public GeneralResponse<String> login() {return null;}


    @RequestMapping("/logout")
    public GeneralResponse<String> logout() {return null;}


    @RequestMapping("/edit/name")
    public GeneralResponse<String> editNickname() {
        return null;
    }
    
    @RequestMapping("edit/password")
    public GeneralResponse<String> editPassword() {return null;}


    @RequestMapping("edit/img")
    public GeneralResponse<String> editProfileImg() {return null;}

    @RequestMapping("/delete")
    public GeneralResponse<String> deleteAccount() {return null;}

}
