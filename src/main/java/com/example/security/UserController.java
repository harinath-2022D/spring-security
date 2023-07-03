package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class UserController {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/welcome")
    public String getWelcome(){
        return "Hi you are in welcome page";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getAdmin(){
        return "Hi you are in admin page";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getUser(){
        return "Hi you are int user page";
    }

    @PostMapping("/user/add")
    public String addUser(@RequestBody UserInfo userInfo){

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "user has been added successfully to db";
    }
}