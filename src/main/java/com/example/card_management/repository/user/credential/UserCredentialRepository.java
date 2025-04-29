package com.example.card_management.repository.user.credential;

import com.example.card_management.model.user.credential.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {
    Optional<UserCredential> findUserCredentialByEmail(String email);

    boolean existsUserCredentialByEmail(String email);

    @Query("select password from UserCredential where email=?1")
    Optional<String> findPasswordHashByEmail(String email);
}
