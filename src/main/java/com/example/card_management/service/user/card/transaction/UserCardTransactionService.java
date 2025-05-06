package com.example.card_management.service.user.card.transaction;

import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.transaction.dto.TransactionInsertDTO;
import com.example.card_management.model.user.card.transaction.dto.TransactionTransferDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserCardTransactionService", description = "Сервис, предоставляющий основной функционал для работы с транзакциями между пользовательскими картами")
public interface UserCardTransactionService<C extends UserCard> {
    @Operation(description = "Внести деньги на пользовательскую карту")
    <T extends TransactionInsertDTO> double insert(T transaction);
    @Operation(description = "Перевести деньги с одной пользовательской карты на другую")
    <T extends TransactionTransferDTO> double transfer(T transaction);
}
