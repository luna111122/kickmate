package com.kickmate.kickmate.domain.vocabulary.controller;

import com.kickmate.kickmate.domain.match.dto.MatchFilterReq;
import com.kickmate.kickmate.domain.match.dto.MatchRes;
import com.kickmate.kickmate.domain.match.exceptions.code.MatchSuccessCode;
import com.kickmate.kickmate.domain.vocabulary.dto.VocabularyReq;
import com.kickmate.kickmate.domain.vocabulary.dto.VocabularyRes;
import com.kickmate.kickmate.domain.vocabulary.exceptions.code.VocabularySuccessCode;
import com.kickmate.kickmate.domain.vocabulary.service.VocabularyService;
import com.kickmate.kickmate.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vocabulary")
@RequiredArgsConstructor
public class VocabularyController {

    private final VocabularyService vocabularyService;

    @PostMapping
    public ApiResponse<?> getVocab(
            @RequestBody @Valid VocabularyReq dto
            ){


        VocabularyRes vocabularyRes = vocabularyService.getVocab(dto);
        return ApiResponse.onSuccess(VocabularySuccessCode.VOCABULARY_SUCCESS_CODE,vocabularyRes);


    }


}
