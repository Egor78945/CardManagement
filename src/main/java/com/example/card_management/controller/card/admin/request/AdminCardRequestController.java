package com.example.card_management.controller.card.admin.request;

import com.example.card_management.model.user.card.request.entity.UserCardRequest;
import com.example.card_management.service.user.card.request.UserCardRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "AdminCardRequestController", description = "Контроллер, обрабатывающий запросы пользователей на запросы по пользовательским картам")
@RestController
@RequestMapping("/api/v1/admin/card/request")
public class AdminCardRequestController {
    private final UserCardRequestService<UserCardRequest> userCardRequestService;

    public AdminCardRequestController(@Qualifier("userCardRequestServiceManager") UserCardRequestService<UserCardRequest> userCardRequestService) {
        this.userCardRequestService = userCardRequestService;
    }

    @Operation(description = "Получить постраничный список запросов пользователей, связанных с картами", responses = {
            @ApiResponse(responseCode = "200", description = "Получен список запросов", content = {@Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserCardRequest.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Произошла ошибка во время получения списка запросов")}
    )
    @GetMapping
    public ResponseEntity<List<UserCardRequest>> getUserCardRequest(@RequestParam(value = "page", defaultValue = "0") int pageNumber){
        return ResponseEntity.ok(userCardRequestService.getAll(pageNumber));
    }

    @Operation(description = "Получить запрос пользователя, связанный с картой по email пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "Получен пользовательский запрос", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserCardRequest.class)
            )),
            @ApiResponse(responseCode = "400", description = "Произошла ошибка во время получения запроса")}
    )
    @GetMapping("/{email}")
    public ResponseEntity<UserCardRequest> getUserCardRequestBySenderEmail(@PathVariable("email") @Email String userEmail){
        return ResponseEntity.ok(userCardRequestService.getBySenderEmail(userEmail));
    }
}
