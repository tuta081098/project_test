package org.example.projecttestpvcb.service.impl;

import org.example.projecttestpvcb.model.Card;
import org.example.projecttestpvcb.service.CardService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private final List<Card> available = new LinkedList<>();

    @Override
    public synchronized List<Card> importFromCsv(MultipartFile file) throws IOException {
        List<Card> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; } // bỏ header
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    Card c = new Card(parts[0].trim(), parts[1].trim());
                    available.add(c);
                    list.add(c);
                }
            }
        }
        return list;
    }

    @Override
    public synchronized int remaining() {
        return available.size();
    }

    @Override
    public synchronized Optional<Card> redeemCard() {
        if (available.isEmpty()) return Optional.empty();
        return Optional.of(available.remove(0));
    }
}
