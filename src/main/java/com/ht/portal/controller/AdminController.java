package com.ht.portal.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ht.portal.entity.User;
import com.ht.portal.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    // Admin can view all users
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users == null || users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No users found");
        }
        return users;
    }

    // Admin can view a specific user by username
    @GetMapping("/users/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    // Admin can update a user's information
    @PutMapping("/users/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public User updateUser(@PathVariable String username, @RequestBody User user) {
        return userService.updateUser(username, user);
    }


    // Admin can delete a user
    @DeleteMapping("/users/{username}")
   @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

    // Admin can view the admin dashboard
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "Welcome to Admin Dashboard";
    }
}
