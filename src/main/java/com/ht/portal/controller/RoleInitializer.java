package com.ht.portal.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ht.portal.dao.RoleRepository;
import com.ht.portal.dao.UserRepository;
import com.ht.portal.entity.Role;
import com.ht.portal.entity.User;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RoleInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize default roles if they don't exist
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setRoleName("ADMIN");  // Set role name for Admin

            Role userRole = new Role();
            userRole.setRoleName("USER");  // Set role name for User

            roleRepository.save(userRole);  // Save the user role
            roleRepository.save(adminRole);  // Save the admin role

            System.out.println("Default roles (ROLE_USER, ROLE_ADMIN) initialized successfully ✅");
        } else {
            System.out.println("Roles already exist. Skipping role initialization.");
        }

        // Create 'admin' user if it doesn't exist
        userRepository.findByUserName("admin").orElseGet(() -> {
            // Ensure the 'admin' role is loaded from the repository after saving
            Role adminRole = roleRepository.findByRoleName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

            User adminUser = new User();
            adminUser.setUserName("admin");  // Set username for the admin
            adminUser.setUserPassword(passwordEncoder.encode("admin123"));  // Default password
            adminUser.setRole(adminRole);  // Assign the 'admin' role to the user
            return userRepository.save(adminUser);  // Save the admin user
        });

        System.out.println("Admin user (admin) initialized successfully ✅");
    }
}
