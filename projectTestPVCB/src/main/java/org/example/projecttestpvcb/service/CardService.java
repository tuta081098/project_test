package org.example.projecttestpvcb.service;

import org.example.projecttestpvcb.model.Card;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CardService {
    List<Card> importFromCsv(MultipartFile file) throws IOException;
    int remaining();
    Optional<Card> redeemCard();
}