package com.trickynguci.civicmessagerbackend.service.Impl;

import com.trickynguci.civicmessagerbackend.config.TokenGenerator;
import com.trickynguci.civicmessagerbackend.dto.LoginDTO;
import com.trickynguci.civicmessagerbackend.dto.SignupDTO;
import com.trickynguci.civicmessagerbackend.dto.TokenDTO;
import com.trickynguci.civicmessagerbackend.model.Token;
import com.trickynguci.civicmessagerbackend.model.User;
import com.trickynguci.civicmessagerbackend.repository.TokenRepository;
import com.trickynguci.civicmessagerbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenRepository tokenRepository;
    private final UserManager userManager;
    private final TokenGenerator tokenGenerator;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

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

    public TokenDTO register(SignupDTO signupDTO){
        User user = User.builder()
                .username(signupDTO.getUsername())
                .password(signupDTO.getPassword())
                .email(signupDTO.getEmail())
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


}
