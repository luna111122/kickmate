package com.kickmate.kickmate.domain.commentary.service;

import com.kickmate.kickmate.domain.commentary.dto.ActionCoordDto;
import com.kickmate.kickmate.domain.commentary.dto.ScoreRes;
import com.kickmate.kickmate.domain.commentary.entity.AiJob;
import com.kickmate.kickmate.domain.commentary.exception.CommentaryException;
import com.kickmate.kickmate.domain.commentary.exception.code.CommentaryErrorCode;
import com.kickmate.kickmate.domain.commentary.repository.AIJobRepository;
import com.kickmate.kickmate.domain.commentary.sse.AICommentarySseService;
import com.kickmate.kickmate.domain.commentary.sse.AiCommentarySseRes;
import com.kickmate.kickmate.domain.commentary.dto.AiWebhookReq;
import com.kickmate.kickmate.domain.commentary.entity.RawActionEvent;
import com.kickmate.kickmate.domain.commentary.repository.ActionEventRepository;
import com.kickmate.kickmate.domain.commentary.s3.S3Uploader;
import com.kickmate.kickmate.domain.commentary.ssml.SsmlBuilder;
import com.kickmate.kickmate.domain.commentary.tts.GoogleTtsClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AIWebhookWorker {

    private final GoogleTtsClient googleTtsClient;
    private final S3Uploader s3Uploader;
    private final ActionEventRepository actionEventRepository;
    private final AICommentarySseService aiCommentarySseService;
    private final AIJobRepository aiJobRepository;
    private final AIJobClaimService aiJobClaimService;

    @Async("aiWebhookExecutor")
    public void processAsync(AiWebhookReq req) {
        try {
            // AiJob 한 번만 조회 (style + clientId 모두 여기서 해결)
            AiJob aiJob = aiJobRepository.findByJobId(req.getJobId())
                    .orElseThrow(() -> new CommentaryException(CommentaryErrorCode.AI_JOB_NOT_FOUND));

            String ssml = SsmlBuilder.toSsml(req.getScript());
            byte[] mp3 = googleTtsClient.createTtsFromSsml(ssml, aiJob.getStyle());

            String key = "commentary/ai/" + req.getGameId() + "/" + req.getJobId() + ".mp3";
            String mp3Url = s3Uploader.upload(key, mp3, "audio/mpeg");

            Integer firstActionId = req.getScript().stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("script is empty"))
                    .getActionId();

            List<RawActionEvent> rawData = actionEventRepository
                    .findByGameIdAndActionIdGreaterThanEqualOrderByActionIdAsc(
                            req.getGameId(), firstActionId, PageRequest.of(0, 10)
                    );

            List<ActionCoordDto> coords = rawData.stream()
                    .map(r -> ActionCoordDto.builder()
                            .gameId(r.getGameId())
                            .actionId(r.getActionId())
                            .startX(r.getStartX())
                            .startY(r.getStartY())
                            .endX(r.getEndX())
                            .endY(r.getEndY())
                            .dx(r.getDx())
                            .dy(r.getDy())
                            .teamNameKoShort(r.getTeamNameKoShort())
                            .build()
                    )
                    .toList();

            List<ScoreRes> scoreList = rawData.stream()
                    .filter(e -> "Goal".equals(e.getTypeName()) || "Own Goal".equals(e.getTypeName()))
                    .map(event -> ScoreRes.builder()
                            .typeName(event.getTypeName())
                            .teamName(event.getTeamNameKoShort())
                            .actionId(event.getActionId())
                            .build()
                    )
                    .toList();

            AiCommentarySseRes result = AiCommentarySseRes.builder()
                    .gameId(req.getGameId())
                    .jobId(req.getJobId())
                    .mp3Url(mp3Url)
                    .script(req.getScript())
                    .coords(coords)
                    .score(scoreList)
                    .clientId(aiJob.getClientId())
                    .build();

            aiCommentarySseService.sendDone(req.getJobId(), result);

        } catch (Exception e) {
            log.error("[WEBHOOK] processAsync failed. jobId={}, gameId={}, error={}",
                    req.getJobId(), req.getGameId(), e.getMessage(), e);
            try {
                aiJobClaimService.markFailed(req.getJobId());
            } catch (Exception dbEx) {
                log.error("[WEBHOOK] failed to mark job as FAILED. jobId={}", req.getJobId(), dbEx);
            }
            aiCommentarySseService.sendError(req.getJobId(), "해설 생성 중 오류가 발생했습니다.");
        }
    }
}
