package com.example.card_management.service.user.card.monitoring;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserCardMonitoringService", description = "Сервис, предоставляющий основной функционал для мониторинга состояния пользовательских кард")
public abstract class UserCardMonitoringService {
    @Operation(description = "Обновить пользовательские карты с истёкшим сроком действия")
    public abstract void updateExpired();
}
