package com.example.card_management.service.user.card.admin;

import com.example.card_management.configuration.card.environment.CardEnvironment;
import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.status.entity.UserCardStatus;
import com.example.card_management.repository.user.card.UserCardRepository;
import com.example.card_management.service.user.card.router.UserCardServiceRouter;
import com.example.card_management.util.encoder.Encoder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Tag(name = "AdminUserCardServiceManager", description = "Базовая реализация сервиса по работе с пользовательскими картами для администрированных пользователей")
@Service
public class AdminUserCardServiceManager extends AdminUserCardService<UserCard> {
    private final UserCardServiceRouter<UserCard> userCardServiceRouter;

    public AdminUserCardServiceManager(@Qualifier("userCardServiceRouterManager") UserCardServiceRouter<UserCard> userCardServiceRouter, UserCardRepository userCardRepository, CardEnvironment cardEnvironment, @Qualifier("stringEncoder") Encoder<String> encoder) {
        super(userCardRepository, cardEnvironment, encoder);
        this.userCardServiceRouter = userCardServiceRouter;
    }

    @Override
    public final void changeUserCardStatusByNumber(String cardNumber, UserCardStatus userCardStatus) {
        userCardRepository.updateUserCardStatusByCardNumber(encoder.encode(cardNumber), userCardStatus);
    }

    @Override
    public final void deleteCardByNumber(String phoneNumber) {
        userCardRepository.deleteUserCardByNumber(encoder.encode(phoneNumber));
    }

    @Override
    public String save(UserCard userCard) {
        return userCardServiceRouter.getByCardNumber(encoder.decode(userCard.getNumber())).save(userCard);
    }

    @Override
    public List<UserCard> getAllByOwnerEmailAndCardType(String email, int pageNumber, UserCardTypeEnumeration userCardTypeEnumeration) {
        return userCardRepository.findAllByOwner_EmailAndType_Id(email, userCardTypeEnumeration.getId(), PageRequest.of(pageNumber, cardEnvironment.getPAGINATION_SIZE()))
                .stream()
                .toList();
    }

    @Override
    public boolean existsByCardNumberAndCardType(String cardNumber, UserCardTypeEnumeration userCardTypeEnumeration) {
        return userCardRepository.existsUserCardByNumberAndType_Id(encoder.encode(cardNumber), userCardTypeEnumeration.getId());
    }
}
