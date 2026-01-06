package com.kickmate.kickmate.domain.commentary.ssml;

import com.kickmate.kickmate.domain.commentary.dto.AiWebhookReq;

import java.util.List;

public class SsmlBuilder {

    // 문장 사이 기본 텀 (원하면 150~300ms로 조절)
    private static final String DEFAULT_BREAK = "200ms";

    public static String toSsml(List<AiWebhookReq.ScriptLine> script) {
        StringBuilder sb = new StringBuilder();
        sb.append("<speak>");

        if (script != null) {
            for (int i = 0; i < script.size(); i++) {
                AiWebhookReq.ScriptLine line = script.get(i);
                String text = XmlEscaper.escape(line.getDescription());

                // 빈 문장 스킵
                if (text.isBlank()) continue;

                var prosody = SsmlToneMapper.map(line.getTone().name());

                // QUESTION 톤이면 물음표 없을 때만 붙이는 옵션 (원치 않으면 제거)
                if ("QUESTION".equalsIgnoreCase(line.getTone().name()) && !endsWithQuestionMark(text)) {
                    text = text + "?";
                }

                if (prosody == null) {
                    sb.append(text);
                } else {
                    sb.append("<prosody rate=\"")
                            .append(prosody.rate())
                            .append("\" pitch=\"")
                            .append(prosody.pitch())
                            .append("\">")
                            .append(text)
                            .append("</prosody>");
                }

                // 문장 간 break
                if (i < script.size() - 1) {
                    sb.append("<break time=\"").append(DEFAULT_BREAK).append("\"/>");
                }
            }
        }

        sb.append("</speak>");
        return sb.toString();
    }

    private static boolean endsWithQuestionMark(String text) {
        String t = text.trim();
        return t.endsWith("?") || t.endsWith("？");
    }
}
