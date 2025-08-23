package org.example.projecttestpvcb.controller;

import org.example.projecttestpvcb.model.Role;
import org.example.projecttestpvcb.model.UserSession;
import org.example.projecttestpvcb.util.CardStore;
import org.example.projecttestpvcb.util.TokenStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final SecureRandom random = new SecureRandom();

    private UserSession requireUser(String token) {
        UserSession session = TokenStore.get(token);
        if (session == null || session.isExpired()) {
            throw new RuntimeException("Invalid token");
        }
        if (session.getRole() != Role.USER) {
            throw new RuntimeException("Permission denied");
        }
        return session;
    }

    @PostMapping("/spin")
    public ResponseEntity<?> spin(@RequestHeader("X-Auth-Token") String token) {
        UserSession session = requireUser(token);
        boolean win = random.nextBoolean();

        Map<String, Object> res = new HashMap<>();
        res.put("username", session.getUsername());
        res.put("win", win);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/claim")
    public ResponseEntity<?> claim(@RequestHeader("X-Auth-Token") String token) {
        UserSession session = requireUser(token);
        String card = CardStore.getCard();

        Map<String, Object> res = new HashMap<>();
        res.put("username", session.getUsername());
        if (card != null) {
            res.put("card", card);
        } else {
            res.put("message", "Hết quà");
        }
        return ResponseEntity.ok(res);
    }
}
