package com.example.card_management.model.user.role.entity;

import com.example.card_management.model.user.credential.entity.UserCredential;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;

import java.util.Objects;

@Tag(name = "UserRole", description = "Entity, представляющий информацию о пользовательской роли")
@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_email")
    private String user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private UserRoleType userRoleType;

    public UserRole(UserCredential user, UserRoleType userRoleType) {
        this.user = user.getEmail();
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
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
                ", user_email=" + (user != null ? user : null) +
                ", role_id=" + (userRoleType != null ? userRoleType.getId() : null) +
                '}';
    }
}
