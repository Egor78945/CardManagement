package com.example.card_management.model.user.card.request.dto;

import com.example.card_management.model.user.card.request.entity.UserCardRequest;
import com.example.card_management.service.validation.annotation.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class UserCardRequestDTO extends UserCardRequest {
    @NotNull(message = "card number is null")
    @Size(min = 16, max = 16, message = "invalid card number")
    @Digits(message = "invalid card number")
    private String cardNumber;
    @NotNull(message = "message is null")
    @Size(min = 100, max = 300, message = "message is out of range")
    private String message;
    @NotNull(message = "request id is null")
    private long requestId;

    public UserCardRequestDTO(String cardNumber, String message, long requestId) {
        this.cardNumber = cardNumber;
        this.message = message;
        this.requestId = requestId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserCardRequestDTO that = (UserCardRequestDTO) o;
        return Objects.equals(cardNumber, that.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cardNumber);
    }

    @Override
    public String toString() {
        return "UserCardRequestDTO{" +
                "message='" + message + '\'' +
                ", requestId=" + requestId +
                '}';
    }
}
