package com.kickmate.kickmate.domain.commentary.service;

import com.kickmate.kickmate.domain.commentary.dto.AiWebhookReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AICallbackService {

    private final AIWebhookWorker aiWebhookWorker;
    private final AIJobClaimService aiJobClaimService;

    public void handle(AiWebhookReq req) {

        // [JOB-CLAIM] 해당 jobId가 최초 완료 처리인지 멱등 체크 시작
        log.info("[AI CALLBACK] try to claim job. jobId={}, gameId={}",
                req.getJobId(), req.getGameId());

        boolean firstDone = aiJobClaimService
                .markDoneIfFirst(req.getJobId(), req.getGameId());

        if (!firstDone) {
            // [DUPLICATE-WEBHOOK] 이미 처리된 jobId → 중복 웹훅 무시
            log.warn("[AI CALLBACK] duplicate webhook ignored. jobId={}, gameId={}",
                    req.getJobId(), req.getGameId());
            return;
        }

        // [JOB-CLAIMED] 최초 웹훅으로 job 소유권 확보 완료
        log.info("[AI CALLBACK] job claimed successfully. jobId={}, gameId={}",
                req.getJobId(), req.getGameId());

        // [ASYNC-PROCESS] 실제 처리는 비동기로 위임 (TTS, S3, SSE 등)
        aiWebhookWorker.processAsync(req);

        // [ASYNC-DISPATCHED] 비동기 작업 위임 완료
        log.info("[AI CALLBACK] async processing dispatched. jobId={}",
                req.getJobId());
    }
}
