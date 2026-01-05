package com.kickmate.kickmate.domain.commentary.ai.dto;


import com.kickmate.kickmate.domain.commentary.entity.RawMatchInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MatchInfoDto {
    private Long gameId;
    private Long seasonId;
    private Long competitionId;
    private Integer gameDay;
    private LocalDateTime gameDate;

    private Long homeTeamId;
    private Long awayTeamId;
    private Integer homeScore;
    private Integer awayScore;

    private String venue;
    private String competitionName;
    private String countryName;
    private String seasonName;

    private String homeTeamName;
    private String homeTeamNameKo;
    private String homeTeamNameKoShort;

    private String awayTeamName;
    private String awayTeamNameKo;
    private String awayTeamNameKoShort;

    private Integer audienceNum;
    private String temperature;
    private String weather;

    private String referee;
    private String assistantReferees;
    private String fourthOfficial;
    private String varReferees;
    private String tsg;

    private Integer homeRank;
    private Integer awayRank;

    private String homeTeamUniform;
    private String awayTeamUniform;

    public static MatchInfoDto from(RawMatchInfo m) {
        return MatchInfoDto.builder()
                .gameId(m.getGameId())
                .seasonId(m.getSeasonId())
                .competitionId(m.getCompetitionId())
                .gameDay(m.getGameDay())
                .gameDate(m.getGameDate())
                .homeTeamId(m.getHomeTeamId())
                .awayTeamId(m.getAwayTeamId())
                .homeScore(m.getHomeScore())
                .awayScore(m.getAwayScore())
                .venue(m.getVenue())
                .competitionName(m.getCompetitionName())
                .countryName(m.getCountryName())
                .seasonName(m.getSeasonName())
                .homeTeamName(m.getHomeTeamName())
                .homeTeamNameKo(m.getHomeTeamNameKo())
                .homeTeamNameKoShort(m.getHomeTeamNameKoShort())
                .awayTeamName(m.getAwayTeamName())
                .awayTeamNameKo(m.getAwayTeamNameKo())
                .awayTeamNameKoShort(m.getAwayTeamNameKoShort())
                .audienceNum(m.getAudienceNum())
                .temperature(m.getTemperature())
                .weather(m.getWeather())
                .referee(m.getReferee())
                .assistantReferees(m.getAssistantReferees())
                .fourthOfficial(m.getFourthOfficial())
                .varReferees(m.getVarReferees())
                .tsg(m.getTsg())
                .homeRank(m.getHomeRank())
                .awayRank(m.getAwayRank())
                .homeTeamUniform(m.getHomeTeamUniform())
                .awayTeamUniform(m.getAwayTeamUniform())
                .build();
    }
}