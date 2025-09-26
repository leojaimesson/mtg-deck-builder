package com.mtg.deck_builder.cards.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class HashUtil {
    private static final Logger log = LoggerFactory.getLogger(HashUtil.class);

    public static String generateId() {
        log.info("[HASH UTIL] === Starting hash generation ===");

        try {
            String randomInput = UUID.randomUUID().toString();
            log.info("[HASH UTIL] Generated UUID: {}", randomInput);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(randomInput.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                String h = Integer.toHexString(0xff & b);
                if (h.length() == 1) hex.append('0');
                hex.append(h);
            }

            String hashResult = hex.toString();
            log.info("[HASH UTIL] Generated SHA-256 hash: {}", hashResult);
            log.info("[HASH UTIL] === Finished hash generation ===");

            return hashResult;
        } catch (NoSuchAlgorithmException e) {
            log.error("[HASH UTIL] Error generating hash: {}", e.getMessage(), e);
            throw new RuntimeException("Error generating hash", e);
        }
    }
}
