package com.example.ztp_lab1.controller;

import com.example.ztp_lab1.dto.BannedPhraseDto;
import com.example.ztp_lab1.service.BannedPhraseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/banned-phrases")
@RequiredArgsConstructor
public class BannedPhraseController {

    private final BannedPhraseService bannedPhraseService;

    @GetMapping
    public ResponseEntity<List<BannedPhraseDto>> getAllBannedPhrases() {
        List<BannedPhraseDto> phrases = bannedPhraseService.getAllBannedPhrases();
        return ResponseEntity.ok(phrases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BannedPhraseDto> getBannedPhraseById(@PathVariable Long id) {
        BannedPhraseDto phrase = bannedPhraseService.getBannedPhraseById(id);
        return ResponseEntity.ok(phrase);
    }

    @PostMapping
    public ResponseEntity<BannedPhraseDto> addBannedPhrase(@Valid @RequestBody BannedPhraseDto dto) {
        BannedPhraseDto createdPhrase = bannedPhraseService.addBannedPhrase(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPhrase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BannedPhraseDto> updateBannedPhrase(@PathVariable Long id, @Valid @RequestBody BannedPhraseDto dto) {
        BannedPhraseDto updatedPhrase = bannedPhraseService.updateBannedPhrase(id, dto);
        return ResponseEntity.ok(updatedPhrase);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBannedPhrase(@PathVariable Long id) {
        bannedPhraseService.deleteBannedPhrase(id);
        return ResponseEntity.noContent().build();
    }
}