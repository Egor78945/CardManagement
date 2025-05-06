package com.example.card_management.model.user.role.entity;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;

import java.util.Objects;

@Tag(name = "UserRoleType", description = "Entity, представляющий тип пользовательской роли")
@Entity
@Table(name = "user_role_type")
public class UserRoleType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role")
    private String role;

    public UserRoleType(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public UserRoleType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleType userRoleType1 = (UserRoleType) o;
        return Objects.equals(id, userRoleType1.id) && Objects.equals(role, userRoleType1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
