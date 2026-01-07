package com.kickmate.kickmate.domain.vocabulary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class VocabularyRes {



    private Long id;
    private String typeName;
    private String typeNameKo;
    private String description;

}
