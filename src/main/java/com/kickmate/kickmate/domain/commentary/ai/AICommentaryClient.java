package com.kickmate.kickmate.domain.commentary.ai;

import com.kickmate.kickmate.domain.commentary.ai.dto.AICommentaryResponse;
import com.kickmate.kickmate.domain.commentary.ai.exception.AIClientException;
import com.kickmate.kickmate.domain.commentary.entity.RawActionEvent;
import com.kickmate.kickmate.domain.commentary.entity.RawMatchInfo;
import com.kickmate.kickmate.domain.commentary.enums.Style;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AICommentaryClient {

    private final WebClient webClient;

    public AICommentaryResponse requestCommentary(
            Long gameId,
            Style style,
            RawMatchInfo matchInfo,
            List<RawActionEvent> actionEvents
    ) {
        AICommentaryRequest request = AICommentaryRequest.of(gameId, style, matchInfo, actionEvents);

        return webClient.post()
                .uri("/ai/commentary")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, resp ->
                        resp.bodyToMono(String.class)
                                .defaultIfEmpty("")
                                .flatMap(body -> Mono.error(new AIClientException(
                                        "AI 4xx error: " + resp.statusCode() + " body=" + body
                                )))
                )
                .onStatus(HttpStatusCode::is5xxServerError, resp ->
                        resp.bodyToMono(String.class)
                                .defaultIfEmpty("")
                                .flatMap(body -> Mono.error(new AIClientException(
                                        "AI 5xx error: " + resp.statusCode() + " body=" + body
                                )))
                )
                .bodyToMono(AICommentaryResponse.class)
                .block();
    }


}
