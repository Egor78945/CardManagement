package com.example.card_management.model.user.card.request.type.entity;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;

import java.util.Objects;

@Tag(name = "UserCardRequestType", description = "Entity, представляющий тип пользовательского запроса, связанного с картами")
@Entity
@Table(name = "user_card_request_type")
public class UserCardRequestType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "type")
    private String type;

    public UserCardRequestType(long id, String type) {
        this.id = id;
        this.type = type;
    }

    public UserCardRequestType() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserCardRequestType that = (UserCardRequestType) o;
        return id == that.id && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return "CardRequestType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
