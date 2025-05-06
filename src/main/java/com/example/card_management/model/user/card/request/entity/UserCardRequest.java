package com.example.card_management.model.user.card.request.entity;

import com.example.card_management.model.user.card.request.type.entity.UserCardRequestType;
import com.example.card_management.model.user.card.entity.UserCard;
import com.example.card_management.model.user.credential.entity.UserCredential;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Tag(name = "UserCardRequest", description = "Entity, представляющий пользовательский запрос, связанный с картами")
@Entity
@Table(name = "user_card_request")
public class UserCardRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "sender_email")
    private UserCredential sender;
    @ManyToOne
    @JoinColumn(name = "request_type_id")
    private UserCardRequestType requestType;
    @OneToOne
    @JoinColumn(name = "card_number", referencedColumnName = "number")
    private UserCard card;
    @Column(name = "message")
    private String message;

    public UserCardRequest() {
    }

    public UserCardRequest(UserCredential sender, UserCardRequestType requestType, UserCard card, String message) {
        this.sender = sender;
        this.requestType = requestType;
        this.card = card;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserCredential getSender() {
        return sender;
    }

    public void setSender(UserCredential sender) {
        this.sender = sender;
    }

    public UserCardRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(UserCardRequestType requestType) {
        this.requestType = requestType;
    }

    public UserCard getCard() {
        return card;
    }

    public void setCard(UserCard card) {
        this.card = card;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserCardRequest that = (UserCardRequest) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserCardRequest{" +
                "id=" + id +
                ", requestType=" + requestType +
                ", message='" + message + '\'' +
                '}';
    }
}
