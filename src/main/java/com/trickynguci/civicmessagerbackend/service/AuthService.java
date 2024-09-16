package com.trickynguci.civicmessagerbackend.service;

import com.trickynguci.civicmessagerbackend.dto.LoginDTO;
import com.trickynguci.civicmessagerbackend.dto.request.LoginWithGoogleRequest;
import com.trickynguci.civicmessagerbackend.dto.SignupDTO;
import com.trickynguci.civicmessagerbackend.dto.TokenDTO;
import com.trickynguci.civicmessagerbackend.dto.response.GoogleLoginResponse;
import com.trickynguci.civicmessagerbackend.model.User;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface AuthService {

    void saveUserToken(User user, String jwtToken);

    TokenDTO register(SignupDTO signupDTO);

    TokenDTO login(LoginDTO loginDTO);

    TokenDTO loginWithGoogle(LoginWithGoogleRequest loginWithGoogleRequest) throws GeneralSecurityException, IOException;


}
