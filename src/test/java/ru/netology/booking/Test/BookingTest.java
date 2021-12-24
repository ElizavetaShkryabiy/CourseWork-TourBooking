package ru.netology.booking.Test;

import com.github.javafaker.Faker;
import lombok.var;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.booking.Data.DataHelper;
import ru.netology.booking.Pages.Dashboard;
import java.time.LocalDate;
import java.util.Locale;
import static com.codeborne.selenide.Selenide.open;

public class BookingTest {

    Faker faker = new Faker();
    Faker fakerRu = new Faker(Locale.forLanguageTag("ru"));
    String previousMonth = String.valueOf(LocalDate.now().minusMonths(1).getMonthValue());
    String notExistingMonth = String.valueOf(faker.number().numberBetween(13, 100));
    String pastDateYear = String.valueOf(LocalDate.now().minusYears(1)).substring(2);
    String thisYear = String.valueOf(LocalDate.now()).substring(2);
    String farFutureDateYear = String.valueOf(LocalDate.now().plusYears(65).getYear()).substring(2);
    String randomShortCardNumber = faker.finance().creditCard().substring(0, 14);
    String randomTooLongCardNumber = faker.number().digits(17);
    String randomShortCvc = String.valueOf(Integer.parseInt(faker.number().digits(2)));
    String randomTooLongCvc = String.valueOf(Integer.parseInt(faker.number().digits(4)));
    String lettersRu = fakerRu.name().fullName();
    String lettersSymbols = "*^%%#%$^(*&^&^%$%^#";


    //    @BeforeAll
//    static void setUpAll() {
//        SelenideLogger.addListener("allure", new AllureSelenide());
//    }
//
//    @AfterAll
//    static void tearDownAll() {
//        SelenideLogger.removeListener("allure");
//    }


    @Test
    void shouldOrderSuccessfully() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        servicePage.successfulOrder();
    }

    @Test
    void shouldDeclineOrderForInternalReasons() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        servicePage.declinedOrder();
    }

    @Test
    void shouldDeclineOrderForInvalidCardNumber() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        servicePage.inValidCardNumberOrder(DataHelper.getRandomCard().getCardNumber());
    }

    @Test
    void shouldGiveErrorForShortCardNumber() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        servicePage.inValidCardNumberFormat(randomShortCardNumber);
    }

    @Test
    void shouldNotFillCardNumberFieldWithLatinLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        var number = DataHelper.getApprovedCardInfo().getOwner();
        String month = String.valueOf(DataHelper.getApprovedCardInfo().getDateMonth());
        var year = DataHelper.getApprovedCardInfo().getDateYear();
        var owner = DataHelper.getRandomCard().getOwner();
        String cvc = String.valueOf(DataHelper.getRandomCard().getCvc());
        servicePage.emptyField(number, month, year, owner, cvc);
    }

    @Test
    void shouldNotFillCardNumberFieldWithRussianLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        String month = String.valueOf(DataHelper.getApprovedCardInfo().getDateMonth());
        var year = DataHelper.getApprovedCardInfo().getDateYear();
        var owner = DataHelper.getRandomCard().getOwner();
        String cvc = String.valueOf(DataHelper.getRandomCard().getCvc());
        servicePage.emptyField(lettersRu, month, year, owner, cvc);
    }

    @Test
    void shouldNotFillCardNumberFieldWithSymbols() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        String month = String.valueOf(DataHelper.getApprovedCardInfo().getDateMonth());
        var year = DataHelper.getApprovedCardInfo().getDateYear();
        var owner = DataHelper.getRandomCard().getOwner();
        String cvc = String.valueOf(DataHelper.getRandomCard().getCvc());
        servicePage.emptyField(lettersSymbols, month, year, owner, cvc);
    }

    @Test
    void shouldGiveErrorForEmptyCardNumberField() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        String number = "";
        String month = String.valueOf(DataHelper.getApprovedCardInfo().getDateMonth());
        var year = DataHelper.getApprovedCardInfo().getDateYear();
        var owner = DataHelper.getRandomCard().getOwner();
        String cvc = String.valueOf(DataHelper.getRandomCard().getCvc());
        servicePage.emptyField(number, month, year, owner, cvc);
    }

    @Test
    void shouldDeclineOrderForTooLongCardNumberNotBeingVerified() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        servicePage.inValidCardNumberOrder(randomTooLongCardNumber);
    }

    @Test
    void shouldDeclineOrderForInvalidDateMonthInPast() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        servicePage.inValidDateCardInfo(previousMonth, thisYear);
    }

    @Test
    void shouldDeclineOrderForInvalidDateYearInPast() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        servicePage.overdueCardDate(pastDateYear);
    }

    @Test
    void shouldDeclineOrderForInvalidDateYearInTooFarFuture() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        String month = String.valueOf(DataHelper.getRandomCard().getDateMonth());
        servicePage.inValidDateCardInfo(month, farFutureDateYear);
    }

    @Test
    void shouldDeclineOrderForInvalidDateDueToTooLongFormatOfMonth() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        var year = DataHelper.getRandomCard().getDateYear();
        servicePage.inValidDateCardInfo(randomShortCardNumber, year);
    }

    @Test
    void shouldDeclineOrderForInvalidDateDueToTooLongFormatOfYear() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        String month = String.valueOf(DataHelper.getRandomCard().getDateMonth());
        servicePage.inValidDateCardInfo(month, randomShortCardNumber);
    }

    @Test
    void shouldGiveErrorForZerosInYear() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        String month = String.valueOf(DataHelper.getRandomCard().getDateMonth());
        servicePage.overdueCardDate("00");
    }

    @Test
    void shouldGiveErrorForNotExistingMonth() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        var year = DataHelper.getRandomCard().getDateYear();
        servicePage.inValidDateCardInfo(notExistingMonth, year);
    }

    @Test
    void shouldGiveErrorForZerosInMonth() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        var year = DataHelper.getRandomCard().getDateYear();
        servicePage.inValidDateCardInfo("00", year);
    }

    @Test
    void shouldNotFillYearWithLatinLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        var number = DataHelper.getApprovedCardInfo().getCardNumber();
        var owner = DataHelper.getRandomCard().getOwner();
        String cvc = String.valueOf(DataHelper.getRandomCard().getCvc());
        String month = String.valueOf(DataHelper.getRandomCard().getDateMonth());
        servicePage.emptyField(number, month, DataHelper.getRandomCard().getOwner(), owner, cvc);
    }

    @Test
    void shouldNotFillMonthWithLatinLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        var number = DataHelper.getApprovedCardInfo().getCardNumber();
        var owner = DataHelper.getRandomCard().getOwner();
        String cvc = String.valueOf(DataHelper.getRandomCard().getCvc());
        var year = DataHelper.getRandomCard().getDateYear();
        servicePage.emptyField(number, DataHelper.getRandomCard().getOwner(), year, owner, cvc);
    }

    @Test
    void shouldNotFillYearWithRussianLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        var number = DataHelper.getApprovedCardInfo().getCardNumber();
        var owner = DataHelper.getRandomCard().getOwner();
        String cvc = String.valueOf(DataHelper.getRandomCard().getCvc());
        String month = String.valueOf(DataHelper.getRandomCard().getDateMonth());
        servicePage.emptyField(number, month, lettersRu, owner, cvc);
    }

    @Test
    void shouldNotFillMonthWithRussianLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        var number = DataHelper.getApprovedCardInfo().getCardNumber();
        var owner = DataHelper.getRandomCard().getOwner();
        String cvc = String.valueOf(DataHelper.getRandomCard().getCvc());
        var year = DataHelper.getRandomCard().getDateYear();
        servicePage.emptyField(number, lettersRu, year, owner, cvc);
    }

    @Test
    void shouldNotFillYearWithSymbols() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        var number = DataHelper.getApprovedCardInfo().getCardNumber();
        var owner = DataHelper.getRandomCard().getOwner();
        String cvc = String.valueOf(DataHelper.getRandomCard().getCvc());
        String month = String.valueOf(DataHelper.getRandomCard().getDateMonth());
        servicePage.emptyField(number, month, lettersSymbols, owner, cvc);
    }

    @Test
    void shouldNotFillMonthWithSymbols() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        var number = DataHelper.getApprovedCardInfo().getCardNumber();
        var owner = DataHelper.getRandomCard().getOwner();
        String cvc = String.valueOf(DataHelper.getRandomCard().getCvc());
        var year = DataHelper.getRandomCard().getDateYear();
        servicePage.emptyField(number, lettersSymbols, year, owner, cvc);
    }

    @Test
    void shouldGiveErrorForEmptyMonthField() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        String number = DataHelper.getApprovedCardInfo().getCardNumber();
        String month = "";
        var year = DataHelper.getApprovedCardInfo().getDateYear();
        var owner = DataHelper.getRandomCard().getOwner();
        String cvc = String.valueOf(DataHelper.getRandomCard().getCvc());
        servicePage.emptyField(number, month, year, owner, cvc);
    }

    @Test
    void shouldGiveErrorForEmptyYearField() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        String number = DataHelper.getApprovedCardInfo().getCardNumber();
        String month = String.valueOf(DataHelper.getApprovedCardInfo().getDateMonth());
        String year = "";
        var owner = DataHelper.getRandomCard().getOwner();
        String cvc = String.valueOf(DataHelper.getRandomCard().getCvc());
        servicePage.emptyField(number, month, year, owner, cvc);
    }

    @Test
    void shouldGiveErrorForShortCVC() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        servicePage.inValidCVCFormat(randomShortCvc);
    }

    @Test
    void shouldNotFillCVCWithTooLongNumber() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        servicePage.longCVC(randomTooLongCvc);
    }

    @Test
    void shouldNotFillCVCWithLatinLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        var number = DataHelper.getApprovedCardInfo().getCardNumber();
        var owner = DataHelper.getRandomCard().getOwner();
        var year = DataHelper.getApprovedCardInfo().getDateYear();
        String month = String.valueOf(DataHelper.getRandomCard().getDateMonth());
        servicePage.emptyField(number, month, year, owner, DataHelper.getRandomCard().getOwner());
    }

    @Test
    void shouldNotFillCVCWithRussianLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        String number = DataHelper.getApprovedCardInfo().getCardNumber();
        String month = String.valueOf(DataHelper.getApprovedCardInfo().getDateMonth());
        var year = DataHelper.getApprovedCardInfo().getDateYear();
        var owner = DataHelper.getRandomCard().getOwner();
        servicePage.emptyField(number, month, year, owner, lettersRu);
    }

    @Test
    void shouldNotFillCVCWithSymbols() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        String number = DataHelper.getApprovedCardInfo().getCardNumber();
        String month = String.valueOf(DataHelper.getApprovedCardInfo().getDateMonth());
        var year = DataHelper.getApprovedCardInfo().getDateYear();
        var owner = DataHelper.getRandomCard().getOwner();
        servicePage.emptyField(number, month, year, owner, lettersSymbols);
    }

    @Test
    void shouldGiveErrorForEmptyCVCField() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        String number = DataHelper.getApprovedCardInfo().getCardNumber();
        String month = String.valueOf(DataHelper.getApprovedCardInfo().getDateMonth());
        var year = DataHelper.getApprovedCardInfo().getDateYear();
        var owner = DataHelper.getRandomCard().getOwner();
        String cvc = "";
        servicePage.emptyField(number, month, year, owner, cvc);
    }


    @Test
    void shouldNotGiveErrorForEmptyOwnerField() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        String number = DataHelper.getApprovedCardInfo().getCardNumber();
        String month = String.valueOf(DataHelper.getApprovedCardInfo().getDateMonth());
        var year = DataHelper.getApprovedCardInfo().getDateYear();
        var owner = "";
        String cvc = String.valueOf(DataHelper.getRandomCard().getCvc());
        servicePage.emptyOwnerField(number, month, year, owner, cvc);
    }

    @Test
    void shouldOrderSuccessfullyWithNumbersInOwner() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        var owner = DataHelper.getApprovedCardInfo().getCardNumber();
        servicePage.validOwnerFormat(owner);
    }

    @Test
    void shouldGiveErrorWhenSymbolsInOwner() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        servicePage.inValidOwnerFormat(lettersSymbols);
    }

    @Test
    void shouldGiveErrorWhenRussianLettersInOwner() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        servicePage.inValidOwnerFormat(lettersRu);
    }

}
