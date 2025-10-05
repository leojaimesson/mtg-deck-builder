package com.mtg.deck_builder.cards.queue.consumer;

import com.mtg.deck_builder.cards.entitie.Card;
import com.mtg.deck_builder.cards.service.protocol.SaveCardsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardConsumerService {
    private final SaveCardsService saveCardsService;

    public  CardConsumerService(SaveCardsService saveCardsService) {
        this.saveCardsService = saveCardsService;
    }
    @RabbitListener(queues = "card-queue")
    public void receiveMessage(List<Card> message) {
        this.saveCardsService.exec(message);
    }
}

