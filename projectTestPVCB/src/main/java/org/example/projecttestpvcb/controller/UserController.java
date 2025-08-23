package org.example.projecttestpvcb.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.projecttestpvcb.model.Role;
import org.example.projecttestpvcb.model.UserSession;
import org.example.projecttestpvcb.util.CardStore;
import org.example.projecttestpvcb.util.TokenStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final SecureRandom random = new SecureRandom();

    private UserSession requireUser(String token) {
        UserSession session = TokenStore.get(token);
        if (session == null || session.isExpired()) {
            throw new RuntimeException("Lỗi token");
        }
        if (session.getRole() != Role.USER) {
            throw new RuntimeException("Lỗi quyền");
        }
        return session;
    }

    @PostMapping("/spin")
    public ResponseEntity<?> spin(@RequestHeader("X-Auth-Token") String token) {
        UserSession session = requireUser(token);
        boolean win = random.nextBoolean();

        if (win) {
            session.increaseWinCount();
        } else {
        }

        Map<String, Object> res = new HashMap<>();
        res.put("username", session.getUsername());
        res.put("win", win);
        res.put("availableClaims", session.getWinCount());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/claim")
    public ResponseEntity<?> claim(@RequestHeader("X-Auth-Token") String token) {
        UserSession session = requireUser(token);

        Map<String, Object> res = new HashMap<>();
        res.put("username", session.getUsername());

        if (session.getWinCount() <= 0) {
            res.put("message", "Bạn chưa có lượt thắng để nhận quà");
            return ResponseEntity.badRequest().body(res);
        }

        String card = CardStore.getCard();
        if (card != null) {
            session.decreaseWinCount();
            res.put("card", card);
            res.put("remainingClaims", session.getWinCount());
        } else {
            res.put("message", "Hết quà");
        }
        return ResponseEntity.ok(res);
    }
}