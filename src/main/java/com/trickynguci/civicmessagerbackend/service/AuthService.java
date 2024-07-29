package com.trickynguci.civicmessagerbackend.service;

import com.trickynguci.civicmessagerbackend.dto.LoginDTO;
import com.trickynguci.civicmessagerbackend.dto.SignupDTO;
import com.trickynguci.civicmessagerbackend.dto.TokenDTO;
import com.trickynguci.civicmessagerbackend.model.User;

public interface AuthService {

    void saveUserToken(User user, String jwtToken);

    TokenDTO register(SignupDTO signupDTO);

    TokenDTO login(LoginDTO loginDTO);

}
