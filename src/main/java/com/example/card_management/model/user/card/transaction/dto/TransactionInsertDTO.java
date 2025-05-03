package com.example.card_management.model.user.card.transaction.dto;

import com.example.card_management.service.user.card.validation.annotation.Card;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class TransactionInsertDTO {
    @NotNull(message = "card number is null")
    @Card
    private String cardNumberTo;
    @Min(value = 10, message = "amount is too low")
    @Max(value = 1_000_000, message = "amount is too high")
    private int amount;

    public TransactionInsertDTO(String cardNumberTo, int amount) {
        this.cardNumberTo = cardNumberTo;
        this.amount = amount;
    }

    public String getCardNumberTo() {
        return cardNumberTo;
    }

    public void setCardNumberTo(String cardNumberTo) {
        this.cardNumberTo = cardNumberTo;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionInsertDTO that = (TransactionInsertDTO) o;
        return amount == that.amount && Objects.equals(cardNumberTo, that.cardNumberTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumberTo, amount);
    }
}
