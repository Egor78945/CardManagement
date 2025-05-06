package com.example.card_management.service.user.card.request.mapper;

import com.example.card_management.enumeration.user.card.request.type.UserCardRequestTypeEnumeration;
import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.exception.CardManagementException;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.request.dto.UserCardRequestDTO;
import com.example.card_management.model.user.card.request.entity.UserCardRequest;
import com.example.card_management.model.user.card.request.type.entity.UserCardRequestType;
import com.example.card_management.model.user.credential.entity.UserCredential;
import com.example.card_management.repository.user.card.request.UserCardRequestRepository;
import com.example.card_management.service.user.card.router.UserCardServiceRouter;
import com.example.card_management.service.user.credential.UserCredentialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Tag(name = "UserCardRequestMapper", description = "Сервис по изменению или преобразованию пользовательских запросов по картам")
@Component
public class UserCardRequestMapper {
    private final UserCardServiceRouter<UserCard> userCardServiceRouter;
    private final UserCardRequestRepository userCardRequestRepository;
    private final UserCredentialService<UserCredential> userCredentialService;

    public UserCardRequestMapper(@Qualifier("userCardServiceRouterManager") UserCardServiceRouter<UserCard> userCardServiceRouter, @Qualifier("userCredentialServiceManager") UserCredentialService<UserCredential> userCredentialService, UserCardRequestRepository userCardRequestRepository) {
        this.userCardRequestRepository = userCardRequestRepository;
        this.userCardServiceRouter = userCardServiceRouter;
        this.userCredentialService = userCredentialService;
    }

    @Operation(description = "Создать объект пользовательского запроса по карте")
    public UserCardRequest mapTo(String senderEmail, UserCardRequestDTO userCardRequestDTO) {
        UserCard userCard = userCardServiceRouter.getByCardType(UserCardTypeEnumeration.VISA).getByNumber(userCardRequestDTO.getCardNumber());
        UserCardRequestTypeEnumeration cardRequestTypeEnumeration = UserCardRequestTypeEnumeration.getById(userCardRequestDTO.getRequestId());
        if (!senderEmail.equals(userCard.getOwnerEmail())) {
            throw new CardManagementException("card belongs to another user");
        }
        if (userCardRequestRepository.existsUserCardRequestByCard_Number(userCard.getNumber())) {
            throw new CardManagementException("card request by this card already exists");
        }

        return new UserCardRequest(userCredentialService.getByEmail(userCard.getOwnerEmail()), new UserCardRequestType(cardRequestTypeEnumeration.getId(), cardRequestTypeEnumeration.getType()), userCard, userCardRequestDTO.getMessage());
    }
}
