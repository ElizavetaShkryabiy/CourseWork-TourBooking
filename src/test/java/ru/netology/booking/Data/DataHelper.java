package ru.netology.booking.Data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String date;
        private int cvc;
    }

    public static CardInfo getApprovedCardInfo() {
        return new CardInfo("1111222233334444", "20.12.24", 123);
    }

    public static CardInfo getDeclinedCardInfo() {
        return new CardInfo("5555666677778888", "20.12.24", 123);
    }




}

