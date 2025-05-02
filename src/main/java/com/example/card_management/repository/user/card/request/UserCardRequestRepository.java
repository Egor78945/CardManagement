package com.example.card_management.repository.user.card.request;

import com.example.card_management.model.user.card.request.entity.UserCardRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCardRequestRepository extends JpaRepository<UserCardRequest, Long> {
    @Query("select case when " +
            "count(cr) > 0 then true else false end " +
            "from UserCardRequest cr where cr.card.number=?1")
    boolean existsUserCardRequestByCard_Number(String cardNumber);
}
