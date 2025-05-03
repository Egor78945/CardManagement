package com.example.card_management.service.user.card;

import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.exception.CardManagementException;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.credential.entity.UserCredential;
import com.example.card_management.repository.user.card.UserCardRepository;
import com.example.card_management.repository.user.card.request.UserCardRequestRepository;
import com.example.card_management.service.user.credential.UserCredentialService;
import com.example.card_management.util.encoder.Encoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisaCardServiceManager extends UserCardService<UserCard> {
    public VisaCardServiceManager(UserCardRepository userCardRepository, UserCardRequestRepository userCardRequestRepository, @Qualifier("userCredentialServiceManager") UserCredentialService<UserCredential> userCredentialService, @Qualifier("stringEncoder") Encoder<String> encoder) {
        super(userCardRepository, userCardRequestRepository, userCredentialService, encoder);
    }

    @Override
    public String save(UserCard userCard) {
        if (userCardRepository.findCountByOwnerEmailAndTypeId(userCard.getOwnerEmail(), UserCardTypeEnumeration.VISA.getId()) < 5) {
            userCardRepository.save(userCard);
            return encoder.decode(userCard.getNumber());
        } else {
            throw new CardManagementException("card with this number is already exists or count is out of limit");
        }
    }

    @Override
    public List<UserCard> getAllByOwnerEmailAndCardType(String email, int pageNumber, int pageSize) {
        return userCardRepository.findAllByOwner_EmailAndType_Id(email, UserCardTypeEnumeration.VISA.getId(), PageRequest.of(pageNumber, pageSize))
                .stream()
                .toList();
    }

    @Override
    public boolean existsByCardNumberAndCardType(String cardNumber) {
        return userCardRepository.existsUserCardByNumberAndType_Id(encoder.encode(cardNumber), UserCardTypeEnumeration.VISA.getId());
    }
}
