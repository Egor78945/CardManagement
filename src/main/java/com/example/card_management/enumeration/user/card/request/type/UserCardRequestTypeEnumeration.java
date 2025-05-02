package com.example.card_management.enumeration.user.card.request.type;

import com.example.card_management.exception.CardManagementException;

import java.util.HashMap;
import java.util.Map;

public enum UserCardRequestTypeEnumeration {
    TYPE_BLOCK(1, "TYPE_BLOCK");
    private final long id;
    private final String type;

    private final static Map<Long, UserCardRequestTypeEnumeration> userCardRequestTypeById;

    UserCardRequestTypeEnumeration(long id, String type) {
        this.id = id;
        this.type = type;
    }

    static {
        userCardRequestTypeById = new HashMap<>();
        userCardRequestTypeById.put(TYPE_BLOCK.id, TYPE_BLOCK);
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public static UserCardRequestTypeEnumeration getById(long id){
        UserCardRequestTypeEnumeration cardRequestType = userCardRequestTypeById.get(id);
        if(cardRequestType != null){
            return cardRequestType;
        }
        throw new CardManagementException("unknown card request type");
    }
}
