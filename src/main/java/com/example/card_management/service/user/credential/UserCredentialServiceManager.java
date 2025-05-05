package com.example.card_management.service.user.credential;

import com.example.card_management.exception.AuthenticationException;
import com.example.card_management.exception.NotFoundException;
import com.example.card_management.model.user.credential.entity.UserCredential;
import com.example.card_management.repository.user.credential.UserCredentialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCredentialServiceManager extends UserCredentialService<UserCredential> {
    private final UserCredentialRepository userCredentialRepository;

    public UserCredentialServiceManager(UserCredentialRepository userCredentialRepository) {
        this.userCredentialRepository = userCredentialRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(UserCredential userCredential) {
        if (!existsByEmail(userCredential.getEmail())) {
            userCredentialRepository.save(userCredential);
        } else {
            throw new AuthenticationException(String.format("user with email '%s' is already exists", userCredential.getEmail()));
        }
    }

    @Override
    public UserCredential getByEmail(String email) {
        return userCredentialRepository.findUserCredentialByEmail(email).orElseThrow(() -> new NotFoundException(String.format("user with email '%s' is not found", email)));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userCredentialRepository.existsUserCredentialByEmail(email);
    }

    @Override
    public String getPasswordByEmail(String email) {
        return userCredentialRepository.findPasswordHashByEmail(email).orElseThrow(() -> new NotFoundException(String.format("password of user with email '%s' is not found", email)));
    }
}
