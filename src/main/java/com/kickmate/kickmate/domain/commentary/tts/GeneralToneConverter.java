package com.kickmate.kickmate.domain.commentary.tts;

import com.kickmate.kickmate.domain.commentary.enums.Style;
import org.springframework.stereotype.Component;

import static com.kickmate.kickmate.domain.commentary.enums.Style.*;

@Component
public class GeneralToneConverter {

    /**
     * Style → SSML 변환
     */
    public String buildSsml(String commentary, Style style) {

        return switch (style) {

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
    }

}
