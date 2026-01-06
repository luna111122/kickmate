package com.kickmate.kickmate.domain.commentary.service;

import com.kickmate.kickmate.domain.commentary.dto.ActionCoordDto;
import com.kickmate.kickmate.domain.commentary.sse.AICommentarySseService;
import com.kickmate.kickmate.domain.commentary.sse.AiCommentarySseRes;
import com.kickmate.kickmate.domain.commentary.dto.AiWebhookReq;
import com.kickmate.kickmate.domain.commentary.entity.RawActionEvent;
import com.kickmate.kickmate.domain.commentary.repository.ActionEventRepository;
import com.kickmate.kickmate.domain.commentary.s3.S3Uploader;
import com.kickmate.kickmate.domain.commentary.ssml.SsmlBuilder;
import com.kickmate.kickmate.domain.commentary.tts.GoogleTtsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIWebhookWorker {

    private final GoogleTtsClient googleTtsClient;
    private final S3Uploader s3Uploader;
    private final ActionEventRepository actionEventRepository;
    private final AICommentarySseService aiCommentarySseService;

    @Async("aiWebhookExecutor")
    public void processAsync(AiWebhookReq req) {
        try {


            String ssml = SsmlBuilder.toSsml(req.getScript());

            byte[] mp3 = googleTtsClient.createTtsFromSsml(ssml);

            String key = "commentary/ai/" + req.getGameId() + "/" + req.getJobId() + ".mp3";
            String mp3Url = s3Uploader.upload(key, mp3, "audio/mpeg");


            Long gameId = req.getGameId();

            //이거 예외 나중에 코드로 처리
            Integer firstActionId = req.getScript().stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("script is empty"))
                    .getActionId();


            Pageable limit50 = PageRequest.of(0, 50);

            List<RawActionEvent> rawData = actionEventRepository.findByGameIdAndActionIdGreaterThanEqualOrderByActionIdAsc(gameId, firstActionId, limit50);

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


            // 7) 🔥 최종 결과 DTO 하나로 조립
            AiCommentarySseRes result = AiCommentarySseRes.builder()
                    .gameId(req.getGameId())
                    .jobId(req.getJobId())
                    .mp3Url(mp3Url)
                    .script(req.getScript())
                    .coords(coords)
                    .build();


            aiCommentarySseService.sendDone(req.getJobId(), result);





            // TODO (다음 단계에서 붙일 것)
            // 1) script -> ssml 파싱
            // 2) tts 생성
            // 3) s3 업로드
            // 좌표랑 선수 팀이름 한국어로 짧은 버전으로 주기
            // 4) SSE로 전달


        } catch (Exception e) {

            // TODO: 실패 상태를 DB에 저장 / 재시도 큐 넣기 등
        }
    }
}
