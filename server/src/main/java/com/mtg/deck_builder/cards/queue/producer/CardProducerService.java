package com.mtg.deck_builder.cards.queue.producer;

import com.mtg.deck_builder.cards.entitie.Card;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardProducerService {

    private final RabbitTemplate rabbitTemplate;

    public CardProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCardMessage(List<Card> message) {
        rabbitTemplate.convertAndSend("card-queue", message);
    }
}
