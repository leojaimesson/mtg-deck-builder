package com.mtg.deck_builder.cards.persistence.repository;

import com.mtg.deck_builder.cards.persistence.entitie.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<CardEntity, String> {
    List<CardEntity> findByScryfallIdIn(List<String> scryfallIds);

    @Query("""
            SELECT c FROM CardEntity c
            WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))
               OR LOWER(c.description) LIKE LOWER(CONCAT('%', :query, '%'))
            """)
    List<CardEntity> searchByNameOrDescription(@Param("query") String query);
}
