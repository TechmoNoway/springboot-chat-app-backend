package com.trickynguci.civicmessagerbackend.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface Oath2JwtService  {

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

}
