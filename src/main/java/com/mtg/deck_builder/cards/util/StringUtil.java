package com.mtg.deck_builder.cards.util;

public class StringUtil {
    public static String sanitize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String result = input.replaceAll("[^a-zA-Z0-9]", " ");

        result = result.replaceAll("\\s+", " ").trim();

        return result;
    }
}

