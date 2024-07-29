package com.trickynguci.civicmessagerbackend.restcontroller;

import com.trickynguci.civicmessagerbackend.dto.UpdateUserDTO;
import com.trickynguci.civicmessagerbackend.dto.request.UserActivityRequest;
import com.trickynguci.civicmessagerbackend.service.RedisService;
import com.trickynguci.civicmessagerbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserAPI {

    private final UserService userService;

    private final RedisService redisService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> doGetAllUsers() {
        HashMap<String, Object> result = new HashMap<>();
        try {
            result.put("success", true);
            result.put("message", "Call api getAllUsers successfully");
            result.put("data", userService.getAllUsers());
            return ResponseEntity.status(HttpStatus.OK).body(result);

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Call api getAllUsers failed");
            result.put("data", null);
            log.error("error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/getUserById")
    public ResponseEntity<?> doGetUserById(@RequestParam("id") int id) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            result.put("success", true);
            result.put("message", "Call api getUserById successfully");
            result.put("status", HttpStatus.OK.value());
            result.put("data", userService.getUserById(id));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Call api getUserById failed");
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.put("data", null);
            log.error("error: ", e);
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/searchUserByString")
    public ResponseEntity<?> doSearchUserByString(@RequestParam("str") String str) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            result.put("success", true);
            result.put("message", "Call api searchUserByString successfully");
            result.put("status", HttpStatus.OK.value());
            result.put("data", userService.searchUserByString(str));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Call api searchUserByString failed");
            result.put("status", HttpStatus.NO_CONTENT.value());
            result.put("data", null);
            log.error("error: ", e);
            return ResponseEntity.ok(result);
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> doUpdateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            result.put("success", true);
            result.put("message", "Call api doUpdateUser successfully");
            result.put("status", HttpStatus.OK.value());
            result.put("data", userService.updateUser(updateUserDTO));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Call api doUpdateUser failed");
            result.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            result.put("data", null);
            log.error("error: ", e);
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/getFriendsAndLatestMessage")
    public ResponseEntity<?> doGetFriendsAndLatestMessage(@RequestParam("userId") int userId) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            result.put("success", true);
            result.put("message", "Call api doGetFriendsAndLatestMessage successfully");
            result.put("data", userService.getAllUserFriendsAndLatestMessage(userId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Call api doGetFriendsAndLatestMessage failed");
            result.put("data", null);
            log.error("error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/getUserStatus")
    public ResponseEntity<?> doGetUserStatus(@RequestParam("userId") int userId) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            result.put("success", true);
            result.put("message", "Call api doGetUserStatus successfully");
            result.put("data", redisService.getUserStatus(userId));
            return ResponseEntity.status(HttpStatus.OK).body(result);

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Call api doGetUserStatus failed");
            result.put("data", null);
            log.error("error: ", e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }
    }

    @PostMapping("/changeUserStatus")
    public ResponseEntity<?> doChangeUserStatus(@RequestParam("userId") int userId,
            @RequestParam("status") String status) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            redisService.setUserStatus(userId, status);
            result.put("success", true);
            result.put("message", "Call api doChangeUserStatus successfully");
            result.put("data", userId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Call api doChangeUserStatus failed");
            result.put("data", null);
            log.error("error: ", e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }
    }

    @PostMapping(value = "/userActivity", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> doUserActivity(@RequestBody UserActivityRequest userActivityRequest) {
        try {
            redisService.setUserStatus(userActivityRequest.getUserId(), userActivityRequest.getStatus());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error", e);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
