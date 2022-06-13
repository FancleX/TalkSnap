package com.dev.userservice.controller;

import com.dev.response.GeneralResponse;
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
    // not null variables: nickname, email, password
    @GetMapping("/signup")
    public GeneralResponse<String> signup(@RequestBody User user) {
        return userService.signup(user);
    }

    // login
    // if first login, check if the email exited,
    // if yes, check password md5 hex code
    // if no, return email not found
    // if password is incorrect, return incorrect password
    // if all are correct, return token
    // post message: {email: xxx, password: xxx}
    @PostMapping("/login")
    public GeneralResponse<String> login(@PathVariable("email") String email, @PathVariable("password") String password) {
        return userService.login(email, password);
    }


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
