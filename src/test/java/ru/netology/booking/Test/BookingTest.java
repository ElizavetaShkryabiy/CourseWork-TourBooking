package ru.netology.booking.Test;

import org.junit.jupiter.api.Test;
import ru.netology.booking.checkSQL.Check;
import ru.netology.booking.data.DataHelper;
import ru.netology.booking.pages.Dashboard;

import static com.codeborne.selenide.Selenide.open;

public class BookingTest {

    @Test
    void shouldOrderSuccessfully() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.notificationOk();
        Check.checkAllOk();
    }

    @Test
    void shouldDeclineOrderForInternalReasons() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getDeclinedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.notificationDeclinedOrder();
        Check.checkCardDeclined();
    }

    @Test
    void shouldDeclineOrderForInvalidCardNumber() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getRandomCard();
        servicePage.order(info.getCardNumber(), info.getDateMonth(),info.getDateYear(),info.getOwner(),info.getCvc());
        servicePage.notificationInvalidCard();

    }

    @Test
    void shouldGiveErrorForShortCardNumber() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getRandomCard();
        servicePage.order(DataHelper.getShortCardNumber(), info.getDateMonth(),info.getDateYear(),info.getOwner(),info.getCvc());
        servicePage.notificationInvalidData();
    }

    @Test
    void shouldNotFillCardNumberFieldWithLatinLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getRandomCard();
        servicePage.order(DataHelper.getLatLetters(), info.getDateMonth(),info.getDateYear(),info.getOwner(),info.getCvc());
        servicePage.emptyFieldNotification();
    }

    @Test
    void shouldNotFillCardNumberFieldWithRussianLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getRandomCard();
        servicePage.order(DataHelper.getRuLetters(), info.getDateMonth(),info.getDateYear(),info.getOwner(),info.getCvc());
        servicePage.emptyFieldNotification();
    }

    @Test
    void shouldNotFillCardNumberFieldWithSymbols() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getRandomCard();
        servicePage.order(DataHelper.getSymbols(), info.getDateMonth(),info.getDateYear(),info.getOwner(),info.getCvc());
        servicePage.emptyFieldNotification();
    }

    @Test
    void shouldGiveErrorForEmptyCardNumberField() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getRandomCard();
        servicePage.order("", info.getDateMonth(),info.getDateYear(),info.getOwner(),info.getCvc());
        servicePage.emptyFieldNotification();
    }

    @Test
    void shouldDeclineOrderForTooLongCardNumberNotBeingVerified() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getRandomCard();
        servicePage.order(DataHelper.getTooLongCardNumber(), info.getDateMonth(),info.getDateYear(),info.getOwner(),info.getCvc());
        servicePage.notificationInvalidCard();
    }

    @Test
    void shouldDeclineOrderForInvalidDateMonthInPast() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        DataHelper.Date date = DataHelper.getDateInRecentPast();
        servicePage.order(info.getCardNumber(), date.getMonth(), date.getYear(),info.getOwner(),info.getCvc());
        servicePage.notificationInvalidDate();
    }

    @Test
    void shouldDeclineOrderForInvalidDateYearInPast() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        DataHelper.Date date = DataHelper.getDateInAncientPast();
        servicePage.order(info.getCardNumber(), date.getMonth(), date.getYear(),info.getOwner(),info.getCvc());
        servicePage.notificationOverdueCardDate();
    }

    @Test
    void shouldDeclineOrderForInvalidDateYearInTooFarFuture() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        DataHelper.Date date = DataHelper.getDateInFuture();
        servicePage.order(info.getCardNumber(), date.getMonth(), date.getYear(),info.getOwner(),info.getCvc());
        servicePage.notificationInvalidDate();
    }

    @Test
    void shouldNotDeclineOrderForTooLongFormatOfMonthDueToCut() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        DataHelper.Date date = DataHelper.getTooLongDate();
        servicePage.order(info.getCardNumber(), date.getMonth(), info.getDateYear(),info.getOwner(),info.getCvc());
        servicePage.notificationOk();
        Check.checkAllOk();
    }

    @Test
    void shouldNotDeclineOrderForTooLongFormatOfYearDueToCut() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        DataHelper.Date date = DataHelper.getTooLongDate();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), date.getYear(), info.getOwner(),info.getCvc());
        servicePage.notificationOk();
        Check.checkAllOk();
    }

    @Test
    void shouldGiveErrorForZerosInYear() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), "00", info.getOwner(),info.getCvc());
        servicePage.notificationOverdueCardDate();
    }

    @Test
    void shouldGiveErrorForNotExistingMonth() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), DataHelper.getNotExistingMonth(), info.getDateYear(), info.getOwner(),info.getCvc());
        servicePage.notificationInvalidData();
    }

    @Test
    void shouldGiveErrorForZerosInMonth() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), "00", info.getDateYear(), info.getOwner(),info.getCvc());
        servicePage.notificationInvalidData();
    }

    @Test
    void shouldNotFillYearWithLatinLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), DataHelper.getLatLetters(), info.getOwner(),info.getCvc());
        servicePage.emptyFieldNotification();
    }

    @Test
    void shouldNotFillMonthWithLatinLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), DataHelper.getLatLetters(),info.getDateYear(), info.getOwner(),info.getCvc());
        servicePage.emptyFieldNotification();
    }

    @Test
    void shouldNotFillYearWithRussianLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), DataHelper.getRuLetters(), info.getOwner(),info.getCvc());
        servicePage.emptyFieldNotification();
    }

    @Test
    void shouldNotFillMonthWithRussianLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), DataHelper.getRuLetters(), info.getDateYear(), info.getOwner(),info.getCvc());
        servicePage.emptyFieldNotification();
    }

    @Test
    void shouldNotFillYearWithSymbols() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), DataHelper.getSymbols(), info.getOwner(),info.getCvc());
        servicePage.emptyFieldNotification();
    }

    @Test
    void shouldNotFillMonthWithSymbols() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), DataHelper.getSymbols(), info.getDateYear(), info.getOwner(),info.getCvc());
        servicePage.emptyFieldNotification();
    }

    @Test
    void shouldGiveErrorForEmptyMonthField() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), "", info.getDateYear(), info.getOwner(),info.getCvc());
        servicePage.emptyFieldNotification();
    }

    @Test
    void shouldGiveErrorForEmptyYearField() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), "", info.getOwner(),info.getCvc());
        servicePage.emptyFieldNotification();
    }

    @Test
    void shouldGiveErrorForShortCVC() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(),DataHelper.getShortCVC());
        servicePage.notificationInvalidData();
    }

    @Test
    void shouldNotFillCVCWithTooLongNumber() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(),DataHelper.getTooLongCVC());
        servicePage.notificationOk();
        Check.checkAllOk();
    }

    @Test
    void shouldNotFillCVCWithLatinLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(),DataHelper.getLatLetters());
        servicePage.emptyFieldNotification();
    }

    @Test
    void shouldNotFillCVCWithRussianLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(),DataHelper.getRuLetters());
        servicePage.emptyFieldNotification();
    }

    @Test
    void shouldNotFillCVCWithSymbols() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(),DataHelper.getSymbols());
        servicePage.emptyFieldNotification();
    }

    @Test
    void shouldGiveErrorForEmptyCVCField() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(),"");
        servicePage.emptyFieldNotification();
    }


    @Test
    void shouldNotGiveErrorForEmptyOwnerField() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), "",info.getCvc());
        servicePage.emptyOwnerFieldNotification();
    }

    @Test
    void shouldOrderSuccessfullyWithNumbersInOwner() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getCardNumber(), info.getCvc());
        servicePage.notificationOk();
        Check.checkAllOk();
    }

    @Test
    void shouldGiveErrorWhenSymbolsInOwner() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), DataHelper.getSymbols(),info.getCvc());
        servicePage.notificationInvalidData();
    }

    @Test
    void shouldGiveErrorWhenRussianLettersInOwner() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), DataHelper.getRuLetters(),info.getCvc());
        servicePage.notificationInvalidData();
    }

}
