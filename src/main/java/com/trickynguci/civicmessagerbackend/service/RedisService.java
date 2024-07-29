package com.trickynguci.civicmessagerbackend.service;

public interface RedisService {

    void setUserStatus(int userId, String status);

    String getUserStatus(int userId);

}
