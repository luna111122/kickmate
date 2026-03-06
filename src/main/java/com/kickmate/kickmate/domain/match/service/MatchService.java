package com.kickmate.kickmate.domain.match.service;


import com.kickmate.kickmate.domain.commentary.entity.RawMatchInfo;
import com.kickmate.kickmate.domain.commentary.repository.MatchInfoRepository;
import com.kickmate.kickmate.domain.match.dto.MatchFilterReq;
import com.kickmate.kickmate.domain.match.dto.MatchRes;
import com.kickmate.kickmate.domain.match.exceptions.MatchException;
import com.kickmate.kickmate.domain.match.exceptions.code.MatchErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchInfoRepository matchInfoRepository;

    public List<MatchRes> matchFilter(@Valid MatchFilterReq dto) {




        LocalDateTime startDate = LocalDateTime.of(Year.now().getValue(), dto.getMonth(), 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1);

        List<RawMatchInfo> matches = matchInfoRepository
                .findAllByRoundAndGameDateBetween(dto.getRound(), startDate, endDate);



        return matches.stream()
                .map(MatchRes::from)
                .toList();



    }

    public MatchRes matchInfo(Long gameId) {


        RawMatchInfo matchInfo = matchInfoRepository.findByGameId(gameId)
                .orElseThrow(() -> new MatchException(MatchErrorCode.MATCH_INFO_NOT_FOUND));

        return MatchRes.from(matchInfo);

    }
}
