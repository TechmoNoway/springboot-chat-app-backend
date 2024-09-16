package com.trickynguci.civicmessagerbackend.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GoogleLoginResponse {
    private int userId;
}
