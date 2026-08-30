package com.niraj.education.user.service;

import com.niraj.education.user.entity.RefreshToken;
import com.niraj.education.user.entity.User;
import com.niraj.education.user.exception.RefreshTokenException;
import com.niraj.education.user.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(User user) {

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(
                Instant.now().plusSeconds(7 * 24 * 60 * 60)
        );

        return refreshTokenRepository.save(refreshToken);
    }


    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().isBefore(Instant.now())){

            refreshTokenRepository.delete(token);

            throw new RefreshTokenException("Refresh token expired");
        }
        return token;
    }
}