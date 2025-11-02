package com.example.ztp_lab1.service;

import com.example.ztp_lab1.dto.BannedPhraseDto;
import com.example.ztp_lab1.model.BannedPhrase;
import com.example.ztp_lab1.repository.BannedPhraseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannedPhraseService {

    private final BannedPhraseRepository bannedPhraseRepository;

    public List<BannedPhraseDto> getAllBannedPhrases() {
        return bannedPhraseRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public BannedPhraseDto getBannedPhraseById(Long id) {
        BannedPhrase bannedPhrase = bannedPhraseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Banned phrase not found with id=%d", id)
                ));
        return mapToDto(bannedPhrase);
    }

    public BannedPhraseDto addBannedPhrase(BannedPhraseDto dto) {
        if (bannedPhraseRepository.existsByPhrase(dto.getPhrase())) {
            throw new IllegalArgumentException(
                    String.format("Phrase '%s' is already banned", dto.getPhrase())
            );
        }

        BannedPhrase bannedPhrase = BannedPhrase.builder()
                .phrase(dto.getPhrase())
                .build();

        bannedPhrase = bannedPhraseRepository.save(bannedPhrase);
        return mapToDto(bannedPhrase);
    }

    public void deleteBannedPhrase(Long id) {
        if (!bannedPhraseRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    String.format("Banned phrase not found with id=%d", id)
            );
        }
        bannedPhraseRepository.deleteById(id);
    }

    public BannedPhraseDto updateBannedPhrase(Long id, BannedPhraseDto dto) {
        BannedPhrase existingPhrase = bannedPhraseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Banned phrase not found with id=%d", id)
                ));

        if (!existingPhrase.getPhrase().equals(dto.getPhrase()) &&
                bannedPhraseRepository.existsByPhrase(dto.getPhrase())) {
            throw new IllegalArgumentException(
                    String.format("Phrase '%s' is already banned", dto.getPhrase())
            );
        }

        existingPhrase.setPhrase(dto.getPhrase());
        existingPhrase = bannedPhraseRepository.save(existingPhrase);

        return mapToDto(existingPhrase);
    }

    private BannedPhraseDto mapToDto(BannedPhrase bannedPhrase) {
        return BannedPhraseDto.builder()
                .id(bannedPhrase.getId())
                .phrase(bannedPhrase.getPhrase())
                .build();
    }
}