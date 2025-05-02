package com.example.card_management.controller.card;

import com.example.card_management.configuration.card.environment.CardEnvironment;
import com.example.card_management.controller.advice.handler.CommonControllerExceptionHandler;
import com.example.card_management.controller.card.advice.handler.CardControllerExceptionHandler;
import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.request.dto.UserCardRequestDTO;
import com.example.card_management.model.user.dto.security.UserCredentialDTO;
import com.example.card_management.service.security.authentication.AuthenticationService;
import com.example.card_management.service.user.card.generator.UserCardGenerator;
import com.example.card_management.service.user.card.router.UserCardServiceRouter;
import jakarta.validation.Valid;
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
    private final CardEnvironment cardEnvironment;

    public CardController(@Qualifier("authenticationServiceManager") AuthenticationService<UserCredentialDTO> authenticationService, @Qualifier("userCardServiceRouterManager") UserCardServiceRouter<UserCard> cardServiceRouter, @Qualifier("userCardGeneratorManager") UserCardGenerator<UserCard> cardGenerator, CardEnvironment cardEnvironment) {
        this.authenticationService = authenticationService;
        this.cardServiceRouter = cardServiceRouter;
        this.cardGenerator = cardGenerator;
        this.cardEnvironment = cardEnvironment;
    }

    @PostMapping("/add")
    public void add(@RequestParam("typeId") long typeId) {
        cardServiceRouter.getByCardType(UserCardTypeEnumeration.getById(typeId)).save(cardGenerator.buildFrom(authenticationService.getCurrentUserEmail(), UserCardTypeEnumeration.getById(typeId)));
    }

    @GetMapping
    public ResponseEntity<List<UserCard>> getCards(@RequestParam(value = "typeId", defaultValue = "0") long typeId, @RequestParam("pageNumber") int pageNumber) {
        if (typeId == 0) {
            return ResponseEntity.ok(cardServiceRouter.getByCardType(UserCardTypeEnumeration.VISA).getAllByOwnerEmail(authenticationService.getCurrentUserEmail(), pageNumber, cardEnvironment.getPAGINATION_SIZE()));
        }
        return ResponseEntity.ok(cardServiceRouter.getByCardType(UserCardTypeEnumeration.getById(typeId)).getAllByOwnerEmailAndCardType(authenticationService.getCurrentUserEmail(), pageNumber, cardEnvironment.getPAGINATION_SIZE()));
    }

    @PostMapping("/request")
    public void sendRequest(@RequestBody @Valid UserCardRequestDTO userCardRequestDTO) {
        cardServiceRouter.getByCardType(UserCardTypeEnumeration.VISA).sendCardRequest(authenticationService.getCurrentUserEmail(), userCardRequestDTO);
    }
}
