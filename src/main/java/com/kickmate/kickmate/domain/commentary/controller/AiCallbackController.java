package com.kickmate.kickmate.domain.commentary.controller;


import com.kickmate.kickmate.domain.commentary.dto.AiWebhookReq;
import com.kickmate.kickmate.domain.commentary.service.AICallbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai/callback")
public class AiCallbackController {

    private final AICallbackService aiCallbackService;

    // api/callback/ai-result 로 받는다
    @PostMapping("/ai-result")
    public ResponseEntity<Void> receiveAIResult(@Valid @RequestBody AiWebhookReq req) {

        // [WEBHOOK-RECEIVE] AI 서버로부터 웹훅 수신 시작
        log.info("[AI CALLBACK] webhook received. gameId={}, jobId={}",
                req.getGameId(), req.getJobId());

        // [WEBHOOK-PROCESS] 웹훅 처리 로직 위임 (비동기/동기 여부는 서비스 책임)
        aiCallbackService.handle(req);

        // [WEBHOOK-ACK] AI 서버에 정상 수신 응답 반환
        log.info("[AI CALLBACK] webhook processed successfully. jobId={}",
                req.getJobId());

        // 웹훅은 받았다 라고 빨리 응답
        return ResponseEntity.ok().build();
    }
}
