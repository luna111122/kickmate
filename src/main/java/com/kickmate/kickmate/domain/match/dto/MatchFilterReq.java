package com.kickmate.kickmate.domain.match.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MatchFilterReq {

    @NotNull
    @Positive
    Integer month;


    @NotNull
    @Positive
    int round;


}
