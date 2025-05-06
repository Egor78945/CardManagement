package com.example.card_management.service.user.card;

import com.example.card_management.configuration.card.environment.CardEnvironment;
import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.exception.CardManagementException;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.repository.user.card.UserCardRepository;
import com.example.card_management.util.encoder.Encoder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Tag(name = "UserCardService", description = "Сервис, предоставляющий основной фунцкионал для работы с пользовательскими картами")
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

    @Operation(description = "Получить постраничный список карт пользователя по его email и типу карты")
    public abstract List<C> getAllByOwnerEmailAndCardType(String email, int pageNumber, UserCardTypeEnumeration cardType);

    @Operation(description = "Проверить, существует ли пользовательская карта по номеру и типу карты")
    public abstract boolean existsByCardNumberAndCardType(String cardNumber, UserCardTypeEnumeration cardType);

    @Operation(description = "Проверить, существует ли пользовательская карта по номеру карты")
    public boolean existsByCardNumber(String cardNumber) {
        return userCardRepository.existsUserCardByNumber(encoder.encode(cardNumber));
    }

    @Operation(description = "Получить постраничный список всех пользовательских карт")
    public List<UserCard> getAllUserCards(int pageNumber) {
        return userCardRepository.findAllUserCard(PageRequest.of(pageNumber, cardEnvironment.getPAGINATION_SIZE()));
    }

    @Operation(description = "Получить пользовательскую карту по номеру")
    public UserCard getByNumber(String number) {
        return userCardRepository.findUserCardByNumber(encoder.encode(number)).orElseThrow(() -> new CardManagementException("card has been not found"));
    }

    @Operation(description = "Получить постраничный список пользовательских карт по email владельца")
    public List<UserCard> getAllByOwnerEmail(String email, int pageNumber) {
        return userCardRepository.findAllByOwner_Email(email, PageRequest.of(pageNumber, cardEnvironment.getPAGINATION_SIZE()));
    }
}

