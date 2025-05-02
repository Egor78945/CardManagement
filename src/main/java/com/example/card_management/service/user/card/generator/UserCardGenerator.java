package com.example.card_management.service.user.card.generator;

import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.model.user.card.entity.UserCard;

public interface UserCardGenerator<C extends UserCard> {
    C buildFrom(String userEmail, UserCardTypeEnumeration userCardTypeEnumeration);
}
