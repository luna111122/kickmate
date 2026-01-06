package com.kickmate.kickmate.domain.commentary.ai;

import com.kickmate.kickmate.domain.commentary.ai.dto.AICommentaryResponse;
import com.kickmate.kickmate.domain.commentary.dto.StartCommentaryReq;
import com.kickmate.kickmate.domain.commentary.entity.RawActionEvent;
import com.kickmate.kickmate.domain.commentary.entity.RawMatchInfo;
import com.kickmate.kickmate.domain.commentary.exception.CommentaryException;
import com.kickmate.kickmate.domain.commentary.exception.code.CommentaryErrorCode;
import com.kickmate.kickmate.domain.commentary.repository.ActionEventRepository;
import com.kickmate.kickmate.domain.commentary.repository.MatchInfoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AICommentaryOrchestrator {

    private final AICommentaryClient aiCommentaryClient;
    private final ActionEventRepository actionEventRepository;
    private final MatchInfoRepository matchInfoRepository;


    public String createScript(@Valid StartCommentaryReq dto) {


        //여기서 분기 처리 만약 해당 gameId와 actionId 에 대한 내용이 만약 있다면 그냥 반환한다

        // table 에 있어야 하는거
        /*
        gameId, actionId, clientId??? 이거를 어케 주지
        아니 이러면 webhook 이 안와서 sse 도 안가는데?
        sse 를 트리거를 어케 하지

        만약 여기서 sse를 트리거 하면 이거는
         */



        //ai 한테 줄 정보 찾기
        RawMatchInfo matchInfo = matchInfoRepository
                .findByGameId(dto.getGameId())
                .orElseThrow(() -> new CommentaryException(
                        CommentaryErrorCode.MATCH_INFO_NOT_FOUND
                ));



        Pageable limit50 = PageRequest.of(0, 50);

        List<RawActionEvent> actionEvents =
                actionEventRepository
                        .findByGameIdAndActionIdGreaterThanEqualOrderByActionIdAsc(
                                dto.getGameId(),
                                dto.getActionId(),
                                limit50
                        );

        if (actionEvents.isEmpty()) {
            throw new CommentaryException(
                    CommentaryErrorCode.ACTION_EVENT_NOT_FOUND
            );
        }

        //실제로 요청 보내기
        AICommentaryResponse aiResponse = aiCommentaryClient.requestCommentary(dto.getGameId(), dto.getStyle(), matchInfo, actionEvents);


        return aiResponse.getJobId();
    }
}
