package com.example.e_commercekotlin.data.model;

public class CardModel {
    CardType type;
    String cardNumber;

     public enum CardType {
        MASTER_CARD,
        VISA
    }

    public CardModel(CardType type, String cardNumber) {
        this.type = type;
        this.cardNumber = cardNumber;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
