package com.kickmate.kickmate.domain.commentary.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionCoordDto {

    private Long gameId;
    private Integer actionId;

    private Double startX;
    private Double startY;
    private Double endX;
    private Double endY;

    private Double dx;
    private Double dy;

    private String teamNameKoShort;
}
