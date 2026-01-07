package com.kickmate.kickmate.domain.vocabulary.repository;

import com.kickmate.kickmate.domain.vocabulary.entity.RawVocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VocabularyRepository extends JpaRepository<RawVocabulary,Long> {

    Optional<RawVocabulary> findByTypeNameKo(String typeNameKo);
}
