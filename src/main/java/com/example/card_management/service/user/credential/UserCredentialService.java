package com.example.card_management.service.user.credential;

import com.example.card_management.model.user.credential.entity.UserCredential;

public interface UserCredentialService<U extends UserCredential> {
    void save(U u);

    U getByEmail(String email);

    boolean existsByEmail(String email);

    String getPasswordByEmail(String email);
}
