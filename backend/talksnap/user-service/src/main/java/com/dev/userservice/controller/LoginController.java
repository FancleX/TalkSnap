package com.dev.userservice.controller;

import com.dev.response.GeneralResponse;
import com.dev.user.User;
import com.dev.userservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class LoginController {


    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    // signup
    // check if the user is existed by email
    // if not existed store in database
    // otherwise give error message
    // not null variables: nickname, email, password
    @PostMapping("/signup")
    public GeneralResponse<String> signup(@RequestBody User user) {
        return loginService.signup(user);
    }

    // login
    // if first login, check if the email exited,
    // if yes, check password md5 hex code
    // if no, return email not found
    // if password is incorrect, return incorrect password
    // if all are correct, return token
    // if login with token, verify the token
    // post message: {email: xxx, password: xxx} or {token: xxx}
    @PostMapping("/login")
    public GeneralResponse<String> login(@RequestBody Map<String, String> info) {
        return loginService.login(info);
    }

    /**
     * Log out will clear the issued token.
     *
     * @return
     */
    @RequestMapping("/logout")
    public GeneralResponse<String> logout() {return null;}


}
