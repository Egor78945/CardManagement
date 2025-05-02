package com.example.card_management.service.user.card;

import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.repository.user.card.UserCardRepository;
import com.example.card_management.service.security.validation.validator.PasswordValidator;
import com.example.card_management.util.encoder.Encoder;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class UserCardService<C extends UserCard> {
    protected final UserCardRepository userCardRepository;
    private final Encoder<String> encoder;

    public UserCardService(UserCardRepository userCardRepository, Encoder<String> encoder) {
        this.userCardRepository = userCardRepository;
        this.encoder = encoder;
    }

    public abstract void save(C userCard);

    public final List<UserCard> getAllByOwnerEmail(String email, int pageNumber, int pageSize) {
        return userCardRepository.findAllByOwner_Email(email, PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(this::maskCardNumber)
                .toList();
    }

    public abstract List<C> getAllByOwnerEmailAndCardType(String email, int pageNumber, int pageSize);

    public abstract boolean existsByCardNumber(String cardNumber);

    protected UserCard maskCardNumber(UserCard userCard) {
        userCard.setNumber(String.format("**** **** **** %s", encoder.decode(userCard.getNumber()).substring(12)));
        return userCard;
    }
}

