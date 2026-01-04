package com.kickmate.kickmate.domain.commentary.controller;

import com.kickmate.kickmate.domain.commentary.dto.StartCommentaryReq;
import com.kickmate.kickmate.domain.commentary.exception.code.CommentarySuccessCode;
import com.kickmate.kickmate.domain.commentary.service.CommentaryService;
import com.kickmate.kickmate.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/commentary")
@RequiredArgsConstructor
public class CommentaryController {

   /* @PostMapping("/sign-up")
    public ApiResponse<MemberResDTO.JoinDTO> signUp(
            @RequestBody @Valid MemberReqDTO.JoinDTO dto
    ){
        return ApiResponse.onSuccess(MemberSuccessCode.FOUND, memberCommandService.signup(dto));
    }*/

    private CommentaryService commentaryService;

    //이거는 첫 해설
    /*@PostMapping
    public ApiResponse<?> startCommentary(
            @RequestBody @Valid StartCommentaryReq dto
            ){


        byte[] tts = commentaryService.startCommentary(dto);


        return ApiResponse.onSuccess(CommentarySuccessCode.COMMENTARY_FIRST_SUCCESS,tts);
    }*/

    @PostMapping
    public ResponseEntity<byte[]> startCommentary(
            @RequestBody @Valid StartCommentaryReq dto
    ){


        byte[] tts = commentaryService.startCommentary(dto);


        return ResponseEntity.ok(tts);
    }

    //이거는 jobId 만으로 부르는거


    //이거는 두번쨰 해설인데 jobId가 없는 상태

}
