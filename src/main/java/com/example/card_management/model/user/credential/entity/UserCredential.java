package com.example.card_management.model.user.credential.entity;

import com.example.card_management.model.user.role.entity.UserRole;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_credential")
public class UserCredential {
    @Id
    @Column(name = "email")
    private String email;
    @Column(name = "password", updatable = false)
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserRole> roleList;

    public UserCredential(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserCredential() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<UserRole> roleList) {
        this.roleList = roleList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserCredential that = (UserCredential) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "UserCredential{" +
                "email='" + email + '\'' +
                '}';
    }
}
