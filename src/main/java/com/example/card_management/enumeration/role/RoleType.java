package com.example.card_management.enumeration.role;

public enum RoleType {
    ROLE_USER(1), ROLE_ADMIN(2);
    private final long id;

    RoleType(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
