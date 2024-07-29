package com.trickynguci.civicmessagerbackend.service.Impl;

import com.trickynguci.civicmessagerbackend.dto.request.MessageRequest;
import com.trickynguci.civicmessagerbackend.dto.response.MessageResponse;
import com.trickynguci.civicmessagerbackend.model.Message;
import com.trickynguci.civicmessagerbackend.repository.MessageRepository;
import com.trickynguci.civicmessagerbackend.repository.UserRepository;
import com.trickynguci.civicmessagerbackend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    @Override
    public List<Message> getMessagesByUserId(int userId) {
        return List.of();
    }

    @Override
    public List<Message> getMessagesByFriendId(int friendId) {
        return List.of();
    }

    @Override
    public List<MessageResponse> getMessagesByFriendBoxChat(int user1Id, int user2Id) {

        List<MessageResponse> result = messageRepository.findMessagesBetweenTwoUsers(user1Id, user2Id).stream()
                .map(message -> MessageResponse.builder()
                        .id(message.getId())
                        .senderId(message.getSender().getId())
                        .receiverId(message.getReceiver().getId())
                        .content(message.getContent())
                        .status(message.getStatus())
                        .mediaType(message.getMediaType())
                        .mediaUrl(message.getMediaUrl())
                        .timestamp(message.getTimestamp())
                        .build())
                .collect(Collectors.toList());


        result.sort(Comparator.comparing(MessageResponse::getTimestamp));
        return result;
    }

    @Override
    public MessageResponse getTheLatestMessage(int user1Id, int user2Id) {

        List<MessageResponse> result = getMessagesByFriendBoxChat(user1Id, user2Id);

        if (result.isEmpty()) {
            return null;
        }

        return result.getLast();
    }

    @Override
    public void saveMessage(MessageRequest messageRequest) {
        Message message = Message.builder()
                .sender(userRepository.findById(messageRequest.getSenderId()))
                .receiver(userRepository.findById(messageRequest.getReceiverId()))
                .content(messageRequest.getContent())
                .status(messageRequest.getStatus())
                .mediaType(messageRequest.getMediaType())
                .mediaUrl(messageRequest.getMediaUrl())
                .timestamp(messageRequest.getTimestamp())
                .build();
        messageRepository.save(message);
    }



}
