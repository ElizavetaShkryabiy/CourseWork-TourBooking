package ru.netology.booking.test;


import org.junit.jupiter.api.Test;
import ru.netology.booking.checkSQL.Check;
import ru.netology.booking.data.DataHelper;
import ru.netology.booking.pages.Dashboard;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingTest {

    @Test
    void shouldOrderSuccessfully() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.notificationOk();
        var status = Check.checkStatus();
        assertEquals("APPROVED", status);
    }

    @Test
    void shouldDeclineOrderForInternalReasons() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getDeclinedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.notificationDeclinedOrder();
        var status = Check.checkStatus();
        assertEquals("DECLINED", status);
    }

    @Test
    void shouldDeclineOrderForInvalidCardNumber() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getRandomCard();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.notificationInvalidCard();

    }

    @Test
    void shouldGiveErrorForShortCardNumber() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getRandomCard();
        servicePage.order(DataHelper.getShortCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.notificationInvalidDataInCardNumberField();
    }

    @Test
    void shouldNotFillCardNumberFieldWithLatinLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getRandomCard();
        servicePage.order(DataHelper.getLatLetters(), info.getDateMonth(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.emptyCardNumberFieldNotification();
    }

    @Test
    void shouldNotFillCardNumberFieldWithRussianLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getRandomCard();
        servicePage.order(DataHelper.getRuLetters(), info.getDateMonth(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.emptyCardNumberFieldNotification();
    }

    @Test
    void shouldNotFillCardNumberFieldWithSymbols() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getRandomCard();
        servicePage.order(DataHelper.getSymbols(), info.getDateMonth(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.emptyCardNumberFieldNotification();
    }

    @Test
    void shouldGiveErrorForEmptyCardNumberField() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getRandomCard();
        servicePage.order("", info.getDateMonth(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.emptyCardNumberFieldNotification();
    }

    @Test
    void shouldDeclineOrderForTooLongCardNumberNotBeingVerified() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getRandomCard();
        servicePage.order(DataHelper.getTooLongCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.notificationInvalidCard();
    }

    @Test
    void shouldDeclineOrderForInvalidDateMonthInPast() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        DataHelper.Date date = DataHelper.getDateInRecentPast();
        servicePage.order(info.getCardNumber(), date.getMonth(), date.getYear(), info.getOwner(), info.getCvc());
        servicePage.notificationInvalidDate();
    }

    @Test
    void shouldDeclineOrderForInvalidDateYearInPast() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        DataHelper.Date date = DataHelper.getDateInAncientPast();
        servicePage.order(info.getCardNumber(), date.getMonth(), date.getYear(), info.getOwner(), info.getCvc());
        servicePage.notificationOverdueCardDate();
    }

    @Test
    void shouldDeclineOrderForInvalidDateYearInTooFarFuture() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        DataHelper.Date date = DataHelper.getDateInFuture();
        servicePage.order(info.getCardNumber(), date.getMonth(), date.getYear(), info.getOwner(), info.getCvc());
        servicePage.notificationInvalidDate();
    }

    @Test
    void shouldNotDeclineOrderForTooLongFormatOfMonthDueToCut() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        DataHelper.Date date = DataHelper.getTooLongDate();
        servicePage.order(info.getCardNumber(), date.getMonth(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.notificationOk();
        var status = Check.checkStatus();
        assertEquals("APPROVED", status);
    }

    @Test
    void shouldNotDeclineOrderForTooLongFormatOfYearDueToCut() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        DataHelper.Date date = DataHelper.getTooLongDate();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), date.getYear(), info.getOwner(), info.getCvc());
        servicePage.notificationOk();
        var status = Check.checkStatus();
        assertEquals("APPROVED", status);
    }

    @Test
    void shouldGiveErrorForZerosInYear() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), "00", info.getOwner(), info.getCvc());
        servicePage.notificationOverdueCardDate();
    }

    @Test
    void shouldGiveErrorForNotExistingMonth() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), DataHelper.getNotExistingMonth(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.notificationInvalidDataInMonthField();
    }

    @Test
    void shouldGiveErrorForZerosInMonth() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), "00", info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.notificationInvalidDataInMonthField();
    }

    @Test
    void shouldNotFillYearWithLatinLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), DataHelper.getLatLetters(), info.getOwner(), info.getCvc());
        servicePage.emptyYearFieldNotification();
    }

    @Test
    void shouldNotFillMonthWithLatinLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), DataHelper.getLatLetters(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.emptyMonthFieldNotification();
    }

    @Test
    void shouldNotFillYearWithRussianLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), DataHelper.getRuLetters(), info.getOwner(), info.getCvc());
        servicePage.emptyYearFieldNotification();
    }

    @Test
    void shouldNotFillMonthWithRussianLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), DataHelper.getRuLetters(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.emptyMonthFieldNotification();
    }

    @Test
    void shouldNotFillYearWithSymbols() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), DataHelper.getSymbols(), info.getOwner(), info.getCvc());
        servicePage.emptyYearFieldNotification();
    }

    @Test
    void shouldNotFillMonthWithSymbols() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), DataHelper.getSymbols(), info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.emptyMonthFieldNotification();
    }

    @Test
    void shouldGiveErrorForEmptyMonthField() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), "", info.getDateYear(), info.getOwner(), info.getCvc());
        servicePage.emptyMonthFieldNotification();
    }

    @Test
    void shouldGiveErrorForEmptyYearField() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), "", info.getOwner(), info.getCvc());
        servicePage.emptyYearFieldNotification();
    }

    @Test
    void shouldGiveErrorForShortCVC() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(), DataHelper.getShortCVC());
        servicePage.notificationInvalidDataInCVCField();
    }

    @Test
    void shouldNotFillCVCWithTooLongNumber() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(), DataHelper.getTooLongCVC());
        servicePage.notificationOk();
        var status = Check.checkStatus();
        assertEquals("APPROVED", status);
    }

    @Test
    void shouldNotFillCVCWithLatinLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(), DataHelper.getLatLetters());
        servicePage.emptyCVCFieldNotification();
    }

    @Test
    void shouldNotFillCVCWithRussianLetters() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(), DataHelper.getRuLetters());
        servicePage.emptyCVCFieldNotification();
    }

    @Test
    void shouldNotFillCVCWithSymbols() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(), DataHelper.getSymbols());
        servicePage.emptyCVCFieldNotification();
    }

    @Test
    void shouldGiveErrorForEmptyCVCField() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getOwner(), "");
        servicePage.emptyCVCFieldNotification();
    }


    @Test
    void shouldGiveErrorForEmptyOwnerField() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), "", info.getCvc());
        servicePage.emptyOwnerFieldNotification();
    }

    @Test
    void shouldOrderSuccessfullyWithNumbersInOwner() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), info.getCardNumber(), info.getCvc());
        servicePage.notificationOk();
        var status = Check.checkStatus();
        assertEquals("APPROVED", status);
    }

    @Test
    void shouldGiveErrorWhenSymbolsInOwner() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), DataHelper.getSymbols(), info.getCvc());
        servicePage.notificationInvalidDataInOwnerField();
    }

    @Test
    void shouldGiveErrorWhenRussianLettersInOwner() {
        var choosePage = open("http://localhost:8080", Dashboard.class);
        var servicePage = choosePage.depositClick();
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        servicePage.order(info.getCardNumber(), info.getDateMonth(), info.getDateYear(), DataHelper.getRuLetters(), info.getCvc());
        servicePage.notificationInvalidDataInOwnerField();
    }

}
