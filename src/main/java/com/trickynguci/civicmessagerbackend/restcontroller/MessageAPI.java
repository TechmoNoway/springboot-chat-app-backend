package com.trickynguci.civicmessagerbackend.restcontroller;

import com.trickynguci.civicmessagerbackend.dto.request.MessageRequest;
import com.trickynguci.civicmessagerbackend.model.Message;
import com.trickynguci.civicmessagerbackend.repository.MessageRepository;
import com.trickynguci.civicmessagerbackend.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/messages")
public class MessageAPI {


    private final MessageService messageService;
    private final MessageRepository messageRepository;

    @GetMapping("/getMessageBetweenTwoUsers")
    public ResponseEntity<?> doGetMessageBetweenTwoUsers(@RequestParam("user1Id") int user1Id, @RequestParam("user2Id") int user2Id) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            result.put("success", true);
            result.put("message", "Call api doGetMessagesByFriendBoxChat successfully");
            result.put("status", HttpStatus.OK.value());
            result.put("data", messageService.getMessagesByFriendBoxChat(user1Id, user2Id));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Call api doGetMessagesByFriendBoxChat failed");
            result.put("status", HttpStatus.NO_CONTENT.value());
            result.put("data", null);
            log.error("error: ", e);
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/getTheLatestMessage")
    public ResponseEntity<?> doGetTheLatestMessage(@RequestParam("user1Id") int user1Id, @RequestParam("user2Id") int user2Id) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            result.put("success", true);
            result.put("message", "Call api doGetTheLatestMessage successfully");
            result.put("status", HttpStatus.OK.value());
            result.put("data", messageService.getTheLatestMessage(user1Id, user2Id));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Call api doGetTheLatestMessage failed");
            result.put("status", HttpStatus.NO_CONTENT.value());
            result.put("data", null);
            log.error("error: ", e);
            return ResponseEntity.ok(result);
        }
    }


    @PostMapping("/saveMessage")
    public ResponseEntity<?> doSaveMessage(@RequestBody MessageRequest messageRequest) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            messageService.saveMessage(messageRequest);
            result.put("success", true);
            result.put("message", "Call api doSaveMessage successfully");
            result.put("status", HttpStatus.OK.value());
            result.put("data", messageRequest);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Call api doSaveMessage failed");
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.put("data", null);
            log.error("error: ", e);
            return ResponseEntity.ok(result);
        }
    }


}
