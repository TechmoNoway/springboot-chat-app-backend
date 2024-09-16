package com.trickynguci.civicmessagerbackend.dto.response;


import lombok.Data;

@Data
public class GoogleUserInfoResponse {
    String sub;
    String name;
    String given_name;
    String family_name;
    String picture;
    String email;
    String email_verified;
    String hd;
}
