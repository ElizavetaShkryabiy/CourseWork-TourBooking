package ru.netology.booking.Pages;

import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import ru.netology.booking.Data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class BankServicePage {
    private SelenideElement cardNumber = $(".input__control", 0);
    private SelenideElement dateMonth = $(".input__control", 1);
    private SelenideElement dateYear = $(".input__control", 2);
    private SelenideElement owner = $(".input__control", 3);
    private SelenideElement cvc = $(".input__control", 4);
    private SelenideElement orderButton = $(".button", 2);
    Faker faker = new Faker();


    public void successfulOrder() {
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        cardNumber.setValue(info.getCardNumber());
        dateMonth.setValue(String.valueOf(info.getDateMonth()));
        dateYear.setValue(String.valueOf(info.getDateYear()));
        owner.setValue(info.getOwner());
        cvc.setValue(String.valueOf(info.getCvc()));
        orderButton.click();
        $(".notification").shouldBe(appear, Duration.ofSeconds(15))
                .shouldHave(text("Операция одобрена банком"));
    }

    public void declinedOrder() {
        DataHelper.CardInfo info = DataHelper.getDeclinedCardInfo();
        cardNumber.setValue(info.getCardNumber());
        dateMonth.setValue(String.valueOf(info.getDateMonth()));
        dateYear.setValue(String.valueOf(info.getDateYear()));
        owner.setValue(info.getOwner());
        cvc.setValue(String.valueOf(info.getCvc()));
        orderButton.click();
        $(".notification").shouldBe(appear, Duration.ofSeconds(30))
                .shouldHave(text("Ошибка! Банк отказал в проведении операции."))
                .shouldNotHave(text("Операция одобрена банком"));
    }

    public void inValidCardNumberOrder(String number) {
        cardNumber.setValue(number);
        cvc.setValue(String.valueOf(DataHelper.getRandomCard().getCvc()));
        dateMonth.setValue(String.valueOf(DataHelper.getRandomCard().getDateMonth()));
        dateYear.setValue(DataHelper.getRandomCard().getDateYear());
        owner.setValue(DataHelper.getRandomCard().getOwner());
        orderButton.click();
        $(".notification").shouldBe(appear, Duration.ofSeconds(30))
                .shouldHave(text("Ошибка! Банк отказал в проведении операции."))
                .shouldNotHave(text("Операция одобрена банком"));

    }

    public void inValidCardNumberFormat(String number) {
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        cardNumber.setValue(number);
        dateMonth.setValue(String.valueOf(DataHelper.getRandomCard().getDateMonth()));
        dateYear.setValue(DataHelper.getRandomCard().getDateYear());
        owner.setValue(info.getOwner());
        cvc.setValue(String.valueOf(info.getCvc()));
        orderButton.click();
        $(".input__sub").shouldBe(appear, Duration.ofSeconds(10))
                .shouldHave(text("Неверный формат"));

    }


    public void inValidDateCardInfo(String month, String year) {
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        cardNumber.setValue(info.getCardNumber());
        dateMonth.setValue(month);
        dateYear.setValue(year);
        owner.setValue(info.getOwner());
        cvc.setValue(String.valueOf(info.getCvc()));
        orderButton.click();
        $(".input__sub").shouldBe(appear, Duration.ofSeconds(10))
                .shouldHave(text("Неверно указан срок действия карты"));

    }

    public void overdueCardDate(String year) {
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        cardNumber.setValue(info.getCardNumber());
        dateMonth.setValue(String.valueOf(DataHelper.getRandomCard().getDateMonth()));
        dateYear.setValue(year);
        owner.setValue(info.getOwner());
        cvc.setValue(String.valueOf(info.getCvc()));
        orderButton.click();
        $(".input__sub").shouldBe(appear, Duration.ofSeconds(10))
                .shouldHave(text("Истёк срок действия карты"));

    }

    public void inValidCVCFormat(String cvcNumber) {
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        cardNumber.setValue(info.getCardNumber());
        dateMonth.setValue(String.valueOf(DataHelper.getRandomCard().getDateMonth()));
        dateYear.setValue(DataHelper.getRandomCard().getDateYear());
        owner.setValue(info.getOwner());
        cvc.setValue(cvcNumber);
        orderButton.click();
        $(".input__sub").shouldBe(appear, Duration.ofSeconds(10))
                .shouldHave(text("Неверный формат"));

    }

    public void longCVC(String cvcNumber) {
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        cardNumber.setValue(info.getCardNumber());
        dateMonth.setValue(String.valueOf(DataHelper.getRandomCard().getDateMonth()));
        dateYear.setValue(DataHelper.getRandomCard().getDateYear());
        owner.setValue(info.getOwner());
        cvc.setValue(cvcNumber);
        orderButton.click();
        $(".notification").shouldBe(appear, Duration.ofSeconds(15))
                .shouldHave(text("Операция одобрена банком"));
    }

    public void emptyField(String number, String month, String year, String ownerName, String cvcNumber) {
        cardNumber.setValue(number);
        dateMonth.setValue(month);
        dateYear.setValue(year);
        owner.setValue(ownerName);
        cvc.setValue(cvcNumber);
        orderButton.click();
        $(".input__sub").shouldBe(appear, Duration.ofSeconds(10))
                .shouldHave(text("Поле обязательно для заполнения"));

    }

    public void emptyOwnerField(String number, String month, String year, String ownerName, String cvcNumber) {
        cardNumber.setValue(number);
        dateMonth.setValue(month);
        dateYear.setValue(year);
        owner.setValue(ownerName);
        cvc.setValue(cvcNumber);
        orderButton.click();
        $(".input__sub").shouldBe(appear, Duration.ofSeconds(10))
                .shouldNotHave(text("Поле обязательно для заполнения"));

    }

    public void inValidOwnerFormat(String ownerName) {
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        cardNumber.setValue(info.getCardNumber());
        dateMonth.setValue(String.valueOf(DataHelper.getRandomCard().getDateMonth()));
        dateYear.setValue(DataHelper.getRandomCard().getDateYear());
        owner.setValue(ownerName);
        cvc.setValue(String.valueOf(info.getCvc()));
        orderButton.click();
        $(".input__sub").shouldBe(appear, Duration.ofSeconds(10))
                .shouldHave(text("Неверный формат"));
    }

    public void validOwnerFormat(String ownerName) {
        DataHelper.CardInfo info = DataHelper.getApprovedCardInfo();
        cardNumber.setValue(info.getCardNumber());
        dateMonth.setValue(String.valueOf(DataHelper.getRandomCard().getDateMonth()));
        dateYear.setValue(DataHelper.getRandomCard().getDateYear());
        owner.setValue(ownerName);
        cvc.setValue(String.valueOf(info.getCvc()));
        orderButton.click();
        $(".notification").shouldBe(appear, Duration.ofSeconds(15))
                .shouldHave(text("Операция одобрена банком"));
    }
}
