package com.trickynguci.civicmessagerbackend.controller;

import com.trickynguci.civicmessagerbackend.dto.request.MessageRequest;
import com.trickynguci.civicmessagerbackend.model.Message;
import com.trickynguci.civicmessagerbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final UserService userService;


//    @MessageMapping("/private-message")
//    public MessageRequest receivePrivateMessage(@Payload MessageRequest message) {
//        simpMessagingTemplate.convertAndSendToUser(
//                userService.getUserById(message.getReceiverId()).getUsername(),
//                "/private",
//                message);
//        System.out.println(userService.getUserById(message.getReceiverId()).getUsername());
//        System.out.println(message.toString());
//
//        return message;
//    }

    @MessageMapping("/topic")
    public void receivePrivateMessage(@Payload MessageRequest message) {
        // Assuming userService.getUserById returns a user object with a username
        String username = userService.getUserById(message.getReceiverId()).getUsername();

        // Construct the destination topic for the user
        String userDestination = "/topic/" + username;

        // Send the message to the user-specific topic
        simpMessagingTemplate.convertAndSend(userDestination, message);

        // Optional: Logging for debugging purposes
        System.out.println("Sending to " + userDestination);
        System.out.println(message.toString());
    }

}
