package com.example.card_management.service.user.card.router;

import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.exception.CardManagementException;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.service.user.card.MastercardCardServiceManager;
import com.example.card_management.service.user.card.UserCardService;
import com.example.card_management.service.user.card.VisaCardServiceManager;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserCardServiceRouterManager implements UserCardServiceRouter<UserCard> {
    private final Map<UserCardTypeEnumeration, UserCardService<UserCard>> cardServiceStrategy;

    public UserCardServiceRouterManager(VisaCardServiceManager visaCardServiceManager, MastercardCardServiceManager mastercardCardServiceManager) {
        this.cardServiceStrategy = new HashMap<>();
        cardServiceStrategy.put(UserCardTypeEnumeration.VISA, visaCardServiceManager);
        cardServiceStrategy.put(UserCardTypeEnumeration.MASTERCARD, mastercardCardServiceManager);
    }

    @Override
    public UserCardService<UserCard> getByCardType(UserCardTypeEnumeration userCardTypeEnumeration) {
        if(userCardTypeEnumeration != null) {
            UserCardService<UserCard> userCardService = cardServiceStrategy.get(userCardTypeEnumeration);
            if(userCardService != null){
                return userCardService;
            }
            throw new CardManagementException("unknown card type");
        }
        throw new CardManagementException("card type is null");
    }
}
