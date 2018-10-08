package com.oauth2.authorizationserver.controller;/*
 * Created by amaity at 10/2/2018
 */

import com.oauth2.authorizationserver.model.User;
import com.oauth2.authorizationserver.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class AuthorizationResourceController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return customUserDetailsService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public Optional<User> getUser(@PathVariable("id") Long id) {
        return customUserDetailsService.getUser(id);
    }

    @GetMapping("/deprecate/{id}")
    public Optional<User> deprecateUser(@PathVariable("id") Long id) {
        return customUserDetailsService.getUser(id);
    }

    // TODO: Register Client
    // TODO: Get Client Details
    // TODO: Delete Client
}
