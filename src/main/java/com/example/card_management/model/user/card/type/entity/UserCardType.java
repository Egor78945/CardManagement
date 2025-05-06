package com.example.card_management.model.user.card.type.entity;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;

import java.util.Objects;

@Tag(name = "UserCardType", description = "Entity, представляющий тип пользовательской карты")
@Entity
@Table(name = "user_card_type")
public class UserCardType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "type")
    private String type;

    public UserCardType() {
    }

    public UserCardType(long id, String type) {
        this.id = id;
        this.type = type;
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
        UserCardType userCardType = (UserCardType) o;
        return id == userCardType.id && Objects.equals(type, userCardType.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return "CardType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
