package com.example.card_management.service.user.card.util;

import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.util.encoder.Encoder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Tag(name = "UserCardMapper", description = "Сервис по преобразованию или изменению пользовательских карт")
@Component
public class UserCardUtility {
    private final Encoder<String> encoder;

    public UserCardUtility(@Qualifier("stringEncoder") Encoder<String> encoder) {
        this.encoder = encoder;
    }

    @Operation(description = "Замаскировать номер пользовательской карты")
    public UserCard maskCardNumber(UserCard userCard) {
        userCard.setNumber(String.format("**** **** **** %s", encoder.decode(userCard.getNumber()).substring(12)));
        return userCard;
    }

    public List<UserCard> maskCardNumber(List<UserCard> userCard) {
        return userCard.stream().map(this::maskCardNumber).toList();
    }

    @Operation(description = "Раскодировать номер пользовательской карты")
    public UserCard showCardNumber(UserCard userCard) {
        userCard.setNumber(encoder.decode(userCard.getNumber()));
        return userCard;
    }

    public List<UserCard> showCardNumber(List<UserCard> userCard) {
        return userCard.stream().map(this::showCardNumber).toList();
    }
}
