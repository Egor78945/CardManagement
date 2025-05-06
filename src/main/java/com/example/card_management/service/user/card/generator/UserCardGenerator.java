package com.example.card_management.service.user.card.generator;

import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.model.user.card.entity.UserCard;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserCardGenerator", description = "Сервис для генерации пользовательских карт")
public interface UserCardGenerator<C extends UserCard> {
    @Operation(description = "Сгенерировать пользовательскую карту из входных данных")
    C buildFrom(String userEmail, UserCardTypeEnumeration userCardTypeEnumeration);
}
