package com.kickmate.kickmate.domain.commentary.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "raw_action_event")
public class RawActionEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "game_id", nullable = false)
    private Integer gameId;

    @NotNull
    @Column(name = "action_id", nullable = false)
    private Integer actionId;

    @NotNull
    @Column(name = "period_id", nullable = false)
    private Integer periodId;

    @Column(name = "time_seconds")
    private Double timeSeconds;

    @NotNull
    @Column(name = "team_id", nullable = false)
    private Integer teamId;

    @NotNull
    @Column(name = "player_id", nullable = false)
    private Integer playerId;

    @Size(max = 50)
    @Column(name = "result_name", length = 50)
    private String resultName;

    @Column(name = "start_x")
    private Double startX;

    @Column(name = "start_y")
    private Double startY;

    @Column(name = "end_x")
    private Double endX;

    @Column(name = "end_y")
    private Double endY;

    @Column(name = "dx")
    private Double dx;

    @Column(name = "dy")
    private Double dy;

    @Size(max = 50)
    @NotNull
    @Column(name = "type_name", nullable = false, length = 50)
    private String typeName;

    @Size(max = 50)
    @NotNull
    @Column(name = "type_name_ko", nullable = false, length = 50)
    private String typeNameKo;

    @Size(max = 50)
    @Column(name = "player_name_ko", length = 50)
    private String playerNameKo;

    @Size(max = 100)
    @Column(name = "team_name_ko", length = 100)
    private String teamNameKo;

    @Size(max = 50)
    @Column(name = "team_name_ko_short", length = 50)
    private String teamNameKoShort;

    @Size(max = 20)
    @Column(name = "position_name", length = 20)
    private String positionName;

    @Size(max = 20)
    @Column(name = "main_position", length = 20)
    private String mainPosition;

}