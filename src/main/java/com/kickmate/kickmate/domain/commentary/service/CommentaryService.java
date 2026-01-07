package com.kickmate.kickmate.domain.commentary.service;

import com.kickmate.kickmate.domain.commentary.ai.AICommentaryOrchestrator;
import com.kickmate.kickmate.domain.commentary.dto.StartCommentaryReq;
import com.kickmate.kickmate.domain.commentary.dto.StartCommentaryRes;
import com.kickmate.kickmate.domain.commentary.entity.AiJob;
import com.kickmate.kickmate.domain.commentary.entity.RawFillerScript;
import com.kickmate.kickmate.domain.commentary.enums.Status;
import com.kickmate.kickmate.domain.commentary.exception.CommentaryException;
import com.kickmate.kickmate.domain.commentary.exception.code.CommentaryErrorCode;
import com.kickmate.kickmate.domain.commentary.repository.AIJobRepository;
import com.kickmate.kickmate.domain.commentary.repository.FillerScriptRepository;
import com.kickmate.kickmate.domain.commentary.s3.S3Uploader;
import com.kickmate.kickmate.domain.commentary.tts.GoogleTtsClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentaryService {

    private final FillerScriptRepository fillerScriptRepository;
    private final GoogleTtsClient googleTtsClient;
    private final AICommentaryOrchestrator aiCommentaryOrchestrator;
    private final S3Uploader s3Uploader;
    private final AIJobRepository aiJobRepository;


    public StartCommentaryRes startCommentary(@Valid StartCommentaryReq dto) {


        /*
        (프론트) 이 부분은 재사용은 안함
        자 일단 여기서는 fillerscript 리포에서 해당하는 경기 id 에 대해서
           filler script 를 가져와서 이를 mp3 에 보내고 이를 스크립트와 함께 보내준다

        (ai) 이 부분은 나중에 또 사용해야 해서 따로 뺴고 재사용
        해당하는 경기 id 와 스타일과 action id를 드리고 이에 대한 50행의 정보와
        matchInfo 전체 정보를 준다


        이후 받은 jobId 를 스케쥴링 하는데 안에다가 넣어놔야 한다
         */

        /*//ai
        String jobId = aiCommentaryOrchestrator.createScript(dto);

        //aiJob 엔티티에 jobId, 그리고 clientId 와 함께 저장해서 나중에 보내주기
        AiJob job = AiJob.builder()
                .jobId(jobId)
                .gameId(dto.getGameId())
                .status(Status.PENDING)
                .clientId(dto.getClientId())
                .createdAt(LocalDateTime.now())
                .build();

        //clientId 까지 저장해둔다
        aiJobRepository.save(job);*/


        //프론트

        //필러멘트 스크립트 mp3 생성
        RawFillerScript script = fillerScriptRepository
                .findFirstByGameId(dto.getGameId())
                .orElseThrow(() -> new CommentaryException(
                        CommentaryErrorCode.FILLER_SCRIPT_NOT_FOUND
                ));

        byte[] tts = googleTtsClient.createTts(script.getCommentary(), dto.getStyle());


        String jobId = "1234567";

        // mp3 S3 업로드
        String key = "commentary/filler/" + dto.getGameId() + "/" + jobId + ".mp3";
        String fillerUrl = s3Uploader.upload(key, tts, "audio/mpeg");

        return StartCommentaryRes.builder()
                .fillerAudioUrl(fillerUrl)
                .jobId(jobId)
                .build();

    }


    public String createCommentary(@Valid StartCommentaryReq dto) {

        //ai
        String jobId = aiCommentaryOrchestrator.createScript(dto);

        //aiJob 엔티티에 jobId, 그리고 clientId 와 함께 저장해서 나중에 보내주기
        AiJob job = AiJob.builder()
                .jobId(jobId)
                .gameId(dto.getGameId())
                .status(Status.PENDING)
                .clientId(dto.getClientId())
                .createdAt(LocalDateTime.now())
                .build();

        //clientId 까지 저장해둔다
        aiJobRepository.save(job);


        return jobId;

    }
}


