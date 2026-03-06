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
        String jobId = requestAiAndSaveJob(dto);

        RawFillerScript script = fillerScriptRepository
                .findFirstByGameId(dto.getGameId())
                .orElseThrow(() -> new CommentaryException(
                        CommentaryErrorCode.FILLER_SCRIPT_NOT_FOUND
                ));

        byte[] tts = googleTtsClient.createTts(script.getCommentary(), dto.getStyle());

        String key = "commentary/filler/" + dto.getGameId() + "/" + jobId + ".mp3";
        String fillerUrl = s3Uploader.upload(key, tts, "audio/mpeg");

        return StartCommentaryRes.builder()
                .fillerAudioUrl(fillerUrl)
                .jobId(jobId)
                .build();
    }


    public String createCommentary(@Valid StartCommentaryReq dto) {
        return requestAiAndSaveJob(dto);
    }

    private String requestAiAndSaveJob(StartCommentaryReq dto) {
        String jobId = aiCommentaryOrchestrator.createScript(dto);

        AiJob job = AiJob.builder()
                .jobId(jobId)
                .gameId(dto.getGameId())
                .status(Status.PENDING)
                .clientId(dto.getClientId())
                .createdAt(LocalDateTime.now())
                .style(dto.getStyle())
                .build();

        aiJobRepository.save(job);
        return jobId;
    }
}
