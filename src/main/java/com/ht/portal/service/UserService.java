package com.ht.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ht.portal.dao.RoleRepository;
import com.ht.portal.dao.UserRepository;
import com.ht.portal.entity.JwtResponse;
import com.ht.portal.entity.RefreshToken;
import com.ht.portal.entity.Role;
import com.ht.portal.entity.User;
import com.ht.portal.exception.ResourceNotFoundException;
import com.ht.portal.util.JwtUtil;

import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Transactional
    public JwtResponse registerNewUser(User user) throws Exception {
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new IllegalArgumentException("Username already exists");
        }

        Role userRole = roleRepository.findByRoleName("USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleName("USER");
                    return roleRepository.save(newRole);
                });

        user.setRole(userRole);
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

        User savedUser = userRepository.save(user);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
            savedUser.getUserName(),
            savedUser.getUserPassword(),
            getAuthorities(savedUser)
        );

        String accessToken = jwtUtil.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(savedUser.getId());

        return JwtResponse.builder()
                .userName(savedUser.getUserName())
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .role(savedUser.getRole().getRoleName())
                .build();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getUserPassword(),
                getAuthorities(user)
        );
    }

    private Set<SimpleGrantedAuthority> getAuthorities(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName()));
        return authorities;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // retrieves logged-in username
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User"));
    }

    // ======= Added/Fixed Admin Functions Below ========

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    public User updateUser(String username, User updatedUser) {
        User existingUser = userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        // Update fields as needed
        existingUser.setUserName(updatedUser.getUserName() != null ? updatedUser.getUserName() : existingUser.getUserName());
        existingUser.setUserPassword(updatedUser.getUserPassword() != null ?
                passwordEncoder.encode(updatedUser.getUserPassword()) : existingUser.getUserPassword());
        if (updatedUser.getRole() != null) {
            Role role = roleRepository.findByRoleName(updatedUser.getRole().getRoleName())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + updatedUser.getRole().getRoleName()));
            existingUser.setRole(role);
        }

        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        // Delete refresh token if it exists
        refreshTokenService.deleteByUser(user);

        // Then delete the user
        userRepository.delete(user);
    }


    // ... other existing methods if any
}
