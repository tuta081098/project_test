package org.example.projecttestpvcb.controller;

import org.example.projecttestpvcb.model.Role;
import org.example.projecttestpvcb.model.UserSession;
import org.example.projecttestpvcb.util.CardStore;
import org.example.projecttestpvcb.util.TokenStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private UserSession requireAdmin(String token) {
        UserSession session = TokenStore.get(token);
        if (session == null || session.isExpired()) {
            throw new RuntimeException("Invalid token");
        }
        if (session.getRole() != Role.ADMIN) {
            throw new RuntimeException("Permission denied");
        }
        return session;
    }

    @PostMapping("/cards/upload")
    public ResponseEntity<?> uploadCards(@RequestHeader("X-Auth-Token") String token,
                                         @RequestParam("file") MultipartFile file) {
        requireAdmin(token);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    CardStore.addCard(line.trim());
                    count++;
                }
            }
            return ResponseEntity.ok("Uploaded " + count + " cards");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }
}