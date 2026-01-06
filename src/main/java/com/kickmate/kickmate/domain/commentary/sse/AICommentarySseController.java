package com.kickmate.kickmate.domain.commentary.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sse")
public class AICommentarySseController {

    private final AICommentarySseService aiCommentarySseService;

    @PostMapping (value = "/commentary/{jobId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable String jobId) {
        return aiCommentarySseService.subscribe(jobId);
    }
}
