package com.example.card_management.configuration.card.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CardEnvironment {
    private final long CARD_LIFETIME;
    private final int PAGINATION_SIZE;
    private final int TRANSACTION_LIMIT_MIN;
    private final int TRANSACTION_LIMIT_MAX;

    public CardEnvironment(@Value("${card.lifetime}") long CARD_LIFETIME, @Value("${card.pagination.size}") int PAGINATION_SIZE, @Value("${card.transaction.limit.min}") int TRANSACTION_LIMIT_MIN, @Value("${card.transaction.limit.max}")int TRANSACTION_LIMIT_MAX) {
        this.CARD_LIFETIME = CARD_LIFETIME;
        this.PAGINATION_SIZE = PAGINATION_SIZE;
        this.TRANSACTION_LIMIT_MIN = TRANSACTION_LIMIT_MIN;
        this.TRANSACTION_LIMIT_MAX = TRANSACTION_LIMIT_MAX;
    }

    public long getCARD_LIFETIME() {
        return CARD_LIFETIME;
    }

    public int getPAGINATION_SIZE() {
        return PAGINATION_SIZE;
    }

    public int getTRANSACTION_LIMIT_MIN() {
        return TRANSACTION_LIMIT_MIN;
    }

    public int getTRANSACTION_LIMIT_MAX() {
        return TRANSACTION_LIMIT_MAX;
    }
}
