package com.example.card_management.controller.transaction;

import com.example.card_management.controller.advice.handler.CommonControllerExceptionHandler;
import com.example.card_management.controller.transaction.advice.handler.TransactionControllerExceptionHandler;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.transaction.dto.TransactionInsertDTO;
import com.example.card_management.model.user.card.transaction.dto.TransactionTransferDTO;
import com.example.card_management.model.user.dto.security.UserCredentialDTO;
import com.example.card_management.service.security.authentication.AuthenticationService;
import com.example.card_management.service.user.card.transaction.UserCardTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "TransactionController", description = "Контроллер, обрабатывающий запросы пользователей, связанных с транзакциями между картами пользоватей")
@RestController
@RequestMapping("/api/v1/transaction")
@CommonControllerExceptionHandler
@TransactionControllerExceptionHandler
public class TransactionController {
    private final UserCardTransactionService<UserCard> userCardTransactionService;
    private final AuthenticationService<UserCredentialDTO> authenticationService;

    public TransactionController(@Qualifier("userCardTransactionServiceManager") UserCardTransactionService<UserCard> userCardTransactionService, @Qualifier("authenticationServiceManager") AuthenticationService<UserCredentialDTO> authenticationService) {
        this.userCardTransactionService = userCardTransactionService;
        this.authenticationService = authenticationService;
    }

    @Operation(description = "Внести деньги на карту пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "Карта пользователя была пополнена и была получена текущий баланс карты", content = @Content(schema = @Schema(
                    implementation = Double.class
            ))),
            @ApiResponse(responseCode = "400", description = "Произошла ошибка во время пополнения карты")}
    )
    @PutMapping("/insert")
    public ResponseEntity<Double> insertMoney(@RequestBody @Valid TransactionInsertDTO insertDTO){
        return ResponseEntity.ok(userCardTransactionService.insert(insertDTO));
    }

    @Operation(description = "Перевести деньги с одной карты на другую", responses = {
            @ApiResponse(responseCode = "200", description = "Сумма была переведена и был получен текущей баланс отправляющей карты", content = @Content(schema = @Schema(
                    implementation = Double.class
            ))),
            @ApiResponse(responseCode = "401", description = "Произошла ошибка во время перевода денег")}
    )
    @PutMapping("/transfer")
    public ResponseEntity<Double> transferMoney(@RequestBody @Valid TransactionTransferDTO transferDTO){
        return ResponseEntity.ok(userCardTransactionService.transfer(transferDTO));
    }
}
