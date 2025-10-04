package com.mtg.deck_builder.cards.repository.jpa;

import com.mtg.deck_builder.cards.entitie.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, String>, JpaSpecificationExecutor<Card> {
    List<Card> findByScryfallIdIn(List<String> scryfallIds);
}
