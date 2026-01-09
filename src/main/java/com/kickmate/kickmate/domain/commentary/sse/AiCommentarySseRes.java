package com.kickmate.kickmate.domain.commentary.sse;


import com.kickmate.kickmate.domain.commentary.dto.ActionCoordDto;
import com.kickmate.kickmate.domain.commentary.dto.AiWebhookReq;
import com.kickmate.kickmate.domain.commentary.dto.ScoreRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiCommentarySseRes {

    private Long gameId;
    private String jobId;


    // 생성된 mp3 URL
    private String mp3Url;

    // 해설 스크립트 (원본 or 가공본)
    private List<AiWebhookReq.ScriptLine> script;

    // 좌표 + 팀 정보
    private List<ActionCoordDto> coords;

    private List<ScoreRes> score;

    //프론트용 clientId
    private String clientId;
}
