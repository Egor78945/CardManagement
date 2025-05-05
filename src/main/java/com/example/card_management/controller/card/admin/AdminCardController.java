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
import com.example.card_management.service.user.card.mapper.UserCardMapper;
import com.example.card_management.service.user.card.request.UserCardRequestService;
import com.example.card_management.service.user.card.router.UserCardServiceRouter;
import com.example.card_management.service.user.card.validation.annotation.Card;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/card")
@CommonControllerExceptionHandler
@CardControllerExceptionHandler
@Validated
public class AdminCardController {
    private final UserCardServiceRouter<UserCard> userCardServiceRouter;
    private final AdminUserCardService<UserCard> adminUserCardService;
    private final UserCardRequestService<UserCardRequest> userCardRequestService;
    private final UserCardMapper userCardMapper;

    public AdminCardController(@Qualifier("adminUserCardServiceManager") AdminUserCardService<UserCard> adminUserCardService, @Qualifier("userCardServiceRouterManager") UserCardServiceRouter<UserCard> userCardServiceRouter, @Qualifier("userCardRequestServiceManager") UserCardRequestService<UserCardRequest> userCardRequestService, UserCardMapper userCardMapper) {
        this.userCardServiceRouter = userCardServiceRouter;
        this.adminUserCardService = adminUserCardService;
        this.userCardRequestService = userCardRequestService;
        this.userCardMapper = userCardMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserCard>> getUserCard(@RequestParam(value = "page", defaultValue = "0") int pageNumber, @RequestParam(value = "mask", defaultValue = "true") boolean mask) {
        UserCardService<UserCard> userCardService = userCardServiceRouter.getByCardType(UserCardTypeEnumeration.VISA);
        return ResponseEntity.ok(mask ? userCardMapper.maskCardNumber(userCardService.getAllUserCards(pageNumber)) : userCardMapper.showCardNumber(userCardService.getAllUserCards(pageNumber)));
    }

    @GetMapping("/request")
    public ResponseEntity<List<UserCardRequest>> getUserCardRequest(@RequestParam(value = "page", defaultValue = "0") int pageNumber){
        return ResponseEntity.ok(userCardRequestService.getAll(pageNumber));
    }

    @GetMapping("/request/{email}")
    public ResponseEntity<UserCardRequest> getUserCardRequestBySenderEmail(@PathVariable("email") @Email String userEmail){
        return ResponseEntity.ok(userCardRequestService.getBySenderEmail(userEmail));
    }

    @PutMapping("/block")
    public void blockUserCard(@RequestParam("number") @Card String cardNumber) {
        adminUserCardService.changeUserCardStatusByNumber(cardNumber, new UserCardStatus(UserCardStatusTypeEnumeration.STATUS_BLOCKED.getId(), UserCardStatusTypeEnumeration.STATUS_BLOCKED.name()));
    }

    @PutMapping("/activate")
    public void activateUserCard(@RequestParam("number") @Card String cardNumber) {
        adminUserCardService.changeUserCardStatusByNumber(cardNumber, new UserCardStatus(UserCardStatusTypeEnumeration.STATUS_ACTIVE.getId(), UserCardStatusTypeEnumeration.STATUS_ACTIVE.name()));
    }

    @DeleteMapping("/delete")
    public void deleteUserCardByNumber(@RequestParam("number") @Card String phoneNumber){
        adminUserCardService.deleteCardByPhoneNumber(phoneNumber);
    }
}
