package org.example.projecttestpvcb.util;

import org.example.projecttestpvcb.model.UserSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenStore {
    private static final Map<String, UserSession> STORE = new ConcurrentHashMap<>();

    public static void put(String token, UserSession session) {
        STORE.put(token, session);
    }

    public static UserSession get(String token) {
        return STORE.get(token);
    }

    public static void remove(String token) {
        STORE.remove(token);
    }
}