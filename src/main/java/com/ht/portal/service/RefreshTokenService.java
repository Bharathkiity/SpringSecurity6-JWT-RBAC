package com.ht.portal.service;

import com.ht.portal.dao.RefreshTokenRepository;
import com.ht.portal.dao.UserRepository;
import com.ht.portal.entity.RefreshToken;
import com.ht.portal.entity.User;
import com.ht.portal.exception.TokenRefreshException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${jwt.refreshExpiration}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    // Find RefreshToken by token string
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    // Create a new refresh token for a user and delete the existing one if exists
    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        // First delete any existing token
        refreshTokenRepository.deleteByUserId(userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .revoked(false)
                .build();
        
        return refreshTokenRepository.save(refreshToken);
    }


    // Verify that the refresh token is not expired or revoked
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.isRevoked()) {
            throw new TokenRefreshException(token.getToken(), "Refresh token was revoked");
        }

        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired");
        }

        return token;
    }

    // Revoke a refresh token by user ID
    @Transactional
    public void revokeByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Delete the refresh token for the user
        refreshTokenRepository.deleteByUserId(userId);
    }
    
    
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
}
