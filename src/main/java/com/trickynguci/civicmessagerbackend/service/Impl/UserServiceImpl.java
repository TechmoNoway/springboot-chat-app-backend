package com.trickynguci.civicmessagerbackend.service.Impl;

import com.trickynguci.civicmessagerbackend.dto.UpdateUserDTO;
import com.trickynguci.civicmessagerbackend.dto.response.UserFriendsResponse;
import com.trickynguci.civicmessagerbackend.dto.response.UserResponse;
import com.trickynguci.civicmessagerbackend.model.User;
import com.trickynguci.civicmessagerbackend.repository.MessageRepository;
import com.trickynguci.civicmessagerbackend.repository.UserRepository;
import com.trickynguci.civicmessagerbackend.service.MessageService;
import com.trickynguci.civicmessagerbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final MessageService messageService;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .avatarUrl(user.getAvatarUrl())
                        .phoneNumber(user.getPhoneNumber())
                        .birthdate(user.getBirthdate())
                        .isActive(user.isActive())
                        .isBlocked(user.isBlocked())
                .build())
                .toList();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserResponse> getUsersByUsername(String username) {
        return userRepository.findByUsername(username).stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .avatarUrl(user.getAvatarUrl())
                        .phoneNumber(user.getPhoneNumber())
                        .birthdate(user.getBirthdate())
                        .isActive(user.isActive())
                        .isBlocked(user.isBlocked())
                        .build())
                .toList();
    }

    @Override
    public boolean isUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public int updateUser(UpdateUserDTO updateUserDTO) {

        User user = userRepository.findById(updateUserDTO.getUserId());
        user.setUsername(updateUserDTO.getUsername());
        user.setEmail(updateUserDTO.getEmail());
        user.setAvatarUrl(updateUserDTO.getAvatarUrl());
        user.setBirthdate(updateUserDTO.getBirthdate());
        user.setPhoneNumber(updateUserDTO.getPhoneNumber());

        userRepository.save(user);


        return user.getId();
    }

    @Override
    public List<UserFriendsResponse> getAllUserFriendsAndLatestMessage(int userId) {

        List<User> users = userRepository.findAllFriends(userId);

        List<UserFriendsResponse> result = new ArrayList<>();

        for (User user : users) {

            UserFriendsResponse response =  UserFriendsResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .avatarUrl(user.getAvatarUrl())
                    .phoneNumber(user.getPhoneNumber())
                    .birthdate(user.getBirthdate())
                    .isActive(user.isActive())
                    .isBlocked(user.isBlocked())
                    .lastMessage(messageService.getTheLatestMessage(userId, user.getId()))
                    .build();
            result.add(response);
        }


        return result;
    }

    @Override
    public List<UserResponse> searchUserByString(String str) {
        return userRepository.findByUsername(str).stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .avatarUrl(user.getAvatarUrl())
                        .phoneNumber(user.getPhoneNumber())
                        .birthdate(user.getBirthdate())
                        .isActive(user.isActive())
                        .isBlocked(user.isBlocked())
                        .build())
                .toList();
    }
}
