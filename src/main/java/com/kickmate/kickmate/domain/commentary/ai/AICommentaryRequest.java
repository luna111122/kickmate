package com.kickmate.kickmate.domain.commentary.ai;


import com.kickmate.kickmate.domain.commentary.ai.dto.ActionEventDto;
import com.kickmate.kickmate.domain.commentary.ai.dto.MatchInfoDto;
import com.kickmate.kickmate.domain.commentary.entity.RawMatchInfo;
import com.kickmate.kickmate.domain.commentary.enums.Style;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AICommentaryRequest {
    private String gameId;
    private Style style;
    private MatchInfoDto matchInfo;
    private List<ActionEventDto> rawData;

    public static AICommentaryRequest of(
            Long gameId,
            Style style,
            RawMatchInfo matchInfo,
            List<com.kickmate.kickmate.domain.commentary.entity.RawActionEvent> actionEvents
    ) {
        return AICommentaryRequest.builder()
                .gameId(String.valueOf(gameId))
                .style(style)
                .matchInfo(MatchInfoDto.from(matchInfo))
                .rawData(actionEvents.stream().map(ActionEventDto::from).toList())
                .build();
    }
}