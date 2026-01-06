package com.kickmate.kickmate.domain.match.controller;


import com.kickmate.kickmate.domain.match.dto.MatchFilterReq;
import com.kickmate.kickmate.domain.match.dto.MatchRes;
import com.kickmate.kickmate.domain.match.exceptions.code.MatchSuccessCode;
import com.kickmate.kickmate.domain.match.service.MatchService;
import com.kickmate.kickmate.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    public ApiResponse<?> matchFilter(
            @RequestBody @Valid MatchFilterReq dto
    ){

        List<MatchRes> matches = matchService.matchFilter(dto);

        return ApiResponse.onSuccess(MatchSuccessCode.MATCH_FILTER_SUCCESS,matches);


    }


    @PostMapping("/{gameId}")
    public ApiResponse<?> matchInfo(
            @PathVariable Long gameId
    ){

        MatchRes matchInfo = matchService.matchInfo(gameId);

        return ApiResponse.onSuccess(MatchSuccessCode.MATCH_INFO_SUCCESS,matchInfo);


    }




}
