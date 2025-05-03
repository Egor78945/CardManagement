package com.example.card_management.controller.transaction;

import com.example.card_management.controller.advice.handler.CommonControllerExceptionHandler;
import com.example.card_management.controller.transaction.advice.handler.TransactionControllerExceptionHandler;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.transaction.dto.TransactionInsertDTO;
import com.example.card_management.model.user.card.transaction.dto.TransactionTransferDTO;
import com.example.card_management.model.user.dto.security.UserCredentialDTO;
import com.example.card_management.service.security.authentication.AuthenticationService;
import com.example.card_management.service.user.card.transaction.UserCardTransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping("/insert")
    public ResponseEntity<Double> insertMoney(@RequestBody @Valid TransactionInsertDTO insertDTO){
        return ResponseEntity.ok(userCardTransactionService.insert(insertDTO, authenticationService.getCurrentUserEmail()));
    }

    @PutMapping("/transfer")
    public ResponseEntity<Double> transferMoney(@RequestBody @Valid TransactionTransferDTO transferDTO){
        return ResponseEntity.ok(userCardTransactionService.transfer(transferDTO, authenticationService.getCurrentUserEmail()));
    }
}
