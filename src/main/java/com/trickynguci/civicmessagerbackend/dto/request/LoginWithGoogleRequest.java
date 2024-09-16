package com.trickynguci.civicmessagerbackend.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginWithGoogleRequest {
    private String username;
    private String email;
    private String accessToken;
}
