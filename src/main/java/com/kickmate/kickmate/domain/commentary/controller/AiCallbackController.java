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
@RequestMapping("/api/callback")
public class AiCallbackController {

    private final AICallbackService aiCallbackService;

    // api/callback/ai-result 로 받는다
    @PostMapping("/ai-result")
    public ResponseEntity<Void> receiveAIResult(@Valid @RequestBody AiWebhookReq req) {



        aiCallbackService.handle(req);

        // 웹훅은 보통 "받았다"만 빨리 응답하는 게 국룰
        return ResponseEntity.ok().build();
    }
}
