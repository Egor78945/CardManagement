package com.example.card_management.controller.card.request;

import com.example.card_management.controller.advice.handler.CommonControllerExceptionHandler;
import com.example.card_management.controller.card.advice.handler.CardControllerExceptionHandler;
import com.example.card_management.model.user.card.request.dto.UserCardRequestDTO;
import com.example.card_management.model.user.card.request.entity.UserCardRequest;
import com.example.card_management.model.user.dto.security.UserCredentialDTO;
import com.example.card_management.service.security.authentication.AuthenticationService;
import com.example.card_management.service.user.card.request.UserCardRequestService;
import com.example.card_management.service.user.card.request.mapper.UserCardRequestMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/card/request")
@CommonControllerExceptionHandler
@CardControllerExceptionHandler
public class CardRequestController {
    private final UserCardRequestService<UserCardRequest> userCardRequestService;
    private final UserCardRequestMapper userCardRequestMapper;
    private final AuthenticationService<UserCredentialDTO> authenticationService;

    public CardRequestController(@Qualifier("userCardRequestServiceManager") UserCardRequestService<UserCardRequest> userCardRequestService, AuthenticationService<UserCredentialDTO> authenticationService, UserCardRequestMapper userCardRequestMapper) {
        this.userCardRequestService = userCardRequestService;
        this.userCardRequestMapper = userCardRequestMapper;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/request")
    public void sendRequest(@RequestBody @Valid UserCardRequestDTO userCardRequestDTO) {
        userCardRequestService.send(userCardRequestMapper.mapTo(authenticationService.getCurrentUserEmail(), userCardRequestDTO));
    }
}
