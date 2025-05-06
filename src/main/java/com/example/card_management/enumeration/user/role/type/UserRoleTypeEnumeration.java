package com.example.card_management.enumeration.user.role.type;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserRoleTypeEnumeration",description = "Перечисление пользовательских ролей")
public enum UserRoleTypeEnumeration {
    ROLE_USER(1), ROLE_ADMIN(2);
    private final long id;

    UserRoleTypeEnumeration(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
