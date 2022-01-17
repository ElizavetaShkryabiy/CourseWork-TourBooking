package ru.netology.booking.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BankServicePage {
    SelenideElement form = $(".form");
    private SelenideElement cardNumberField = form.$$(".input__control")
            .findBy(Condition.attribute("placeholder", "0000 0000 0000 0000"));
    private SelenideElement dateMonthField = form.$$(".input__control")
            .findBy(Condition.attribute("placeholder", "08"));
    private SelenideElement dateYearField = form.$$(".input__control")
            .findBy(Condition.attribute("placeholder", "22"));
    private SelenideElement ownerField = $(byText("Владелец")).parent().$("input");
    private SelenideElement cvcField = form.$$(".input__control")
            .findBy(Condition.attribute("placeholder", "999"));
    private SelenideElement orderButton = $$(".button").findBy(Condition.exactText("Продолжить"));
    private SelenideElement notification = $(".notification").shouldBe(appear, Duration.ofSeconds(20));
    private SelenideElement errorLine = $(".input__sub").shouldBe(appear, Duration.ofSeconds(10));


    public void order(String number, String month, String year, String ownerName, String cvcCode) {
        cardNumberField.setValue(number);
        dateMonthField.setValue(month);
        dateYearField.setValue(year);
        ownerField.setValue(ownerName);
        cvcField.setValue(cvcCode);
        orderButton.click();
    }

    public void notificationOk() {
        notification.shouldHave(text("Операция одобрена банком"));
    }


    public void notificationDeclinedOrder() {
        notification
                .shouldHave(text("Ошибка! Банк отказал в проведении операции."))
                .shouldNotHave(text("Операция одобрена банком"));
    }

    public void notificationInvalidCard() {
        notification
                .shouldHave(text("Ошибка! Банк отказал в проведении операции."))
                .shouldNotHave(text("Операция одобрена банком"));
    }

    public void notificationInvalidDataInCardNumberField() {
        errorLine.shouldHave(text("Неверный формат"));
    }

    public void notificationInvalidDataInMonthField() {
        errorLine.shouldHave(text("Неверный формат"));
    }

    public void notificationInvalidDataInCVCField() {
        errorLine.shouldHave(text("Неверный формат"));
    }

    public void notificationInvalidDataInOwnerField() {
        errorLine.shouldHave(text("Неверный формат"));
    }


    public void notificationInvalidDate() {
        errorLine.shouldHave(text("Неверно указан срок действия карты"));

    }

    public void notificationOverdueCardDate() {
        errorLine.shouldHave(text("Истёк срок действия карты"));
    }


    public void emptyCardNumberFieldNotification() {
        errorLine.shouldHave(text("Поле обязательно для заполнения"));

    }

    public void emptyMonthFieldNotification() {
        errorLine.shouldHave(text("Поле обязательно для заполнения"));

    }

    public void emptyYearFieldNotification() {
        errorLine.shouldHave(text("Поле обязательно для заполнения"));

    }

    public void emptyCVCFieldNotification() {
        errorLine.shouldHave(text("Поле обязательно для заполнения"));

    }

    public void emptyOwnerFieldNotification() {
        errorLine.shouldBe(appear, Duration.ofSeconds(10))
                .shouldNotHave(text("Поле обязательно для заполнения"));
    }


}
