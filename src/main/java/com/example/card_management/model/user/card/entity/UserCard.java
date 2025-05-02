package com.example.card_management.model.user.card.entity;

import com.example.card_management.model.user.card.status.entity.UserCardStatus;
import com.example.card_management.model.user.card.type.entity.UserCardType;
import com.example.card_management.model.user.credential.entity.UserCredential;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user_card")
public class UserCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "owner_email")
    private String ownerEmail;
    @Column(name = "number")
    private String number;
    @Column(name = "expiredAt")
    private Date expiredAt;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private UserCardStatus status;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private UserCardType type;
    @Column(name = "balance")
    private double balance;

    public UserCard() {
    }

    public UserCard(String ownerEmail, String number, Date expiredAt, UserCardStatus status, UserCardType type, double balance) {
        this.ownerEmail = ownerEmail;
        this.number = number;
        this.expiredAt = expiredAt;
        this.status = status;
        this.type = type;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public UserCardStatus getStatus() {
        return status;
    }

    public void setStatus(UserCardStatus status) {
        this.status = status;
    }

    public UserCardType getType() {
        return type;
    }

    public void setType(UserCardType type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserCard userCard = (UserCard) o;
        return id == userCard.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserCard{" +
                "id=" + id +
                ", expiredAt=" + expiredAt +
                ", status=" + (status != null ? status : null) +
                ", type=" + (type != null ? type : null) +
                ", balance=" + balance +
                '}';
    }
}
