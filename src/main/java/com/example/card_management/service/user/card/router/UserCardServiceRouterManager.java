package com.example.card_management.service.user.card.router;

import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.exception.CardManagementException;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.repository.user.card.UserCardRepository;
import com.example.card_management.service.user.card.MastercardCardServiceManager;
import com.example.card_management.service.user.card.UserCardService;
import com.example.card_management.service.user.card.VisaCardServiceManager;
import com.example.card_management.util.encoder.Encoder;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "UserCardServiceRouterManager", description = "Базовая реализация маршрутизатора сервисов по работе с пользовательскими картами")
@Component
public class UserCardServiceRouterManager implements UserCardServiceRouter<UserCard> {
    @Parameter(description = "Карта, содержащая сервисы по работе с пользовательскими картами, доступные по типу пользовательской карты")
    private final Map<UserCardTypeEnumeration, UserCardService<UserCard>> cardServiceStrategy;
    private final UserCardRepository userCardRepository;
    private final Encoder<String> encoder;

    public UserCardServiceRouterManager(VisaCardServiceManager visaCardServiceManager, MastercardCardServiceManager mastercardCardServiceManager, UserCardRepository userCardRepository, Encoder<String> encoder) {
        this.userCardRepository = userCardRepository;
        this.encoder = encoder;
        this.cardServiceStrategy = new HashMap<>();
        cardServiceStrategy.put(UserCardTypeEnumeration.VISA, visaCardServiceManager);
        cardServiceStrategy.put(UserCardTypeEnumeration.MASTERCARD, mastercardCardServiceManager);
    }

    @Override
    public UserCardService<UserCard> getByCardType(UserCardTypeEnumeration userCardTypeEnumeration) {
        if (userCardTypeEnumeration != null) {
            UserCardService<UserCard> userCardService = cardServiceStrategy.get(userCardTypeEnumeration);
            if (userCardService != null) {
                return userCardService;
            }
            throw new CardManagementException("unknown card type");
        }
        throw new CardManagementException("card type is null");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserCardService<UserCard> getByCardNumber(String cardNumber) {
        return cardServiceStrategy
                .get(UserCardTypeEnumeration.getById(userCardRepository.findUserCardByNumber(encoder.encode(cardNumber))
                .orElseThrow(() -> new CardManagementException("card by number is not found"))
                .getId()));

    }
}
