package org.example.projecttestpvcb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSession {
    private String username;
    private Role role;
    private Instant expiry;
    private int winCount = 0;

    public UserSession(String username, Role role, boolean dummy) {
        this.username = username;
        this.role = role;
        this.expiry = Instant.now().plusSeconds(3600); // mặc định hết hạn sau 1h
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiry);
    }

    public void increaseWinCount() {
        this.winCount++;
    }

    public void decreaseWinCount() {
        if (this.winCount > 0) {
            this.winCount--;
        }
    }
}
