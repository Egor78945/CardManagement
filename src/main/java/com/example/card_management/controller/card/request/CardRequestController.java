package com.example.card_management.controller.card.request;

import com.example.card_management.controller.advice.handler.CommonControllerExceptionHandler;
import com.example.card_management.controller.card.advice.handler.CardControllerExceptionHandler;
import com.example.card_management.model.user.card.request.dto.UserCardRequestDTO;
import com.example.card_management.model.user.card.request.entity.UserCardRequest;
import com.example.card_management.model.user.credential.dto.UserCredentialDTO;
import com.example.card_management.service.security.authentication.AuthenticationService;
import com.example.card_management.service.user.card.request.UserCardRequestService;
import com.example.card_management.service.user.card.request.mapper.UserCardRequestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CardRequestController", description = "Контроллер, обрабатывающий запросы пользователей для отправку запросов, касательно карт")
@RestController
@RequestMapping("/api/v1/card/request")
@CommonControllerExceptionHandler
@CardControllerExceptionHandler
public class CardRequestController {
    private final UserCardRequestService<UserCardRequest> userCardRequestService;
    private final UserCardRequestMapper userCardRequestMapper;

    public CardRequestController(@Qualifier("userCardRequestServiceManager") UserCardRequestService<UserCardRequest> userCardRequestService, UserCardRequestMapper userCardRequestMapper) {
        this.userCardRequestService = userCardRequestService;
        this.userCardRequestMapper = userCardRequestMapper;
    }

    @Operation(description = "Отправить запрос, связанный с картой пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "Запрос, связанный с картами пользователей отправлен"),
            @ApiResponse(responseCode = "400", description = "Произошла ошибка во время отправки запроса")
    }
    )
    public void sendRequest(@RequestBody @Valid UserCardRequestDTO userCardRequestDTO) {
        userCardRequestService.send(userCardRequestMapper.mapTo(userCardRequestDTO));
    }
}
