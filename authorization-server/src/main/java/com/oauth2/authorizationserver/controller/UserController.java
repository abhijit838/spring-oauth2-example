package com.oauth2.authorizationserver.controller;

import com.oauth2.authorizationserver.model.User;
import com.oauth2.authorizationserver.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        user = customUserDetailsService.saveUserDetails(user);
        user.setPassword("[protected]");
        return user;
    }

    @GetMapping("/userInfo")
    public Principal userPrincipal(Principal principal) {
        return principal;
    }

    @RequestMapping(value="/logout", method = RequestMethod.POST)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "logged out";
    }
}
