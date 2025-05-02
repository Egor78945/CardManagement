package com.example.card_management.enumeration.card;

import com.example.card_management.exception.CardManagementException;

import java.util.HashMap;
import java.util.Map;

public enum UserCardType {
    VISA(1,"4"), MASTERCARD(2, "5");
    private final long id;
    private final String prefix;
    private final static Map<Long, UserCardType> cardTypeById;

    UserCardType(long id, String prefix) {
        this.id = id;
        this.prefix = prefix;
    }

    static {
        cardTypeById = new HashMap<>();
        cardTypeById.put(VISA.id, VISA);
        cardTypeById.put(MASTERCARD.id, MASTERCARD);
    }

    public long getId() {
        return id;
    }

    public String getPrefix() {
        return prefix;
    }

    public static UserCardType getById(long id){
        UserCardType userCardType = cardTypeById.get(id);
        if(userCardType != null){
            return userCardType;
        }
        throw new CardManagementException(String.format("unknown card type by id '%s'", id));
    }

}
