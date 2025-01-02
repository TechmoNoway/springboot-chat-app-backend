package com.trickynguci.civicmessagerbackend.service.Impl;


import com.trickynguci.civicmessagerbackend.model.User;
import com.trickynguci.civicmessagerbackend.repository.UserRepository;
import com.trickynguci.civicmessagerbackend.service.Oath2JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class Oath2JwtServiceImpl implements Oath2JwtService {

    private final JwtDecoder jwtDecoder;

    private final UserRepository userRepository;

    public String extractUsername(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        User user = userRepository.findById(Integer.parseInt(jwt.getSubject()));
        return user.getUsername();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return Objects.requireNonNull(jwt.getExpiresAt()).isBefore(Instant.now());
    }
}

