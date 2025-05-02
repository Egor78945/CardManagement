package com.example.card_management.model.card.type.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "card_type")
public class CardType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "type")
    private String type;

    public CardType() {
    }

    public CardType(long id, String type) {
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
        CardType cardType = (CardType) o;
        return id == cardType.id && Objects.equals(type, cardType.type);
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
