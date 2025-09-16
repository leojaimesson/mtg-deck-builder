package com.mtg.deck_builder.cards.repository.specifications;

import com.mtg.deck_builder.cards.entitie.Card;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.Arrays;

public class CardSpecifications {

    /**
     * Builds a specification to search cards by both the full phrase and individual terms.
     *
     * @param query The search query string.
     * @return A Specification<Card> to use with JpaSpecificationExecutor.
     */
    public static Specification<Card> searchByTermsAndPhrase(String query) {
        return (root, cq, cb) -> {
            if (query == null || query.trim().isEmpty()) {
                return cb.conjunction(); // Return a "true" predicate if query is empty
            }

            String trimmedQuery = query.trim();
            String[] terms = trimmedQuery.split("\\s+");

            // Predicate for the full phrase match in name or description
            Predicate phraseMatch = cb.or(
                    cb.like(cb.lower(root.get("name")), "%" + trimmedQuery.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("description")), "%" + trimmedQuery.toLowerCase() + "%")
            );

            // Predicates for each individual term match in name or description
            Predicate[] termMatches = Arrays.stream(terms)
                    .map(term -> cb.or(
                            cb.like(cb.lower(root.get("name")), "%" + term.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("description")), "%" + term.toLowerCase() + "%")
                    ))
                    .toArray(Predicate[]::new);

            // Combine: match full phrase OR match all individual terms
            return cb.or(phraseMatch, cb.and(termMatches));
        };
    }
}
