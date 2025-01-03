package com.trickynguci.civicmessagerbackend.dto.request;

import lombok.Builder;

@Builder
public class ChangePasswordRequest {
    int userId;
    String newPassword;
    String confirmPassword;
}
