package com.example.card_management.controller.card;

import com.example.card_management.controller.advice.handler.CommonControllerExceptionHandler;
import com.example.card_management.controller.card.advice.handler.CardControllerExceptionHandler;
import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.dto.security.UserCredentialDTO;
import com.example.card_management.service.security.authentication.AuthenticationService;
import com.example.card_management.service.user.card.generator.UserCardGenerator;
import com.example.card_management.service.user.card.mapper.UserCardMapper;
import com.example.card_management.service.user.card.router.UserCardServiceRouter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/card")
@CommonControllerExceptionHandler
@CardControllerExceptionHandler
public class CardController {
    private final AuthenticationService<UserCredentialDTO> authenticationService;
    private final UserCardServiceRouter<UserCard> cardServiceRouter;
    private final UserCardGenerator<UserCard> cardGenerator;
    private final UserCardMapper userCardMapper;

    public CardController(@Qualifier("authenticationServiceManager") AuthenticationService<UserCredentialDTO> authenticationService, @Qualifier("userCardServiceRouterManager") UserCardServiceRouter<UserCard> cardServiceRouter, @Qualifier("userCardGeneratorManager") UserCardGenerator<UserCard> cardGenerator, UserCardMapper userCardMapper) {
        this.authenticationService = authenticationService;
        this.cardServiceRouter = cardServiceRouter;
        this.cardGenerator = cardGenerator;
        this.userCardMapper = userCardMapper;
    }

    @PostMapping("/add/{typeId}")
    public ResponseEntity<String> add(@PathVariable("typeId") long typeId) {
        return ResponseEntity.ok(cardServiceRouter.getByCardType(UserCardTypeEnumeration.getById(typeId)).save(cardGenerator.buildFrom(authenticationService.getCurrentUserEmail(), UserCardTypeEnumeration.getById(typeId))));
    }

    @GetMapping
    public ResponseEntity<List<UserCard>> getCards(@RequestParam(value = "typeId", defaultValue = "0") long typeId, @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(value = "mask", defaultValue = "true") boolean mask) {
        List<UserCard> cards;
        if (typeId == 0) {
            cards = cardServiceRouter.getByCardType(UserCardTypeEnumeration.VISA).getAllByOwnerEmail(authenticationService.getCurrentUserEmail(), pageNumber);
        } else {
            cards = cardServiceRouter.getByCardType(UserCardTypeEnumeration.getById(typeId)).getAllByOwnerEmailAndCardType(authenticationService.getCurrentUserEmail(), pageNumber, UserCardTypeEnumeration.getById(typeId));
        }
        return ResponseEntity.ok(mask ? userCardMapper.maskCardNumber(cards) : userCardMapper.showCardNumber(cards));
    }
}
