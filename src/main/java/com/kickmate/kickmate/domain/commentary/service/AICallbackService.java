package com.kickmate.kickmate.domain.commentary.service;

import com.kickmate.kickmate.domain.commentary.dto.AiWebhookReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AICallbackService {

    private final AIWebhookWorker aiWebhookWorker;
    private final AIJobClaimService aiJobClaimService;

    public void handle(AiWebhookReq req) {

        boolean firstDone = aiJobClaimService
                .markDoneIfFirst(req.getJobId(), req.getGameId());

        if (!firstDone) {
            // 중복인 경우 그냥 되돌려 보낸다
            return;
        }

        // 여기서는 "접수"만 하고 끝.
        // (가능하면 jobId 멱등 체크 후) 비동기 호출
        aiWebhookWorker.processAsync(req);
    }


}
