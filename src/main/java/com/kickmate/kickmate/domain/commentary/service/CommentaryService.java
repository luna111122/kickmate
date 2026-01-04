package com.kickmate.kickmate.domain.commentary.service;

import com.kickmate.kickmate.domain.commentary.ai.AICommentaryOrchestrator;
import com.kickmate.kickmate.domain.commentary.dto.StartCommentaryReq;
import com.kickmate.kickmate.domain.commentary.entity.RawFillerScript;
import com.kickmate.kickmate.domain.commentary.exception.CommentaryException;
import com.kickmate.kickmate.domain.commentary.exception.code.CommentaryErrorCode;
import com.kickmate.kickmate.domain.commentary.repository.FillerScriptRepository;
import com.kickmate.kickmate.domain.commentary.tts.GoogleTtsClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentaryService {

    private final FillerScriptRepository fillerScriptRepository;
    private final GoogleTtsClient googleTtsClient;
    private final AICommentaryOrchestrator aiCommentaryOrchestrator;


    public byte[] startCommentary(@Valid StartCommentaryReq dto) {


        /*
        (프론트) 이 부분은 재사용은 안함
        자 일단 여기서는 fillerscript 리포에서 해당하는 경기 id 에 대해서
           filler script 를 가져와서 이를 mp3 에 보내고 이를 스크립트와 함께 보내준다

        (ai) 이 부분은 나중에 또 사용해야 해서 따로 뺴고 재사용
        해당하는 경기 id 와 스타일과 action id를 드리고 이에 대한 50행의 정보와
        matchInfo 전체 정보를 준다


        이후 받은 jobId 를 스케쥴링 하는데 안에다가 넣어놔야 한다
         */

        //ai
        aiCommentaryOrchestrator.






        //프론트

        RawFillerScript script = fillerScriptRepository
                .findFirstByGameId(dto.getGameId())
                .orElseThrow(() -> new CommentaryException(
                        CommentaryErrorCode.FILLER_SCRIPT_NOT_FOUND
                ));

        byte[] tts = googleTtsClient.createTts(script.getCommentary(), dto.getStyle());

        return tts;

    }
}
