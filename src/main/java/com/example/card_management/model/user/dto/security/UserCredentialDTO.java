package com.example.card_management.model.user.dto.security;

import com.example.card_management.service.security.validation.annotation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class UserCredentialDTO {
    @NotNull(message = "email is null")
    @Email(message = "invalid email format")
    private String email;
    @NotNull(message = "password in null")
    @Password
    private String password;

    public UserCredentialDTO(String email,
                             String password) {
        this.email = email.toLowerCase();
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserCredentialDTO that = (UserCredentialDTO) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "UserCredentialDTO{" +
                "email='" + email + '\'' +
                '}';
    }
}
