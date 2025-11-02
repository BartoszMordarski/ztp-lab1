package com.example.ztp_lab1.service;

import com.example.ztp_lab1.exception.BannedPhraseException;
import com.example.ztp_lab1.repository.BannedPhraseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannedPhraseValidator implements ProductNameValidator<String> {

    private final BannedPhraseRepository bannedPhraseRepository;

    @Override
    public void validate(String productName) {
        if (productName == null || productName.isEmpty()) {
            return;
        }

        List<String> bannedPhrases = bannedPhraseRepository.findAllPhrases();
        String lowerCaseName = productName.toLowerCase();

        for (String bannedPhrase : bannedPhrases) {
            if (lowerCaseName.contains(bannedPhrase.toLowerCase())) {
                throw new BannedPhraseException(
                        String.format("Product name contains banned phrase: '%s'", bannedPhrase)
                );
            }
        }
    }
}
