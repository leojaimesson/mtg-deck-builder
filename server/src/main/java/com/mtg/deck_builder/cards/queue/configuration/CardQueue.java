package com.mtg.deck_builder.cards.queue.configuration;


import com.mtg.deck_builder.configuration.RabbitConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;


@Configuration
public class CardQueue {

    private final RabbitConfig rabbitConfig;

    public CardQueue(RabbitConfig rabbitConfig) {
        this.rabbitConfig = rabbitConfig;
    }

    @Bean
    public Queue defineQueue() {
        return rabbitConfig.createQueue("card-queue");
    }
}
