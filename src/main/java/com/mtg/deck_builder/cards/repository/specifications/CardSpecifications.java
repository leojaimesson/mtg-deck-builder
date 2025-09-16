package com.mtg.deck_builder.cards.repository.specifications;

import com.mtg.deck_builder.cards.entitie.Card;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;

public class CardSpecifications {

    private static final Logger log = LoggerFactory.getLogger(CardSpecifications.class);

    /**
     * Builds a specification to search cards by both the full phrase and individual terms.
     *
     * @param query The search query string.
     * @return A Specification<Card> to use with JpaSpecificationExecutor.
     */
    public static Specification<Card> searchByTermsAndPhrase(String query) {
        return (root, cq, cb) -> {
            log.info("[CARD SPECIFICATION] === Starting Specification for search query ===");

            if (query == null || query.trim().isEmpty()) {
                log.info("[CARD SPECIFICATION] Search query is empty. Returning all records.");
                return cb.conjunction();
            }

            String trimmedQuery = query.trim();
            String[] terms = trimmedQuery.split("\\s+");

            log.info("[CARD SPECIFICATION] Original query: '{}'", query);
            log.info("[CARD SPECIFICATION] Trimmed query: '{}'", trimmedQuery);
            log.info("[CARD SPECIFICATION] Number of terms extracted: {}", terms.length);
            log.info("[CARD SPECIFICATION] Terms: {}", Arrays.toString(terms));

            // Predicate for the full phrase match in name or description
            Predicate phraseMatch = cb.or(
                    cb.like(cb.lower(root.get("name")), "%" + trimmedQuery.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("description")), "%" + trimmedQuery.toLowerCase() + "%")
            );
            log.info("[CARD SPECIFICATION] Full phrase predicate created for: '{}'", trimmedQuery);

            // Predicates for each individual term match in name or description
            Predicate[] termMatches = Arrays.stream(terms)
                    .map(term -> {
                        Predicate termPredicate = cb.or(
                                cb.like(cb.lower(root.get("name")), "%" + term.toLowerCase() + "%"),
                                cb.like(cb.lower(root.get("description")), "%" + term.toLowerCase() + "%")
                        );
                        log.info("[CARD SPECIFICATION] Individual term predicate created for: '{}'", term);
                        return termPredicate;
                    })
                    .toArray(Predicate[]::new);

            // Combine: match full phrase OR match all individual terms
            Predicate combined = cb.or(phraseMatch, cb.and(termMatches));
            log.info("[CARD SPECIFICATION] Combined predicate created (full phrase OR all individual terms).");

            log.info("[CARD SPECIFICATION] === End of Specification Creation ===");

            return combined;
        };
    }
}
