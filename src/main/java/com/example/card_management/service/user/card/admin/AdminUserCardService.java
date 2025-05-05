package com.example.card_management.service.user.card.admin;

import com.example.card_management.configuration.card.environment.CardEnvironment;
import com.example.card_management.enumeration.user.card.status.UserCardStatusTypeEnumeration;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.status.entity.UserCardStatus;
import com.example.card_management.repository.user.card.UserCardRepository;
import com.example.card_management.service.user.card.UserCardService;
import com.example.card_management.util.encoder.Encoder;

import java.util.Date;

public abstract class AdminUserCardService<C extends UserCard> extends UserCardService<C> {
    public AdminUserCardService(UserCardRepository userCardRepository, CardEnvironment cardEnvironment, Encoder<String> encoder) {
        super(userCardRepository, cardEnvironment, encoder);
    }

    public abstract void changeUserCardStatusByNumber(String phoneNumber, UserCardStatus userCardStatus);

    public abstract void deleteCardByPhoneNumber(String phoneNumber);

    public void updateExpiredUserCards() {
        userCardRepository.updateExpiredUserCardStatus(new Date(), new UserCardStatus(UserCardStatusTypeEnumeration.STATUS_EXPIRED.getId(), UserCardStatusTypeEnumeration.STATUS_EXPIRED.name()));
    }
}
