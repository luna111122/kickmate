package com.kickmate.kickmate.domain.commentary.controller;

import com.kickmate.kickmate.domain.commentary.dto.StartCommentaryReq;
import com.kickmate.kickmate.domain.commentary.dto.StartCommentaryRes;
import com.kickmate.kickmate.domain.commentary.exception.code.CommentarySuccessCode;
import com.kickmate.kickmate.domain.commentary.service.CommentaryService;
import com.kickmate.kickmate.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)
@RequestMapping("/api/v1/commentary")
@RequiredArgsConstructor
public class CommentaryController {



    private CommentaryService commentaryService;

    //이거는 첫 해설
    @PostMapping
    public ApiResponse<?> startCommentary(
            @RequestBody @Valid StartCommentaryReq dto
            ){


        StartCommentaryRes startCommentary = commentaryService.startCommentary(dto);


        return ApiResponse.onSuccess(CommentarySuccessCode.COMMENTARY_FIRST_SUCCESS,startCommentary);
    }

    //다음에 알려주는 해설 mp3
    @PostMapping("/next")
    public ApiResponse<?> createCommentary(
            @RequestBody @Valid StartCommentaryReq dto
    ){


        String commentary = commentaryService.createCommentary(dto);

        return ApiResponse.onSuccess(CommentarySuccessCode.COMMENTARY_SUCCESS,commentary);
    }


}
