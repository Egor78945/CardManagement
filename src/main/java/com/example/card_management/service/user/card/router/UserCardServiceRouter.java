package com.example.card_management.service.user.card.router;

import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.service.user.card.UserCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserCardServiceRouter", description = "Сервис, предоставляющий основной функционал для маршрутизации сервисов по работе с пользовательскими картами")
public interface UserCardServiceRouter <C extends UserCard> {
    @Operation(description = "Получить сервис по работе с пользовательскими картами по типу пользовательской карты")
    UserCardService<C> getByCardType(UserCardTypeEnumeration userCardTypeEnumeration);
    @Operation(description = "Получить сервис по работе с пользовательскими картами по номеру карты")
    UserCardService<C> getByCardNumber(String cardNumber);
}
