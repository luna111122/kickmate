package com.kickmate.kickmate.domain.match.dto;

import com.kickmate.kickmate.domain.commentary.entity.RawMatchInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MatchRes {

    private Long id;

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

    private String audienceNum;


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



    public static MatchRes from(RawMatchInfo e) {
        return MatchRes.builder()
                .id(e.getId())

                .gameId(e.getGameId())
                .seasonId(e.getSeasonId())
                .competitionId(e.getCompetitionId())

                .gameDay(e.getGameDay())
                .gameDate(e.getGameDate())

                .homeTeamId(e.getHomeTeamId())
                .awayTeamId(e.getAwayTeamId())

                .homeScore(e.getHomeScore())
                .awayScore(e.getAwayScore())

                .venue(e.getVenue())

                .competitionName(e.getCompetitionName())
                .countryName(e.getCountryName())
                .seasonName(e.getSeasonName())

                .homeTeamName(e.getHomeTeamName())
                .homeTeamNameKo(e.getHomeTeamNameKo())
                .homeTeamNameKoShort(e.getHomeTeamNameKoShort())

                .awayTeamName(e.getAwayTeamName())
                .awayTeamNameKo(e.getAwayTeamNameKo())
                .awayTeamNameKoShort(e.getAwayTeamNameKoShort())

                .audienceNum(formatAudience(e.getAudienceNum()))


                .temperature(e.getTemperature())
                .weather(e.getWeather())

                .referee(e.getReferee())
                .assistantReferees(e.getAssistantReferees())
                .fourthOfficial(e.getFourthOfficial())
                .varReferees(e.getVarReferees())
                .tsg(e.getTsg())

                .homeRank(e.getHomeRank())
                .awayRank(e.getAwayRank())

                .homeTeamUniform(e.getHomeTeamUniform())
                .awayTeamUniform(e.getAwayTeamUniform())
                .build();
    }

    private static String formatAudience(Integer num) {
        if (num == null) return null;
        return String.format("%,d", num);
    }

}
