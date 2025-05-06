package com.example.card_management.service.request;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "RequestService", description = "Сервис предоставляющий основной функционал для отправки запросов")
public interface RequestService<R> {
    void send(R request);
}
