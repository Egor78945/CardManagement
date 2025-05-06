package com.example.card_management.model.user.card.transaction.dto;

import com.example.card_management.service.user.card.validation.annotation.Card;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Tag(name = "TransactionTransferDTO", description = "DTO, имеющий основную информацию о транзакции перевода денег с одной карты на другую")
public class TransactionTransferDTO {
    @NotNull(message = "from card number is null")
    @Card
    private String cardNumberFrom;
    @NotNull(message = "to card number is null")
    @Card
    private String cardNumberTo;
    @Min(value = 10, message = "amount is too low")
    @Max(value = 1_000_000, message = "amount is too high")
    private int amount;

    public String getCardNumberFrom() {
        return cardNumberFrom;
    }

    public void setCardNumberFrom(String cardNumberFrom) {
        this.cardNumberFrom = cardNumberFrom;
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
        TransactionTransferDTO that = (TransactionTransferDTO) o;
        return amount == that.amount && Objects.equals(cardNumberFrom, that.cardNumberFrom) && Objects.equals(cardNumberTo, that.cardNumberTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumberFrom, cardNumberTo, amount);
    }

}
