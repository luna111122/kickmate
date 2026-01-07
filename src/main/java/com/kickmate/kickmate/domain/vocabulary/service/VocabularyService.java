package com.kickmate.kickmate.domain.vocabulary.service;


import com.kickmate.kickmate.domain.vocabulary.dto.VocabularyReq;
import com.kickmate.kickmate.domain.vocabulary.dto.VocabularyRes;
import com.kickmate.kickmate.domain.vocabulary.entity.RawVocabulary;
import com.kickmate.kickmate.domain.vocabulary.exceptions.VocabularyException;
import com.kickmate.kickmate.domain.vocabulary.exceptions.code.VocabularyErrorCode;
import com.kickmate.kickmate.domain.vocabulary.repository.VocabularyRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VocabularyService {


    private final VocabularyRepository vocabularyRepository;


    public VocabularyRes getVocab(@Valid VocabularyReq dto) {

        RawVocabulary vocab = vocabularyRepository
                .findByTypeName(dto.getVocab())
                .orElseThrow(() -> new VocabularyException(VocabularyErrorCode.VOCABULARY_ERROR_CODE));


        return VocabularyRes.builder()
                .id(vocab.getId())
                .typeName(vocab.getTypeName())
                .typeNameKo(vocab.getTypeNameKo())
                .description(vocab.getDescription())
                .build();




    }
}
