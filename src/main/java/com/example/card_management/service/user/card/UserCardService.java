package com.example.card_management.service.user.card;

import com.example.card_management.enumeration.user.card.request.type.UserCardRequestTypeEnumeration;
import com.example.card_management.exception.CardManagementException;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.request.dto.UserCardRequestDTO;
import com.example.card_management.model.user.card.request.entity.UserCardRequest;
import com.example.card_management.model.user.card.request.type.entity.UserCardRequestType;
import com.example.card_management.model.user.credential.entity.UserCredential;
import com.example.card_management.repository.user.card.UserCardRepository;
import com.example.card_management.repository.user.card.request.UserCardRequestRepository;
import com.example.card_management.service.security.validation.validator.PasswordValidator;
import com.example.card_management.service.user.credential.UserCredentialService;
import com.example.card_management.util.encoder.Encoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class UserCardService<C extends UserCard> {
    protected final UserCardRepository userCardRepository;
    protected final UserCardRequestRepository userCardRequestRepository;
    protected final UserCredentialService<UserCredential> userCredentialService;
    private final Encoder<String> encoder;

    public UserCardService(UserCardRepository userCardRepository, UserCardRequestRepository userCardRequestRepository, @Qualifier("userCredentialServiceManager") UserCredentialService<UserCredential> userCredentialService, Encoder<String> encoder) {
        this.userCardRepository = userCardRepository;
        this.userCardRequestRepository = userCardRequestRepository;
        this.userCredentialService = userCredentialService;
        this.encoder = encoder;
    }

    public abstract void save(C userCard);

    public abstract List<C> getAllByOwnerEmailAndCardType(String email, int pageNumber, int pageSize);

    public abstract boolean existsByCardNumber(String cardNumber);

    protected UserCard getByNumber(String number) {
        return userCardRepository.findUserCardByNumber(encoder.encode(number)).orElseThrow(() -> new CardManagementException("card has been not found"));
    }

    public void sendCardRequest(String senderEmail, UserCardRequestDTO userCardRequestDTO) {
        UserCard userCard = getByNumber(userCardRequestDTO.getCardNumber());
        UserCardRequestTypeEnumeration cardRequestTypeEnumeration = UserCardRequestTypeEnumeration.getById(userCardRequestDTO.getRequestId());
        if (userCardRequestRepository.existsUserCardRequestByCard_Number(userCard.getNumber())) {
            throw new CardManagementException("card request by this card already exists");
        }
        if (!senderEmail.equals(userCard.getOwnerEmail())) {
            throw new CardManagementException("card belongs to another user");
        }
        UserCardRequest userCardRequest = new UserCardRequest(userCredentialService.getByEmail(userCard.getOwnerEmail()), new UserCardRequestType(cardRequestTypeEnumeration.getId(), cardRequestTypeEnumeration.getType()), userCard, userCardRequestDTO.getMessage());
        userCardRequestRepository.save(userCardRequest);
    }

    public final List<UserCard> getAllByOwnerEmail(String email, int pageNumber, int pageSize) {
        return userCardRepository.findAllByOwner_Email(email, PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(this::maskCardNumber)
                .toList();
    }

    protected UserCard maskCardNumber(UserCard userCard) {
        userCard.setNumber(String.format("**** **** **** %s", encoder.decode(userCard.getNumber()).substring(12)));
        return userCard;
    }
}

