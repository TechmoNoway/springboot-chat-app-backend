package com.trickynguci.civicmessagerbackend.service;

import com.trickynguci.civicmessagerbackend.dto.UpdateUserDTO;
import com.trickynguci.civicmessagerbackend.dto.request.ChangePasswordRequest;
import com.trickynguci.civicmessagerbackend.dto.response.UserFriendsResponse;
import com.trickynguci.civicmessagerbackend.dto.response.UserResponse;
import com.trickynguci.civicmessagerbackend.model.User;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    User getUserById(int id);

    List<UserResponse> getUsersByUsername(String username);

    boolean isUsernameExist(String username);

    int updateUser(UpdateUserDTO updateUserDTO);

    List<UserFriendsResponse> getAllUserFriendsAndLatestMessage(int userId);

    List<UserResponse> searchUserByString(String str);

    void changePassword(ChangePasswordRequest changePasswordRequest);
}
