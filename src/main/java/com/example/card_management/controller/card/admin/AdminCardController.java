package com.example.card_management.controller.card.admin;

import com.example.card_management.controller.advice.handler.CommonControllerExceptionHandler;
import com.example.card_management.controller.card.advice.handler.CardControllerExceptionHandler;
import com.example.card_management.enumeration.user.card.status.UserCardStatusTypeEnumeration;
import com.example.card_management.enumeration.user.card.type.UserCardTypeEnumeration;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.request.entity.UserCardRequest;
import com.example.card_management.model.user.card.status.entity.UserCardStatus;
import com.example.card_management.service.user.card.UserCardService;
import com.example.card_management.service.user.card.admin.AdminUserCardService;
import com.example.card_management.service.user.card.util.UserCardUtility;
import com.example.card_management.service.user.card.request.UserCardRequestService;
import com.example.card_management.service.user.card.router.UserCardServiceRouter;
import com.example.card_management.service.user.card.validation.annotation.Card;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "AdminCardController", description = "Контроллер, обрабатывающий запросы администрированных пользователей для манипуляций пользовательскими картами")
@RestController
@RequestMapping("/api/v1/admin/card")
@CommonControllerExceptionHandler
@CardControllerExceptionHandler
@Validated
public class AdminCardController {
    private final UserCardServiceRouter<UserCard> userCardServiceRouter;
    private final AdminUserCardService<UserCard> adminUserCardService;
    private final UserCardRequestService<UserCardRequest> userCardRequestService;
    private final UserCardUtility userCardUtility;

    public AdminCardController(@Qualifier("adminUserCardServiceManager") AdminUserCardService<UserCard> adminUserCardService, @Qualifier("userCardServiceRouterManager") UserCardServiceRouter<UserCard> userCardServiceRouter, @Qualifier("userCardRequestServiceManager") UserCardRequestService<UserCardRequest> userCardRequestService, UserCardUtility userCardUtility) {
        this.userCardServiceRouter = userCardServiceRouter;
        this.adminUserCardService = adminUserCardService;
        this.userCardRequestService = userCardRequestService;
        this.userCardUtility = userCardUtility;
    }

    @Operation(description = "Получить постраничный список карт всех пользователей", responses = {
            @ApiResponse(responseCode = "200", description = "Получены пользовательские карты", content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserCard.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Произошла ошибка во время получения карт")}
    )
    @GetMapping
    public ResponseEntity<List<UserCard>> getUserCard(@RequestParam(value = "page", defaultValue = "0") int pageNumber,
                                                      @Parameter(description = "Флаг на маскировку номера карты") @RequestParam(value = "mask", defaultValue = "true") boolean mask) {
        UserCardService<UserCard> userCardService = userCardServiceRouter.getByCardType(UserCardTypeEnumeration.VISA);
        return ResponseEntity.ok(mask ? userCardUtility.maskCardNumber(userCardService.getAllUserCards(pageNumber)) : userCardUtility.showCardNumber(userCardService.getAllUserCards(pageNumber)));
    }

    @Operation(description = "Заблокировать карту пользователя по номеру", responses = {
            @ApiResponse(responseCode = "200", description = "Карта пользователя заблокирована"),
            @ApiResponse(responseCode = "400", description = "Произошла ошибка во время блокировки карты")}
    )
    @PutMapping("/block")
    public void blockUserCard(@RequestParam("number") @Card String cardNumber) {
        adminUserCardService.changeUserCardStatusByNumber(cardNumber, new UserCardStatus(UserCardStatusTypeEnumeration.STATUS_BLOCKED.getId(), UserCardStatusTypeEnumeration.STATUS_BLOCKED.name()));
    }

    @Operation(description = "Активировать карту пользователя по номеру", responses = {
            @ApiResponse(responseCode = "200", description = "Карта пользователя активирована"),
            @ApiResponse(responseCode = "400", description = "Произошла ошибка во время активирования карты")}
    )
    @PutMapping("/activate")
    public void activateUserCard(@RequestParam("number") @Card String cardNumber) {
        adminUserCardService.changeUserCardStatusByNumber(cardNumber, new UserCardStatus(UserCardStatusTypeEnumeration.STATUS_ACTIVE.getId(), UserCardStatusTypeEnumeration.STATUS_ACTIVE.name()));
    }

    @Operation(description = "Удалить карту пользователя по номеру", responses = {
            @ApiResponse(responseCode = "200", description = "Карта пользователя удалена"),
            @ApiResponse(responseCode = "400", description = "Произошла ошибка во время удаления карты")}
    )
    @DeleteMapping("/delete")
    public void deleteUserCardByNumber(@RequestParam("number") @Card String cardNumber){
        adminUserCardService.deleteCardByNumber(cardNumber);
    }
}
