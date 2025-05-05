package com.example.card_management.service.user.card.monitoring;

import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.service.user.card.admin.AdminUserCardService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ExpirationUserCardMonitoringService extends UserCardMonitoringService {
    private final AdminUserCardService<UserCard> userCardService;

    public ExpirationUserCardMonitoringService(@Qualifier("adminUserCardServiceManager") AdminUserCardService<UserCard> userCardService) {
        this.userCardService = userCardService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateExpiredCards(){
        userCardService.updateExpiredUserCards();
    }
}
