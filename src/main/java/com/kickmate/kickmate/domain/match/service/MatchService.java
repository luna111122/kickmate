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

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchInfoRepository matchInfoRepository;

    public List<MatchRes> matchFilter(@Valid MatchFilterReq dto) {




        List<RawMatchInfo> matches = matchInfoRepository
                .findAllByMonthAndRound(dto.getMonth(), dto.getRound());



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
