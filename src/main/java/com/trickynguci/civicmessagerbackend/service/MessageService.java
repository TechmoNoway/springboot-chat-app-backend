package com.trickynguci.civicmessagerbackend.service;

import com.trickynguci.civicmessagerbackend.dto.request.MessageRequest;
import com.trickynguci.civicmessagerbackend.dto.response.MessageResponse;
import com.trickynguci.civicmessagerbackend.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> getMessagesByUserId(int userId);

    List<Message> getMessagesByFriendId(int friendId);

    List<MessageResponse> getMessagesByFriendBoxChat(int user1Id, int user2Id);

    MessageResponse getTheLatestMessage(int user1Id, int user2Id);

    void saveMessage(MessageRequest messageRequest);




}
