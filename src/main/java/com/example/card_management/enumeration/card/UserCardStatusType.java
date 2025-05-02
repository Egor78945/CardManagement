package com.example.card_management.enumeration.card;

public enum UserCardStatusType {
    STATUS_ACTIVE(1), STATUS_BLOCKED(2), STATUS_EXPIRED(3);
    private final long id;

    UserCardStatusType(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
