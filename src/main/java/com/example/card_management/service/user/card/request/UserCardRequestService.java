package com.example.card_management.service.user.card.request;

import com.example.card_management.configuration.card.environment.CardEnvironment;
import com.example.card_management.model.user.card.request.entity.UserCardRequest;
import com.example.card_management.repository.user.card.request.UserCardRequestRepository;
import com.example.card_management.service.request.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "UserCardRequestService", description = "Сервис, предоставляющий основной функционал по работе с запросами пользователей по картам")
public abstract class UserCardRequestService<R extends UserCardRequest> implements RequestService<R> {
    protected final UserCardRequestRepository userCardRequestRepository;
    protected final CardEnvironment cardEnvironment;

    public UserCardRequestService(UserCardRequestRepository userCardRequestRepository, CardEnvironment cardEnvironment) {
        this.userCardRequestRepository = userCardRequestRepository;
        this.cardEnvironment = cardEnvironment;
    }

    @Override
    public abstract void send(R request);

    @Operation(description = "Получить пользовательский запрос по email отправителя")
    public abstract UserCardRequest getBySenderEmail(String senderEmail);

    @Operation(description = "Получить постраничный список всех пользовательских запросов")
    public abstract List<UserCardRequest> getAll(int pageNumber);
}
