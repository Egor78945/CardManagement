package com.example.card_management.service.user.card;

import com.example.card_management.exception.CardManagementException;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.credential.entity.UserCredential;
import com.example.card_management.repository.user.card.UserCardRepository;
import com.example.card_management.repository.user.card.request.UserCardRequestRepository;
import com.example.card_management.service.user.credential.UserCredentialService;
import com.example.card_management.util.encoder.Encoder;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public abstract class UserCardService<C extends UserCard> {
    protected final UserCardRepository userCardRepository;
    protected final UserCardRequestRepository userCardRequestRepository;
    protected final UserCredentialService<UserCredential> userCredentialService;
    protected final Encoder<String> encoder;

    public UserCardService(UserCardRepository userCardRepository, UserCardRequestRepository userCardRequestRepository, UserCredentialService<UserCredential> userCredentialService, Encoder<String> encoder) {
        this.userCardRepository = userCardRepository;
        this.userCardRequestRepository = userCardRequestRepository;
        this.userCredentialService = userCredentialService;
        this.encoder = encoder;
    }

    public abstract String save(C userCard);

    public abstract List<C> getAllByOwnerEmailAndCardType(String email, int pageNumber, int pageSize);

    public abstract boolean existsByCardNumberAndCardType(String cardNumber);

    public final boolean existsByCardNumber(String cardNumber) {
        return userCardRepository.existsUserCardByNumber(encoder.encode(cardNumber));
    }

    public final UserCard getByNumber(String number) {
        return userCardRepository.findUserCardByNumber(encoder.encode(number)).orElseThrow(() -> new CardManagementException("card has been not found"));
    }

    public final List<UserCard> getAllByOwnerEmail(String email, int pageNumber, int pageSize) {
        return userCardRepository.findAllByOwner_Email(email, PageRequest.of(pageNumber, pageSize));
    }

    public final List<UserCard> getAllByOwnerEmailWithoutMasking(String email, int pageNumber, int pageSize) {
        return userCardRepository.findAllByOwner_Email(email, PageRequest.of(pageNumber, pageSize));
    }

    public final UserCard maskCardNumber(UserCard userCard) {
        userCard.setNumber(String.format("**** **** **** %s", encoder.decode(userCard.getNumber()).substring(12)));
        return userCard;
    }

    public final List<UserCard> maskCardNumber(List<UserCard> userCard) {
        return userCard.stream().map(this::maskCardNumber).toList();
    }

    public final UserCard showCardNumber(UserCard userCard) {
        userCard.setNumber(encoder.decode(userCard.getNumber()));
        return userCard;
    }

    public final List<UserCard> showCardNumber(List<UserCard> userCard) {
        return userCard.stream().map(this::showCardNumber).toList();
    }
}

