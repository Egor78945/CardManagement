package com.example.card_management.service.user.card.generator;

import com.example.card_management.configuration.card.environment.CardEnvironment;
import com.example.card_management.enumeration.card.UserCardStatusType;
import com.example.card_management.enumeration.card.UserCardType;
import com.example.card_management.exception.CardManagementException;
import com.example.card_management.model.card.status.entity.CardStatus;
import com.example.card_management.model.card.type.entity.CardType;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.service.user.card.router.UserCardServiceRouter;
import com.example.card_management.util.encoder.Encoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class UserCardGeneratorManager implements UserCardGenerator<UserCard> {
    private final UserCardServiceRouter<UserCard> userCardUserCardServiceRouter;
    private final CardEnvironment cardEnvironment;
    private final Encoder<String> encoder;
    private final Random random;

    public UserCardGeneratorManager(@Qualifier("userCardServiceRouterManager") UserCardServiceRouter<UserCard> userCardUserCardServiceRouter, @Qualifier("stringEncoder") Encoder<String> encoder, CardEnvironment cardEnvironment) {
        this.random = new Random();
        this.userCardUserCardServiceRouter = userCardUserCardServiceRouter;
        this.cardEnvironment = cardEnvironment;
        this.encoder = encoder;
    }

    @Override
    public UserCard buildFrom(String userEmail, UserCardType userCardType) {
        String cardNumber = generateCardNumber(userCardType.getPrefix());
        int limit = 0;
        while (userCardUserCardServiceRouter.getByCardType(userCardType).existsByCardNumber(cardNumber) && limit < 10){
            cardNumber = generateCardNumber(userCardType.getPrefix());
            limit++;
        }
        if(limit >= 10){
            throw new CardManagementException("card number has not been generated");
        }
        return new UserCard(userEmail, encoder.encode(cardNumber), new Date(new Date().getTime() + cardEnvironment.getCARD_LIFETIME()), new CardStatus(UserCardStatusType.STATUS_ACTIVE.getId(), UserCardStatusType.STATUS_ACTIVE.name()), new CardType(userCardType.getId(), userCardType.name()), 0.0);
    }

    private String generateCardNumber(String prefix) {
        StringBuilder cardNumber = new StringBuilder(prefix);

        while (cardNumber.length() < 15) {
            cardNumber.append(random.nextInt(10));
        }

        int checkDigit = calculateLuhnCheckDigit(cardNumber.toString());
        cardNumber.append(checkDigit);

        return cardNumber.toString();
    }

    private int calculateLuhnCheckDigit(String number) {
        int sum = 0;
        boolean alternate = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit % 10) + 1;
                }
            }
            sum += digit;
            alternate = !alternate;
        }
        return (10 - (sum % 10)) % 10;
    }
}
