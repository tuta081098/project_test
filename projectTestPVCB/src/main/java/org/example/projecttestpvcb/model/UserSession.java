package org.example.projecttestpvcb.model;

public class UserSession {
    private String username;
    private Role role;
    private boolean expired;

    public UserSession(String username, Role role, boolean expired) {
        this.username = username;
        this.role = role;
        this.expired = expired;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
