package com.example.card_management.service.user.card.monitoring;

import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.service.user.card.admin.AdminUserCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Tag(name = "UserCardMonitoringServiceManager", description = "Базовая реализация сервиса по мониторингу пользовательских кард")
@Service
public class UserCardMonitoringServiceManager extends UserCardMonitoringService {
    private final AdminUserCardService<UserCard> userCardService;

    public UserCardMonitoringServiceManager(@Qualifier("adminUserCardServiceManager") AdminUserCardService<UserCard> userCardService) {
        this.userCardService = userCardService;
    }

    @Operation(description = "Обновить пользовательские карты с истёкшим сроком действия каждый день в 00:00")
    @Scheduled(cron = "0 0 0 * * ?")
    @Override
    public void updateExpired(){
        userCardService.updateExpiredUserCards();
    }
}
