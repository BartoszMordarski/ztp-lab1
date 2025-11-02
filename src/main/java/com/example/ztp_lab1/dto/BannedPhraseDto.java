package com.example.ztp_lab1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannedPhraseDto {
    private Long id;

    @NotBlank(message = "Phrase cannot be empty")
    @Size(min = 2, max = 50, message = "Phrase must be between 2 and 20 characters")
    private String phrase;
}