package com.example.card_management.service.user.card.transaction;

import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.transaction.dto.TransactionInsertDTO;
import com.example.card_management.model.user.card.transaction.dto.TransactionTransferDTO;

public interface UserCardTransactionService<C extends UserCard> {
    <T extends TransactionInsertDTO> double insert(T transaction, String inserterEmail);
    <T extends TransactionTransferDTO> double transfer(T transaction, String transmittingEmail);
}
