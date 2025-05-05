package com.example.card_management.service.user.card;

import com.example.card_management.configuration.card.environment.CardEnvironment;
import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.exception.CardManagementException;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.repository.user.card.UserCardRepository;
import com.example.card_management.util.encoder.Encoder;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public abstract class UserCardService<C extends UserCard> {
    protected final UserCardRepository userCardRepository;
    protected final CardEnvironment cardEnvironment;
    protected final Encoder<String> encoder;

    public UserCardService(UserCardRepository userCardRepository, CardEnvironment cardEnvironment, Encoder<String> encoder) {
        this.userCardRepository = userCardRepository;
        this.cardEnvironment = cardEnvironment;
        this.encoder = encoder;
    }

    public abstract String save(C userCard);

    public abstract List<C> getAllByOwnerEmailAndCardType(String email, int pageNumber, UserCardTypeEnumeration cardType);

    public abstract boolean existsByCardNumberAndCardType(String cardNumber, UserCardTypeEnumeration cardType);

    public boolean existsByCardNumber(String cardNumber) {
        return userCardRepository.existsUserCardByNumber(encoder.encode(cardNumber));
    }

    public List<UserCard> getAllUserCards(int pageNumber) {
        return userCardRepository.findAllUserCard(PageRequest.of(pageNumber, cardEnvironment.getPAGINATION_SIZE()));
    }

    public UserCard getByNumber(String number) {
        return userCardRepository.findUserCardByNumber(encoder.encode(number)).orElseThrow(() -> new CardManagementException("card has been not found"));
    }

    public List<UserCard> getAllByOwnerEmail(String email, int pageNumber) {
        return userCardRepository.findAllByOwner_Email(email, PageRequest.of(pageNumber, cardEnvironment.getPAGINATION_SIZE()));
    }
}

