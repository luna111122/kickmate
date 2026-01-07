package com.kickmate.kickmate.domain.vocabulary.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VocabularyReq {

    @NotBlank
    String vocab;

}
