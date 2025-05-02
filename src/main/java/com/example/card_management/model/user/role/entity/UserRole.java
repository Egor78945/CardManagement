package com.example.card_management.model.user.role.entity;

import com.example.card_management.model.user.credential.entity.UserCredential;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_email")
    private UserCredential user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private UserRoleType userRoleType;

    public UserRole(UserCredential user, UserRoleType userRoleType) {
        this.user = user;
        this.userRoleType = userRoleType;
    }

    public UserRole() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserCredential getUser() {
        return user;
    }

    public void setUser(UserCredential user) {
        this.user = user;
    }

    public UserRoleType getRole() {
        return userRoleType;
    }

    public void setRole(UserRoleType userRoleType) {
        this.userRoleType = userRoleType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", user_email=" + (user != null ? user.getEmail() : null) +
                ", role_id=" + (userRoleType != null ? userRoleType.getId() : null) +
                '}';
    }
}
