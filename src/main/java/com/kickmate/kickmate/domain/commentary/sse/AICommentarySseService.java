package com.kickmate.kickmate.domain.commentary.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class AICommentarySseService {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    private static final long TIMEOUT_MS = 10 * 60 * 1000L; // 10분

    public SseEmitter subscribe(String jobId) {
        SseEmitter emitter = new SseEmitter(TIMEOUT_MS);
        emitters.put(jobId, emitter);

        emitter.onCompletion(() -> {
            emitters.remove(jobId);
            log.info("[SSE] completed jobId={}", jobId);
        });

        emitter.onTimeout(() -> {
            emitters.remove(jobId);
            log.info("[SSE] timeout jobId={}", jobId);
        });

        emitter.onError(e -> {
            emitters.remove(jobId);
            log.warn("[SSE] error jobId={} msg={}", jobId, e.toString());
        });

        // 연결 확인 이벤트
        try {
            emitter.send(SseEmitter.event().name("connected").data("ok"));
        } catch (IOException e) {
            emitters.remove(jobId);
        }

        return emitter;
    }

    public void sendDone(String jobId, AiCommentarySseRes resultDto) {
        SseEmitter emitter = emitters.get(jobId);

        if (emitter == null) {
            log.info("[SSE] no subscriber jobId={} (result dropped)", jobId);
            return;
        }

        try {
            emitter.send(SseEmitter.event()
                    .name("done")
                    .data(resultDto));

            // DONE 이벤트 보냈으면 연결 종료
            emitter.complete();

        } catch (IOException e) {
            emitters.remove(jobId);
            log.warn("[SSE] send failed jobId={} msg={}", jobId, e.toString());
        }
    }
}