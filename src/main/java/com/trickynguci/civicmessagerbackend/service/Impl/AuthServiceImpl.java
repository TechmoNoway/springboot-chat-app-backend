package com.trickynguci.civicmessagerbackend.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trickynguci.civicmessagerbackend.config.TokenGenerator;
import com.trickynguci.civicmessagerbackend.dto.LoginDTO;
import com.trickynguci.civicmessagerbackend.dto.request.LoginWithGoogleRequest;
import com.trickynguci.civicmessagerbackend.dto.SignupDTO;
import com.trickynguci.civicmessagerbackend.dto.TokenDTO;
import com.trickynguci.civicmessagerbackend.dto.response.GoogleUserInfoResponse;
import com.trickynguci.civicmessagerbackend.model.Token;
import com.trickynguci.civicmessagerbackend.model.User;
import com.trickynguci.civicmessagerbackend.repository.TokenRepository;
import com.trickynguci.civicmessagerbackend.repository.UserRepository;
import com.trickynguci.civicmessagerbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenRepository tokenRepository;
    private final UserManager userManager;
    private final TokenGenerator tokenGenerator;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final UserRepository userRepository;

    public void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .userId(user.getId())
                .token(jwtToken)
//                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public TokenDTO register(SignupDTO signupDTO) {
        User user = User.builder()
                .username(signupDTO.getUsername())
                .password(signupDTO.getPassword())
                .email(signupDTO.getEmail())
                .roleId(1)
                .isActive(true)
                .isBlocked(false)
                .build();
        userManager.createUser(user);
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, signupDTO.getPassword(), Collections.emptyList());
        TokenDTO tokenDTO = tokenGenerator.createToken(authentication);
        saveUserToken(user, tokenDTO.getAccessToken());

        return tokenDTO;
    }

    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        Authentication authentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(loginDTO.getUsername(), loginDTO.getPassword()));

        return tokenGenerator.createToken(authentication);
    }

    @Override
    public TokenDTO loginWithGoogle(LoginWithGoogleRequest loginWithGoogleRequest) throws GeneralSecurityException, IOException {
        String apiUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + loginWithGoogleRequest.getAccessToken());

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ObjectMapper objectMapper = new ObjectMapper();
            GoogleUserInfoResponse googleUserInfoResponse = objectMapper.readValue(response.toString(), GoogleUserInfoResponse.class);

            String email = googleUserInfoResponse.getEmail();
            String avatarUrl = googleUserInfoResponse.getPicture();
            String name = googleUserInfoResponse.getName();

            if (!userRepository.existsByUsername(email)) {
                int index = email.indexOf('@');
                User user = User.builder()
                        .username(email.substring(0, index))
                        .password(email)
                        .email(email)
                        .avatarUrl(avatarUrl)
                        .roleId(1)
                        .isActive(true)
                        .isBlocked(false)
                        .build();
                userManager.createUser(user);

                Authentication registingAuthentication = UsernamePasswordAuthenticationToken.authenticated(user, user.getPassword(), Collections.emptyList());
                TokenDTO tokenDTO = tokenGenerator.createToken(registingAuthentication);
                saveUserToken(user, tokenDTO.getAccessToken());

                Authentication loginAuthentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(email, email));
                return tokenGenerator.createToken(loginAuthentication);
            } else {
                Optional<User> user = userRepository.findByUsername(email);
                Authentication loginAuthentication = daoAuthenticationProvider.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(user.get().getUsername(), user.get().getEmail()));
                return tokenGenerator.createToken(loginAuthentication);
            }
        } else {
            return null;
        }
    }
}
