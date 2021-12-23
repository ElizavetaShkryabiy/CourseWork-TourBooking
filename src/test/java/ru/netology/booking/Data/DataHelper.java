package ru.netology.booking.Data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.util.Locale;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
        private int dateMonth;
        private String dateYear;
        private String owner;
        private int cvc;
    }

    public static CardInfo getApprovedCardInfo() {
        return new CardInfo("1111222233334444", 12, "24", "Vasya Pupkin", 123);
    }

    public static CardInfo getDeclinedCardInfo() {
        return new CardInfo("5555666677778888", 12, "24", "Vask Pup", 123);
    }

    public static CardInfo getRandomCard(){
        Faker faker = new Faker();
        int correctDateMonth = LocalDate.now().getMonthValue();
        String correctDateYear = String.valueOf(LocalDate.now().plusYears(2).getYear()).substring(2);
        String randomCardNumber = faker.finance().creditCard();
        int randomCvc = Integer.parseInt(faker.numerify("###"));
        String ownerLat = faker.name().fullName().toUpperCase(Locale.forLanguageTag("eng"));
        return new CardInfo(randomCardNumber, correctDateMonth, correctDateYear,ownerLat,randomCvc);
    }
    public static void getInvalidCardInfo(){

    }


}

