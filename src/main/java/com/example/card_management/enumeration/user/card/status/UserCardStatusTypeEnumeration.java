package com.example.card_management.enumeration.user.card.status;

public enum UserCardStatusTypeEnumeration {
    STATUS_ACTIVE(1), STATUS_BLOCKED(2), STATUS_EXPIRED(3);
    private final long id;

    UserCardStatusTypeEnumeration(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
