package com.example.card_management.controller.security.authentication;

import com.example.card_management.controller.advice.handler.CommonControllerExceptionHandler;
import com.example.card_management.controller.security.authentication.advice.handler.AuthenticationControllerExceptionHandler;
import com.example.card_management.model.user.credential.dto.UserCredentialDTO;
import com.example.card_management.service.security.authentication.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AuthenticationController", description = "Контроллер, обрабатывающий запросы пользователей, связанных с аутентификацией и регистрацией")
@RestController
@RequestMapping("/api/v1/authentication")
@CommonControllerExceptionHandler
@AuthenticationControllerExceptionHandler
public class AuthenticationController {
    private final AuthenticationService<UserCredentialDTO> authenticationService;

    public AuthenticationController(@Qualifier("authenticationServiceManager") AuthenticationService<UserCredentialDTO> authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(description = "Зарегестрировать нового пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "Новый пользователь зарегестирован"),
            @ApiResponse(responseCode = "401", description = "Произошла ошибка во время регистрации пользователя")}
    )
    @PostMapping("/registration")
    public void registration(@RequestBody @Valid UserCredentialDTO user) {
        authenticationService.register(user);
    }

    @Operation(description = "Аутентифицировать пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "Токен был выдан пользователю после успешной аутентификации", content = @Content(schema = @Schema(
                    implementation = String.class
            ))),
            @ApiResponse(responseCode = "401", description = "Произошла ошибка во время аутентификации пользователя")}
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserCredentialDTO user) {
        return ResponseEntity.ok(authenticationService.authenticate(user));
    }
}
