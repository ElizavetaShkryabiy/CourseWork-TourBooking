package ru.netology.booking.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.var;

import java.time.LocalDate;
import java.util.Locale;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String dateMonth;
        private String dateYear;
        private String owner;
        private String cvc;
    }

    public static CardInfo getApprovedCardInfo() {
        return new CardInfo("1111222233334444", "12", "24", "Vasya Pupkin", "123");
    }

    public static CardInfo getDeclinedCardInfo() {
        return new CardInfo("5555666677778888", "12", "24", "Vask Pup", "123");
    }

    public static CardInfo getRandomCard() {
        Faker faker = new Faker();
        var correctDateMonth = String.valueOf(LocalDate.now().getMonthValue());
        String correctDateYear = String.valueOf(LocalDate.now().plusYears(2).getYear()).substring(2);
        String randomCardNumber = faker.finance().creditCard();
        var randomCvc = faker.numerify("###");
        String ownerLat = faker.name().fullName().toUpperCase(Locale.forLanguageTag("eng"));
        return new CardInfo(randomCardNumber, correctDateMonth, correctDateYear, ownerLat, randomCvc);
    }

    @Value
    public static class Date {
        String month;
        String year;
    }

    public static Date getDateInRecentPast() {
        String previousMonth = String.valueOf(LocalDate.now().minusMonths(1).getMonthValue());
        String thisYear = String.valueOf(LocalDate.now()).substring(2);
        return new Date(previousMonth, thisYear);
    }

    public static Date getDateInAncientPast() {
        String previousMonth = String.valueOf(LocalDate.now().minusMonths(1).getMonthValue());
        String pastDateYear = String.valueOf(LocalDate.now().minusYears(1)).substring(2);
        return new Date(previousMonth, pastDateYear);
    }
    public static String getNotExistingMonth() {
        Faker faker = new Faker();
        return String.valueOf(faker.number().numberBetween(13, 100));
    }

    public static Date getTooLongDate() {
        Faker faker = new Faker();
        String tooLongMonth = String.valueOf(faker.number().numberBetween(100,129));
        String tooLongYear = String.valueOf(faker.number().numberBetween(220,259));
        return new Date(tooLongMonth,tooLongYear);
    }

    public static Date getDateInFuture() {
        return new Date(String.valueOf(LocalDate.now().getMonthValue()), String.valueOf(LocalDate.now().plusYears(65).getYear()).substring(2));
    }

    public static String getShortCardNumber() {
        Faker faker = new Faker();
        return faker.finance().creditCard().substring(0, 14);
    }

    public static String getTooLongCardNumber() {
        Faker faker = new Faker();
        return faker.number().digits(17);
    }

    public static String getShortCVC() {
        Faker faker = new Faker();
        return String.valueOf(Integer.parseInt(faker.number().digits(2)));
    }

    public static String getTooLongCVC() {
        Faker faker = new Faker();
        return String.valueOf(Integer.parseInt(faker.number().digits(4)));
    }

    public static String getRuLetters() {
        Faker fakerRu = new Faker(Locale.forLanguageTag("ru"));
        return fakerRu.name().fullName();
    }
    public static String getLatLetters() {
        Faker faker = new Faker();
        return faker.name().fullName().toUpperCase(Locale.forLanguageTag("eng"));
    }

    public static String getSymbols() {
        String lettersSymbols = "*^%%#%$^(*&^&^%$%^#";
        return lettersSymbols;
    }


}

