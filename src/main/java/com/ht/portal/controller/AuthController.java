package com.ht.portal.controller;

import com.ht.portal.entity.JwtRequest;
import com.ht.portal.entity.JwtResponse;
import com.ht.portal.entity.RefreshTokenRequest;
import com.ht.portal.service.JwtService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest authenticationRequest) {
        try {
            JwtResponse jwtResponse = jwtService.createJwtToken(authenticationRequest, authenticationManager);
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponse> refreshTokenrefreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        JwtResponse jwtResponse = jwtService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(jwtResponse);
    }
    
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
//        try {
//            jwtService.logoutUser(refreshTokenRequest.getRefreshToken());
//            return ResponseEntity.ok("Logout successful");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Logout failed: " + e.getMessage());
//        }
//    }
    
    

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            jwtService.logoutUser(refreshTokenRequest.getRefreshToken());
            response.put("message", "Logout successful");
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8") // Force JSON Response
                    .body(response);  // Return JSON object, not a string
        } catch (Exception e) {
            response.put("message", "Logout failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json; charset=UTF-8") // Force JSON Error
                    .body(response);  // Return JSON error message
        }
    }
    
}
