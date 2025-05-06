package com.example.card_management.repository.user.credential;

import com.example.card_management.model.user.credential.entity.UserCredential;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Tag(name = "UserCredentialRepository", description = "Репозиторий основной пользовательской информации")
@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {
    @Operation(description = "Получить основную пользовательскую информацию по email")
    @Query("from UserCredential u " +
            "left join fetch u.roleList l " +
            "left join fetch l.userRoleType " +
            "where u.email=?1")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    Optional<UserCredential> findUserCredentialByEmail(String email);

    @Operation(description = "Проверить, существует ли пользователь по email")
    boolean existsUserCredentialByEmail(String email);

    @Operation(description = "Получить хэш пароля пользователя по email")
    @Query("select password from UserCredential where email=?1")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    Optional<String> findPasswordHashByEmail(String email);
}
