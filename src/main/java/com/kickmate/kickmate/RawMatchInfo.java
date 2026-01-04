package com.kickmate.kickmate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Entity
@Table(name = "raw_match_info")
public class RawMatchInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Column(name = "season_id")
    private Long seasonId;

    @Column(name = "competition_id")
    private Long competitionId;

    @Column(name = "game_day")
    private Integer gameDay;

    @Column(name = "game_date")
    private Instant gameDate;

    @Column(name = "home_team_id")
    private Long homeTeamId;

    @Column(name = "away_team_id")
    private Long awayTeamId;

    @Column(name = "home_score")
    private Integer homeScore;

    @Column(name = "away_score")
    private Integer awayScore;

    @Size(max = 255)
    @Column(name = "venue")
    private String venue;

    @Size(max = 100)
    @Column(name = "competition_name", length = 100)
    private String competitionName;

    @Size(max = 10)
    @Column(name = "country_name", length = 10)
    private String countryName;

    @Size(max = 50)
    @Column(name = "season_name", length = 50)
    private String seasonName;

    @Size(max = 150)
    @Column(name = "home_team_name", length = 150)
    private String homeTeamName;

    @Size(max = 150)
    @Column(name = "home_team_name_ko", length = 150)
    private String homeTeamNameKo;

    @Size(max = 50)
    @Column(name = "home_team_name_ko_short", length = 50)
    private String homeTeamNameKoShort;

    @Size(max = 150)
    @Column(name = "away_team_name", length = 150)
    private String awayTeamName;

    @Size(max = 150)
    @Column(name = "away_team_name_ko", length = 150)
    private String awayTeamNameKo;

    @Size(max = 50)
    @Column(name = "away_team_name_ko_short", length = 50)
    private String awayTeamNameKoShort;

    @Column(name = "audience_num")
    private Integer audienceNum;

    @Size(max = 20)
    @Column(name = "temperature", length = 20)
    private String temperature;

    @Size(max = 50)
    @Column(name = "weather", length = 50)
    private String weather;

    @Size(max = 100)
    @Column(name = "referee", length = 100)
    private String referee;

    @Size(max = 255)
    @Column(name = "assistant_referees")
    private String assistantReferees;

    @Size(max = 100)
    @Column(name = "fourth_official", length = 100)
    private String fourthOfficial;

    @Size(max = 255)
    @Column(name = "var_referees")
    private String varReferees;

    @Size(max = 100)
    @Column(name = "tsg", length = 100)
    private String tsg;

    @Column(name = "home_rank")
    private Integer homeRank;

    @Column(name = "away_rank")
    private Integer awayRank;

    @Size(max = 100)
    @Column(name = "home_team_uniform", length = 100)
    private String homeTeamUniform;

    @Size(max = 100)
    @Column(name = "away_team_uniform", length = 100)
    private String awayTeamUniform;

}