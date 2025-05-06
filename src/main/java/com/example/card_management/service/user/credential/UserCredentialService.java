package com.example.card_management.service.user.credential;

import com.example.card_management.model.user.credential.entity.UserCredential;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "UserCredentialService", description = "Сервис, предоставляющий основной функционал по работе с основными пользовательскими данными")
public abstract class UserCredentialService<U extends UserCredential> {
    public abstract void save(U u);

    public abstract U getByEmail(String email);

    public abstract boolean existsByEmail(String email);

    public abstract String getPasswordByEmail(String email);
}
