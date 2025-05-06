package com.example.card_management.service.user.card;

import com.example.card_management.configuration.card.environment.CardEnvironment;
import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.exception.CardManagementException;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.repository.user.card.UserCardRepository;
import com.example.card_management.util.encoder.Encoder;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Tag(name = "MastercardCardServiceManager", description = "Реализация сервиса по работе с пользовательскими картами системы Mastercard")
@Service
public class MastercardCardServiceManager extends UserCardService<UserCard> {
    public MastercardCardServiceManager(UserCardRepository userCardRepository, CardEnvironment cardEnvironment, @Qualifier("stringEncoder") Encoder<String> encoder) {
        super(userCardRepository, cardEnvironment, encoder);
    }

    @Override
    @Transactional
    public String save(UserCard userCard) {
        if (userCardRepository.findCountByOwnerEmailAndTypeId(userCard.getOwnerEmail(), UserCardTypeEnumeration.MASTERCARD.getId()) < 5) {
            userCardRepository.save(userCard);
            return encoder.decode(userCard.getNumber());
        } else {
            throw new CardManagementException("card with this number is already exists or count is out of limit");
        }
    }

    @Override
    public List<UserCard> getAllByOwnerEmailAndCardType(String email, int pageNumber, UserCardTypeEnumeration userCardTypeEnumeration) {
        return userCardRepository.findAllByOwner_EmailAndType_Id(email, UserCardTypeEnumeration.MASTERCARD.getId(), PageRequest.of(pageNumber, cardEnvironment.getPAGINATION_SIZE()))
                .stream()
                .toList();
    }

    @Override
    public boolean existsByCardNumberAndCardType(String cardNumber, UserCardTypeEnumeration userCardTypeEnumeration) {
        return userCardRepository.existsUserCardByNumberAndType_Id(encoder.encode(cardNumber), UserCardTypeEnumeration.MASTERCARD.getId());
    }
}
