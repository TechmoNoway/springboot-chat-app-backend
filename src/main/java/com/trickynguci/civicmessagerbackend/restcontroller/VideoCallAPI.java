package com.trickynguci.civicmessagerbackend.restcontroller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/video-call")
public class VideoCallAPI {

    @Value("${videosdk.api.key}")
    private String VIDEOSDK_API_KEY;

    @Value("${videosdk.api.secret}")
    private String VIDEOSDK_SECRET_KEY;

    @GetMapping("/generateToken")
    public ResponseEntity<?> doGenerateToken(@RequestParam int participantId
    ) {
        Map<String, Object> payload = new HashMap<>();

        payload.put("apikey", VIDEOSDK_API_KEY);
        payload.put("permissions", new String[]{"allow_join", "allow_mod"});
        payload.put("version", 2);
//        payload.put("participantId", participantId);
//        payload.put("roles", new String[]{"crawler", "rtc"});

        Map<String, Object> result = new HashMap<>();

        try {
            String token = Jwts
                    .builder()
                    .setClaims(payload)
                    .setExpiration(new Date(System.currentTimeMillis() + 86400 * 1000))
                    .signWith(SignatureAlgorithm.HS256, VIDEOSDK_SECRET_KEY.getBytes())
                    .compact();
            result.put("success", true);
            result.put("message", "Call api doGenerateToken successfully");
            result.put("data", token);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Call api doGenerateToken failed");
            result.put("data", null);
            log.error("Error: ", e);
            return ResponseEntity.ok(result);
        }

    }
}
