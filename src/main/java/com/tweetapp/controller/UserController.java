package com.tweetapp.controller;

import com.tweetapp.model.User;
import com.tweetapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    public static final String LOGOUT_FAILED = "logout failed";

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/logout")
    public String logout(@RequestBody User user) {

        if(userService.logOut(user.getEmail())) {
            log.info("logged out");
            return "logged out";
        }
        log.info(LOGOUT_FAILED);
        return LOGOUT_FAILED;
    }

    @GetMapping("/api/v1.0/tweets/users/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/resetPassword")
    public User resetPassword(@RequestBody User user) {
        return userService.resetPassword(user);
    }
}
