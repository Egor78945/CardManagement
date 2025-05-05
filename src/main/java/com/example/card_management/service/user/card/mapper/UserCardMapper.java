package com.example.card_management.service.user.card.mapper;

import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.util.encoder.Encoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCardMapper {
    private final Encoder<String> encoder;

    public UserCardMapper(@Qualifier("stringEncoder") Encoder<String> encoder) {
        this.encoder = encoder;
    }

    public final UserCard maskCardNumber(UserCard userCard) {
        userCard.setNumber(String.format("**** **** **** %s", encoder.decode(userCard.getNumber()).substring(12)));
        return userCard;
    }

    public final List<UserCard> maskCardNumber(List<UserCard> userCard) {
        return userCard.stream().map(this::maskCardNumber).toList();
    }

    public final UserCard showCardNumber(UserCard userCard) {
        userCard.setNumber(encoder.decode(userCard.getNumber()));
        return userCard;
    }

    public final List<UserCard> showCardNumber(List<UserCard> userCard) {
        return userCard.stream().map(this::showCardNumber).toList();
    }
}
