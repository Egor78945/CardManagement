package com.example.card_management.repository.user.card;

import com.example.card_management.model.user.card.entity.UserCard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard, Long> {
    @Query("from UserCard c " +
            "left join fetch c.type t" +
            "left join fetch c.status s " +
            "where c.ownerEmail=?1")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<UserCard> findAllByOwner_Email(String ownerEmail, Pageable pageable);

    @Query("from UserCard c " +
            "left join fetch c.type t" +
            "left join fetch c.status s " +
            "where c.ownerEmail=?1 and c.type.id=?2")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<UserCard> findAllByOwner_EmailAndType_Id(String ownerEmail, long typeId, Pageable pageable);

    @Transactional(isolation = Isolation.READ_COMMITTED)
    boolean existsUserCardByNumber(String number);

    @Query("select count(u) from UserCard u where u.ownerEmail=?1 and u.type.id=?2")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    long findCountByOwnerEmailAndTypeId(String ownerEmail, long typeId);
}
