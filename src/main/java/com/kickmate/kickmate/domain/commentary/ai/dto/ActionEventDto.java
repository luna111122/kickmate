package com.kickmate.kickmate.domain.commentary.ai.dto;


import com.kickmate.kickmate.domain.commentary.entity.RawActionEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ActionEventDto {
    private Long gameId;
    private Integer actionId;
    private Integer periodId;
    private Double timeSeconds;
    private Integer teamId;
    private Integer playerId;

    private String resultName;

    private Double startX;
    private Double startY;
    private Double endX;
    private Double endY;

    private Double dx;
    private Double dy;

    private String typeName;
    private String typeNameKo;

    private String playerNameKo;
    private String teamNameKo;
    private String teamNameKoShort;

    private String positionName;
    private String mainPosition;

    public static ActionEventDto from(RawActionEvent e) {
        return ActionEventDto.builder()
                .gameId(e.getGameId())
                .actionId(e.getActionId())
                .periodId(e.getPeriodId())
                .timeSeconds(e.getTimeSeconds())
                .teamId(e.getTeamId())
                .playerId(e.getPlayerId())
                .resultName(e.getResultName())
                .startX(e.getStartX())
                .startY(e.getStartY())
                .endX(e.getEndX())
                .endY(e.getEndY())
                .dx(e.getDx())
                .dy(e.getDy())
                .typeName(e.getTypeName())
                .typeNameKo(e.getTypeNameKo())
                .playerNameKo(e.getPlayerNameKo())
                .teamNameKo(e.getTeamNameKo())
                .teamNameKoShort(e.getTeamNameKoShort())
                .positionName(e.getPositionName())
                .mainPosition(e.getMainPosition())
                .build();
    }
}