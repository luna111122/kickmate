package com.kickmate.kickmate.domain.commentary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreRes {

    private String typeName;          // Goal / Own Goal
    private String teamName;          // 한국어 짧은 팀명
    private Integer actionId;         // 몇 번째 액션인지
}