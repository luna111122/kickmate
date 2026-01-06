package com.kickmate.kickmate.domain.commentary.ssml;

import java.util.Locale;

public class SsmlToneMapper {

    public record Prosody(String rate, String pitch) {
    }

    public static Prosody map(String toneRaw) {
        if (toneRaw == null) return null;

        String tone = toneRaw.trim().toUpperCase(Locale.ROOT);

        return switch (tone) {
            case "SAD" -> new Prosody("slow", "-2st");
            case "ANGRY" -> new Prosody("fast", "+1st");
            case "CALM" -> new Prosody("slow", "-1st");
            case "QUESTION" -> new Prosody("medium", "+1st");
            case "EXCITED" -> new Prosody("fast", "+3st");
            case "EMPHASIS" -> new Prosody("medium", "+2st");
            case "DEFAULT" -> null;
            default -> null; // 알 수 없는 값은 DEFAULT 취급
        };
    }
}