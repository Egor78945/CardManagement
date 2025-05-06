package com.example.card_management.controller.card;

import com.example.card_management.controller.advice.handler.CommonControllerExceptionHandler;
import com.example.card_management.controller.card.advice.handler.CardControllerExceptionHandler;
import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.credential.dto.UserCredentialDTO;
import com.example.card_management.service.security.authentication.AuthenticationService;
import com.example.card_management.service.user.card.generator.UserCardGenerator;
import com.example.card_management.service.user.card.util.UserCardUtility;
import com.example.card_management.service.user.card.router.UserCardServiceRouter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CardController", description = "Контроллер, обрабатывающий запросы пользователей, связанных с картами")
@RestController
@RequestMapping("/api/v1/card")
@CommonControllerExceptionHandler
@CardControllerExceptionHandler
public class CardController {
    private final AuthenticationService<UserCredentialDTO> authenticationService;
    private final UserCardServiceRouter<UserCard> cardServiceRouter;
    private final UserCardGenerator<UserCard> cardGenerator;
    private final UserCardUtility userCardUtility;

    public CardController(@Qualifier("authenticationServiceManager") AuthenticationService<UserCredentialDTO> authenticationService, @Qualifier("userCardServiceRouterManager") UserCardServiceRouter<UserCard> cardServiceRouter, @Qualifier("userCardGeneratorManager") UserCardGenerator<UserCard> cardGenerator, UserCardUtility userCardUtility) {
        this.authenticationService = authenticationService;
        this.cardServiceRouter = cardServiceRouter;
        this.cardGenerator = cardGenerator;
        this.userCardUtility = userCardUtility;
    }

    @Operation(description = "Привязать к пользователю новую карту", responses = {
            @ApiResponse(responseCode = "200", description = "Новая карта привязана к пользователю", content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "400", description = "Произошла ошибка во время привязки новой карты к пользователю")}
    )
    @PostMapping("/add/{typeId}")
    public ResponseEntity<String> add(@Parameter(description = "Идентификатор типа карты") @PathVariable("typeId") long typeId) {
        return ResponseEntity.ok(cardServiceRouter.getByCardType(UserCardTypeEnumeration.getById(typeId)).save(cardGenerator.buildFrom(authenticationService.getCurrentUserEmail(), UserCardTypeEnumeration.getById(typeId))));
    }

    @Operation(description = "Получить постраничный список карт текущего (авторизованного) пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "Новая карта привязана к пользователю", content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "400", description = "Произошла ошибка во время привязки новой карты к пользователю")}
    )
    @GetMapping
    public ResponseEntity<List<UserCard>> getCardsOfCurrentUser(@Parameter(description = "Идентификатор типа карты") @RequestParam(value = "typeId", defaultValue = "0") long typeId,
                                                                @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                                @Parameter(description = "Флаг на маскировку номера карты") @RequestParam(value = "mask", defaultValue = "true") boolean mask) {
        List<UserCard> cards;
        if (typeId == 0) {
            cards = cardServiceRouter.getByCardType(UserCardTypeEnumeration.VISA).getAllByOwnerEmail(authenticationService.getCurrentUserEmail(), pageNumber);
        } else {
            cards = cardServiceRouter.getByCardType(UserCardTypeEnumeration.getById(typeId)).getAllByOwnerEmailAndCardType(authenticationService.getCurrentUserEmail(), pageNumber, UserCardTypeEnumeration.getById(typeId));
        }
        return ResponseEntity.ok(mask ? userCardUtility.maskCardNumber(cards) : userCardUtility.showCardNumber(cards));
    }
}
