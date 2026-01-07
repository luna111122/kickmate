package com.kickmate.kickmate.domain.commentary.tts;

import com.google.cloud.texttospeech.v1.*;
import com.kickmate.kickmate.domain.commentary.enums.Style;
import com.kickmate.kickmate.domain.commentary.exception.CommentaryException;
import com.kickmate.kickmate.domain.commentary.exception.code.CommentaryErrorCode;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
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

    // commentary + style -> ssml -> mp3
    public byte[] createTts(@NotNull String commentary, Style style) {
        log.info("[TTS] createTts start | style={}", style);

        String ssml = generalToneConverter.buildSsml(commentary, style);
        log.debug("[TTS] SSML built | length={}", ssml.length());

        byte[] audio = synthesizeFromSsml(ssml);

        log.info("[TTS] createTts success | audioBytes={}", audio.length);
        return audio;
    }

    // SSML을 이미 만들어놨을 때
    public byte[] createTtsFromSsml(@NotNull String ssml) {
        log.info("[TTS] createTtsFromSsml start | ssmlLength={}", ssml.length());

        byte[] audio = synthesizeFromSsml(ssml);

        log.info("[TTS] createTtsFromSsml success | audioBytes={}", audio.length);
        return audio;
    }

    // 실제 합성
    private byte[] synthesizeFromSsml(String ssml) {
        try {
            log.debug("[TTS] synthesize start");

            validateSsml(ssml);

            SynthesisInput input = SynthesisInput.newBuilder()
                    .setSsml(ssml)
                    .build();

            SynthesizeSpeechResponse response =
                    ttsClient.synthesizeSpeech(input, VOICE, AUDIO_CONFIG);

            byte[] audio = response.getAudioContent().toByteArray();

            log.info("[TTS] synthesize success | audioBytes={}", audio.length);
            return audio;

        } catch (Exception e) {
            log.error("[TTS] synthesize failed", e);
            throw new CommentaryException(CommentaryErrorCode.AI_REQUEST_FAILED);
        }
    }

    private void validateSsml(String ssml) {
        String trimmed = ssml == null ? "" : ssml.trim();

        if (!trimmed.startsWith("<speak>")) {
            log.warn("[TTS] invalid SSML: missing <speak>");
            throw new IllegalArgumentException("SSML must start with <speak>");
        }
        if (!trimmed.endsWith("</speak>")) {
            log.warn("[TTS] invalid SSML: missing </speak>");
            throw new IllegalArgumentException("SSML must end with </speak>");
        }

        log.debug("[TTS] SSML validation passed");
    }
}
