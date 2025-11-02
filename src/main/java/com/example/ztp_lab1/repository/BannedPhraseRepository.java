package com.example.ztp_lab1.repository;

import com.example.ztp_lab1.model.BannedPhrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannedPhraseRepository extends JpaRepository<BannedPhrase, Long> {

    boolean existsByPhrase(String phrase);

    @Query("SELECT bp.phrase FROM BannedPhrase bp")
    List<String> findAllPhrases();
}