package org.example.projecttestpvcb.service.impl;

import org.example.projecttestpvcb.model.SpinResult;
import org.example.projecttestpvcb.service.GameService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class GameServiceImpl implements GameService {
    private final SecureRandom random = new SecureRandom();

    @Override
    public SpinResult spin() {
        boolean win = random.nextBoolean();
        return new SpinResult(win);
    }
}
