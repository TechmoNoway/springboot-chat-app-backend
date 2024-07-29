package com.trickynguci.civicmessagerbackend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class UserFriendsResponse {
    private int id;
    private String username;
    private String email;
    private String avatarUrl;
    private String phoneNumber;
    private Date birthdate;
    private boolean isActive;
    private boolean isBlocked;
    private MessageResponse lastMessage;
}
