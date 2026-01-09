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


    private static final AudioConfig AUDIO_CONFIG = AudioConfig.newBuilder()
            .setAudioEncoding(AudioEncoding.MP3)
            .build();

    // commentary + style -> ssml -> mp3
    public byte[] createTts(@NotNull String commentary, Style style) {
        log.info("[TTS] createTts start | style={}", style);

        // 얘는 그냥 여기다가 voice 바꾸면 될듯
        String ssml = generalToneConverter.buildSsml(commentary, style);
        log.debug("[TTS] SSML built | length={}", ssml.length());

        byte[] audio = synthesizeFromSsml(ssml,style);

        log.info("[TTS] createTts success | audioBytes={}", audio.length);
        return audio;
    }

    // SSML을 이미 만들어놨을 때
    public byte[] createTtsFromSsml(@NotNull String ssml, Style style) {
        log.info("[TTS] createTtsFromSsml start | ssmlLength={}", ssml.length());

        byte[] audio = synthesizeFromSsml(ssml,style);

        log.info("[TTS] createTtsFromSsml success | audioBytes={}", audio.length);
        return audio;
    }

    // 실제 합성
    //여기서 Style 받아서 Voice 를 다르게 받는거를 해봐야 한다
    private byte[] synthesizeFromSsml(String ssml, Style style) {
        try {
            log.debug("[TTS] synthesize start");

            validateSsml(ssml);

            SynthesisInput input = SynthesisInput.newBuilder()
                    .setSsml(ssml)
                    .build();

            SsmlVoiceGender gender = resolveGender(style);

            VoiceSelectionParams VOICE = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("ko-KR")
                    .setSsmlGender(gender)
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

    private SsmlVoiceGender resolveGender(Style style) {
        return switch (style) {
            case CASTER -> SsmlVoiceGender.MALE;     // 캐스터: 남성
            case ANALYST -> SsmlVoiceGender.FEMALE;  // 분석가: 여성
            case FRIEND -> SsmlVoiceGender.NEUTRAL;  // 친구: 중성
        };
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
