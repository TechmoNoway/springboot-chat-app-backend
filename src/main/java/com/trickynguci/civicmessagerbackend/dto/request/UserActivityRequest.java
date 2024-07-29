package com.trickynguci.civicmessagerbackend.dto.request;

import lombok.Data;

@Data
public class UserActivityRequest {
    private int userId;
    private String status;
}
