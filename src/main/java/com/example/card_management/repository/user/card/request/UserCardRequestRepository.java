package com.example.card_management.repository.user.card.request;

import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.card.request.entity.UserCardRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCardRequestRepository extends JpaRepository<UserCardRequest, Long> {
    @Query("select case when " +
            "count(cr) > 0 then true else false end " +
            "from UserCardRequest cr where cr.card.number=?1")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    boolean existsUserCardRequestByCard_Number(String cardNumber);

    @Query("from UserCardRequest ucr " +
            "left join fetch ucr.card " +
            "left join fetch ucr.requestType " +
            "left join fetch ucr.sender " +
            "where ucr.card.number=?1")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    Optional<UserCardRequest> findUserCardRequestByCard_Number(String cardNumber);

    @Transactional
    Optional<UserCardRequest> findUserCardRequestBySender_Email(String senderEmail);

    @Query("from UserCardRequest")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<UserCardRequest> findAllUserCardRequest(Pageable pageable);
}
