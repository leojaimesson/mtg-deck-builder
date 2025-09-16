package com.mtg.deck_builder.cards.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
    private static final Logger log = LoggerFactory.getLogger(StringUtil.class);

    public static String sanitize(String input) {
        log.info("[STRING UTIL] === Starting string sanitization ===");

        if (input == null || input.isEmpty()) {
            log.info("[STRING UTIL] Input is null or empty. Returning as is.");
            return input;
        }

        log.info("[STRING UTIL] Original input: '{}'", input);

        // Replace non-alphanumeric characters with space
        String intermediate = input.replaceAll("[^a-zA-Z0-9]", " ");
        log.info("[STRING UTIL] After replacing non-alphanumeric characters: '{}'", intermediate);

        // Reduce multiple spaces to a single space and trim
        String result = intermediate.replaceAll("\\s+", " ").trim();
        log.info("[STRING UTIL] Final sanitized string: '{}'", result);

        log.info("[STRING UTIL] === Finished string sanitization ===");

        return result;
    }
}
