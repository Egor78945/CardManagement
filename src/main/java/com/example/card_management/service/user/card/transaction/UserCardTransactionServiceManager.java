package com.example.card_management.service.user.card.transaction;

import com.example.card_management.configuration.card.environment.CardEnvironment;
import com.example.card_management.enumeration.user.card.status.UserCardStatusTypeEnumeration;
import com.example.card_management.exception.TransactionManagementException;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.transaction.dto.TransactionInsertDTO;
import com.example.card_management.model.user.card.transaction.dto.TransactionTransferDTO;
import com.example.card_management.model.user.dto.security.UserCredentialDTO;
import com.example.card_management.service.security.authentication.AuthenticationService;
import com.example.card_management.service.user.card.UserCardService;
import com.example.card_management.service.user.card.router.UserCardServiceRouter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCardTransactionServiceManager implements UserCardTransactionService<UserCard> {
    private final AuthenticationService<UserCredentialDTO> authenticationService;
    private final UserCardServiceRouter<UserCard> cardServiceRouter;
    private final CardEnvironment cardEnvironment;

    public UserCardTransactionServiceManager(@Qualifier("authenticationServiceManager") AuthenticationService<UserCredentialDTO> authenticationService, @Qualifier("userCardServiceRouterManager") UserCardServiceRouter<UserCard> cardServiceRouter, CardEnvironment cardEnvironment) {
        this.authenticationService = authenticationService;
        this.cardServiceRouter = cardServiceRouter;
        this.cardEnvironment = cardEnvironment;
    }

    @Override
    @Transactional
    public double insert(TransactionInsertDTO transaction) {
        UserCardService<UserCard> cardService = cardServiceRouter.getByCardNumber(transaction.getCardNumberTo());
        UserCard targetCard = cardService.getByNumber(transaction.getCardNumberTo());
        if (targetCard.getOwnerEmail().equals(authenticationService.getCurrentUserEmail()) && targetCard.getStatus().getId() == UserCardStatusTypeEnumeration.STATUS_ACTIVE.getId() && canReceive(targetCard, transaction.getAmount())) {
            targetCard.setBalance(targetCard.getBalance() + transaction.getAmount());
            cardService.save(targetCard);
            return targetCard.getBalance();
        } else {
            throw new TransactionManagementException("card is expired or blocked");
        }
    }

    @Override
    @Transactional
    public double transfer(TransactionTransferDTO transaction) {
        UserCardService<UserCard> cardService = cardServiceRouter.getByCardNumber(transaction.getCardNumberFrom());
        UserCard fromCard = cardService.getByNumber(transaction.getCardNumberFrom());
        UserCard toCard = cardService.getByNumber(transaction.getCardNumberTo());
        if (fromCard.getOwnerEmail().equals(authenticationService.getCurrentUserEmail())
                && toCard.getId() != fromCard.getId()
                && canSend(fromCard, transaction.getAmount())
                && canReceive(toCard, transaction.getAmount())) {
            toCard.setBalance(toCard.getBalance() + transaction.getAmount());
            fromCard.setBalance(fromCard.getBalance() - transaction.getAmount());
            cardService.save(fromCard);
            cardService.save(toCard);
            return fromCard.getBalance();
        } else {
         throw new TransactionManagementException("card is expired or blocked")   ;
        }
    }

    private boolean canReceive(UserCard userCard, int amount) {
        return userCard.getStatus().getId() == UserCardStatusTypeEnumeration.STATUS_ACTIVE.getId() &&
                amountInLimit(amount);

    }

    private boolean canSend(UserCard userCard, int amount) {
        return userCard.getStatus().getId() == UserCardStatusTypeEnumeration.STATUS_ACTIVE.getId() &&
                userCard.getBalance() >= amount &&
                amountInLimit(amount);
    }

    private boolean amountInLimit(int amount) {
        return amount <= cardEnvironment.getTRANSACTION_LIMIT_MAX() && amount >= cardEnvironment.getTRANSACTION_LIMIT_MIN();
    }
}
