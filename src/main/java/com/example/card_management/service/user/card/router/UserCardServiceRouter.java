package com.example.card_management.service.user.card.router;

import com.example.card_management.enumeration.card.UserCardType;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.service.user.card.UserCardService;

public interface UserCardServiceRouter <C extends UserCard> {
    UserCardService<C> getByCardType(UserCardType userCardType);
}
