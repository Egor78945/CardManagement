package com.example.card_management.model.user.card.status.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_card_status")
public class UserCardStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "status")
    private String status;

    public UserCardStatus() {
    }

    public UserCardStatus(long id, String status) {
        this.id = id;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserCardStatus that = (UserCardStatus) o;
        return id == that.id && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }

    @Override
    public String toString() {
        return "CardStatus{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
