package com.kickmate.kickmate.domain.commentary.dto;

import com.kickmate.kickmate.domain.commentary.enums.Style;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
public class StartCommentaryReq {

    @NotNull
    @Positive
    Long gameId;

    @NotNull
    @PositiveOrZero
    int actionId;

    @NotNull
    Style style;
}
