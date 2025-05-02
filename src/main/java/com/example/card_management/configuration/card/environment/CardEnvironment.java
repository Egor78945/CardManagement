package com.example.card_management.configuration.card.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CardEnvironment {
    private final long CARD_LIFETIME;
    private final int PAGINATION_SIZE;

    public CardEnvironment(@Value("${card.lifetime}") long CARD_LIFETIME, @Value("${card.pagination.size}") int PAGINATION_SIZE) {
        this.CARD_LIFETIME = CARD_LIFETIME;
        this.PAGINATION_SIZE = PAGINATION_SIZE;
    }

    public long getCARD_LIFETIME() {
        return CARD_LIFETIME;
    }

    public int getPAGINATION_SIZE() {
        return PAGINATION_SIZE;
    }
}
