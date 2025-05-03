package com.example.card_management.service.user.card.request;

import com.example.card_management.model.user.card.request.entity.UserCardRequest;
import com.example.card_management.service.request.RequestService;

public abstract class UserCardRequestService<R extends UserCardRequest> implements RequestService<R> {
    @Override
    public abstract void send(String senderUsername, R request);
}
