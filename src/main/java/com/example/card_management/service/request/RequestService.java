package com.example.card_management.service.request;

public interface RequestService<R> {
    void send(R request);
}
