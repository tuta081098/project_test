package org.example.projecttestpvcb.controller;

import org.example.projecttestpvcb.model.Role;
import org.example.projecttestpvcb.model.UserSession;
import org.example.projecttestpvcb.util.TokenStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SecureRandom random = new SecureRandom();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username) {
        Role role = "admin".equalsIgnoreCase(username) ? Role.ADMIN : Role.USER;

        String token = generateToken();
        UserSession session = new UserSession(username, role, false);
        TokenStore.put(token, session);

        Map<String, Object> res = new HashMap<>();
        res.put("username", username);
        res.put("role", role);
        res.put("token", token);
        return ResponseEntity.ok(res);
    }

    private String generateToken() {
        byte[] buf = new byte[24];
        random.nextBytes(buf);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
    }
}