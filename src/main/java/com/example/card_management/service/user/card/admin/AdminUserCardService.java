package com.example.card_management.service.user.card.admin;

import com.example.card_management.configuration.card.environment.CardEnvironment;
import com.example.card_management.enumeration.user.card.status.UserCardStatusTypeEnumeration;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.status.entity.UserCardStatus;
import com.example.card_management.repository.user.card.UserCardRepository;
import com.example.card_management.service.user.card.UserCardService;
import com.example.card_management.util.encoder.Encoder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Date;

@Tag(name = "AdminUserCardService", description = "Сервис, предоставляющий основной функционал по работе с пользовательскими картами для администрированных пользователей")
public abstract class AdminUserCardService<C extends UserCard> extends UserCardService<C> {
    public AdminUserCardService(UserCardRepository userCardRepository, CardEnvironment cardEnvironment, Encoder<String> encoder) {
        super(userCardRepository, cardEnvironment, encoder);
    }

    @Operation(description = "Сменить статус пользовательской карт по номеру карты")
    public abstract void changeUserCardStatusByNumber(String cardNumber, UserCardStatus userCardStatus);

    public abstract void deleteCardByNumber(String phoneNumber);

    @Operation(description = "Сменить статус пользовательских карт, у которых истёк срок действия")
    public void updateExpiredUserCards() {
        userCardRepository.updateExpiredUserCardStatus(new Date(), new UserCardStatus(UserCardStatusTypeEnumeration.STATUS_EXPIRED.getId(), UserCardStatusTypeEnumeration.STATUS_EXPIRED.name()));
    }
}
