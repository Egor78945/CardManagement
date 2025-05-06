package com.example.card_management.repository.user.card;

import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.status.entity.UserCardStatus;
import com.example.card_management.service.user.card.UserCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Tag(name = "UserCardRepository", description = "Репозиторий пользовательских кард")
@Repository
public interface UserCardRepository extends JpaRepository<UserCard, Long> {
    @Operation(description = "Получить все пользовательские карты постранично")
    @Query("from UserCard c " +
            "left join fetch c.type t" +
            "left join fetch c.status s")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<UserCard> findAllUserCard(Pageable pageable);

    @Operation(description = "Получить все пользовательские карты по email владельца постранично")
    @Query("from UserCard c " +
            "left join fetch c.type t" +
            "left join fetch c.status s " +
            "where c.ownerEmail=?1")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<UserCard> findAllByOwner_Email(String ownerEmail, Pageable pageable);

    @Operation(description = "Получить все пользовательские карты по email владельца и идентификаторы типа постранично")
    @Query("from UserCard c " +
            "left join fetch c.type t" +
            "left join fetch c.status s " +
            "where c.ownerEmail=?1 and c.type.id=?2")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<UserCard> findAllByOwner_EmailAndType_Id(String ownerEmail, long typeId, Pageable pageable);

    @Operation(description = "Получить пользовательскую карту по номеру")
    @Query("from UserCard c " +
            "left join fetch c.type t " +
            "left join fetch c.status s " +
            "where c.number=?1")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    Optional<UserCard> findUserCardByNumber(String number);

    @Operation(description = "Проверить, существует ли пользовательская карта по номеру и идентификатору типа")
    boolean existsUserCardByNumberAndType_Id(String number, long typeId);

    @Operation(description = "Проверить, существует ли пользовательская карта по номеру")
    boolean existsUserCardByNumber(String number);

    @Operation(description = "получить количество пользовательских карт по email владельца и идентификатору типа")
    @Query("select count(u) from UserCard u where u.ownerEmail=?1 and u.type.id=?2")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    long findCountByOwnerEmailAndTypeId(String ownerEmail, long typeId);

    @Operation(description = "Обновить статус все пользовательских карт, у которых истёк срок действия")
    @Modifying
    @Query("update UserCard uc set uc.status=?2 where uc.expiredAt<=?1")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    void updateExpiredUserCardStatus(Date currentDate, UserCardStatus expiredStatus);

    @Operation(description = "Обновить статус пользовательской карты по номеру")
    @Modifying
    @Query("update UserCard uc set uc.status=?2 where uc.number=?1")
    @Transactional
    void updateUserCardStatusByCardNumber(String cardNumber, UserCardStatus userCardStatus);

    @Operation(description = "Удалить пользовательскую карту по номеру")
    @Modifying
    @Transactional
    void deleteUserCardByNumber(String number);
}
