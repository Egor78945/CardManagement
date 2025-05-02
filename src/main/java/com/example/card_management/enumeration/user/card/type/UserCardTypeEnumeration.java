package com.example.card_management.enumeration.user.card.type;

import com.example.card_management.exception.CardManagementException;

import java.util.HashMap;
import java.util.Map;

public enum UserCardTypeEnumeration {
    VISA(1,"4"), MASTERCARD(2, "5");
    private final long id;
    private final String prefix;
    private final static Map<Long, UserCardTypeEnumeration> cardTypeById;

    UserCardTypeEnumeration(long id, String prefix) {
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

    public static UserCardTypeEnumeration getById(long id){
        UserCardTypeEnumeration userCardTypeEnumeration = cardTypeById.get(id);
        if(userCardTypeEnumeration != null){
            return userCardTypeEnumeration;
        }
        throw new CardManagementException(String.format("unknown card type by id '%s'", id));
    }

}
