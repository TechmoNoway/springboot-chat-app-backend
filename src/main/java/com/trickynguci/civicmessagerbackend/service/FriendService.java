package com.trickynguci.civicmessagerbackend.service;

public interface FriendService {

    boolean checkIsFriend(int userId, int friendId);

    void addFriend(int userId, int friendId, String status);

    void removeFriend(int userId, int friendId);

}
