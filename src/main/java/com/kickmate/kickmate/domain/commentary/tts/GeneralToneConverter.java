package com.kickmate.kickmate.domain.commentary.tts;

import com.kickmate.kickmate.domain.commentary.enums.Style;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.kickmate.kickmate.domain.commentary.enums.Style.*;

@Component
@Slf4j
public class GeneralToneConverter {

    /**
     * Style → SSML 변환
     */
    public String buildSsml(String commentary, Style style) {

        log.info("[TTS][SSML] buildSsml start | style={}", style);
        log.debug("[TTS][SSML] commentary length={}", commentary != null ? commentary.length() : 0);

        String ssml = switch (style) {

            case CASTER -> """
                <speak>
                  <prosody rate="fast" pitch="+2st">
                    %s
                  </prosody>
                </speak>
                """.formatted(commentary);

            case ANALYST -> """
                <speak>
                  <prosody rate="medium" pitch="-1st">
                    %s
                  </prosody>
                </speak>
                """.formatted(commentary);

            case FRIEND -> """
                <speak>
                  <prosody rate="slow" pitch="+1st">
                    %s
                  </prosody>
                </speak>
                """.formatted(commentary);
        };

        log.info("[TTS][SSML] buildSsml success | ssmlLength={}", ssml.length());

        return ssml;
    }
}
