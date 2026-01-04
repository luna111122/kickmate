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

    private final GeneralToneConverter generalToneConverter;


    public byte[] createTts(@NotNull String commentary, Style style) {


        try (TextToSpeechClient client = TextToSpeechClient.create()) {

            String ssml = generalToneConverter.buildSsml(commentary, style);

            SynthesisInput input = SynthesisInput.newBuilder()
                    .setSsml(ssml)
                    .build();

            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("ko-KR")
                    .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                    .build();

            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3)
                    .build();

            SynthesizeSpeechResponse response =
                    client.synthesizeSpeech(input, voice, audioConfig);

            return response.getAudioContent().toByteArray();

        } catch (Exception e) {
            throw new CommentaryException(
                    CommentaryErrorCode.AI_REQUEST_FAILED
            );
        }
    }
}
