package com.fm.smartlearningplatform.security.service;



import com.fm.smartlearningplatform.model.user.RefreshToken;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.repository.user.RefreshTokenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    public RefreshToken createRefreshToken(User user)
    {
        RefreshToken refreshToken = RefreshToken.builder()
                        .token(UUID.randomUUID().toString())
                        .expiresAt(LocalDateTime.now().plusDays(7))
                        .revoked(false)
                        .user(user)
                        .build();
        return refreshTokenRepository.save(refreshToken);
    }
    public RefreshToken validateToken(String token)
    {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                        .orElseThrow();

        if (refreshToken.getRevoked())
        {
            throw new RuntimeException("Refresh token revoked");
        }
        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now()))
        {
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;
    }

    @Transactional
    public void revokeToken(RefreshToken token)
    {
        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }
    @Transactional
    public RefreshToken rotateToken(RefreshToken oldToken
    ) {
        oldToken.setRevoked(true);
        refreshTokenRepository.save(oldToken);
        RefreshToken newToken =
                RefreshToken.builder()
                        .token(UUID.randomUUID().toString())
                        .expiresAt(LocalDateTime.now().plusDays(7))
                        .revoked(false)
                        .user(oldToken.getUser())
                        .build();
        return refreshTokenRepository.save(newToken);
    }
    @Transactional
    public void revokeAllTokens(Long userId)
    {
        List<RefreshToken> tokens =
                refreshTokenRepository.findByUserIdAndRevokedFalse(userId);
        tokens.forEach(token -> token.setRevoked(true));
        refreshTokenRepository.saveAll(tokens);
    }
}
