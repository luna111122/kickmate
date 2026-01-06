package com.kickmate.kickmate.domain.commentary.tts;

import com.google.cloud.texttospeech.v1.*;
import com.kickmate.kickmate.domain.commentary.enums.Style;
import com.kickmate.kickmate.domain.commentary.exception.CommentaryException;
import com.kickmate.kickmate.domain.commentary.exception.code.CommentaryErrorCode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GoogleTtsClient {

    private final TextToSpeechClient ttsClient;
    private final GeneralToneConverter generalToneConverter;


    // voice/audioConfig는 고정이면 필드로 캐싱해도 됨
    private static final VoiceSelectionParams VOICE = VoiceSelectionParams.newBuilder()
            .setLanguageCode("ko-KR")
            .setSsmlGender(SsmlVoiceGender.NEUTRAL)
            .build();

    private static final AudioConfig AUDIO_CONFIG = AudioConfig.newBuilder()
            .setAudioEncoding(AudioEncoding.MP3)
            .build();

    //  기존 시그니처 유지: commentary + style -> ssml -> mp3
    public byte[] createTts(@NotNull String commentary, Style style) {
        String ssml = generalToneConverter.buildSsml(commentary, style);
        return synthesizeFromSsml(ssml);
    }

    //  SSML을 이미 만들어놨을 때
    public byte[] createTtsFromSsml(@NotNull String ssml) {
        return synthesizeFromSsml(ssml);
    }

    //  중복 제거: 실제 합성은 여기 한 곳에서만
    private byte[] synthesizeFromSsml(String ssml) {
        try {
            validateSsml(ssml);

            SynthesisInput input = SynthesisInput.newBuilder()
                    .setSsml(ssml)
                    .build();

            SynthesizeSpeechResponse response =
                    ttsClient.synthesizeSpeech(input, VOICE, AUDIO_CONFIG);

            return response.getAudioContent().toByteArray();

        } catch (Exception e) {
            throw new CommentaryException(CommentaryErrorCode.AI_REQUEST_FAILED);
        }
    }

    private void validateSsml(String ssml) {
        String trimmed = ssml == null ? "" : ssml.trim();
        if (!trimmed.startsWith("<speak>")) {
            throw new IllegalArgumentException("SSML must start with <speak>");
        }
        if (!trimmed.endsWith("</speak>")) {
            throw new IllegalArgumentException("SSML must end with </speak>");
        }
    }
}
