package ru.netology.booking.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.booking.checkSQL.Check;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BankServicePage {
    SelenideElement form = $(".form");
    private SelenideElement cardNumber = form.$$(".input__control")
            .findBy(Condition.attribute("placeholder","0000 0000 0000 0000"));
    private SelenideElement dateMonth = form.$$(".input__control")
            .findBy(Condition.attribute("placeholder","08"));
    private SelenideElement dateYear = form.$$(".input__control")
            .findBy(Condition.attribute("placeholder","22"));
    private SelenideElement owner = form.$(".input__control", 3);
    private SelenideElement cvc = form.$$(".input__control")
            .findBy(Condition.attribute("placeholder","999"));
    private SelenideElement orderButton = $$(".button").findBy(Condition.exactText("Продолжить"));
    Check checkInfo = new Check();

    public void order(String number, String month, String year, String ownerName, String cvcCode) {
        cardNumber.setValue(number);
        dateMonth.setValue(month);
        dateYear.setValue(year);
        owner.setValue(ownerName);
        cvc.setValue(cvcCode);
        orderButton.click();
    }

    public void notificationOk() {
        $(".notification").shouldBe(appear, Duration.ofSeconds(20))
                .shouldHave(text("Операция одобрена банком"));
        checkInfo.checkAllOk();
    }


    public void notificationDeclinedOrder() {
        $(".notification").shouldBe(appear, Duration.ofSeconds(30))
                .shouldHave(text("Ошибка! Банк отказал в проведении операции."))
                .shouldNotHave(text("Операция одобрена банком"));
        checkInfo.checkCardDeclined();
    }

    public void notificationInvalidCard() {
        $(".notification").shouldBe(appear, Duration.ofSeconds(30))
                .shouldHave(text("Ошибка! Банк отказал в проведении операции."))
                .shouldNotHave(text("Операция одобрена банком"));
    }

    public void notificationInvalidData() {
        $(".input__sub").shouldBe(appear, Duration.ofSeconds(10))
                .shouldHave(text("Неверный формат"));
    }


    public void notificationInvalidDate() {
        $(".input__sub").shouldBe(appear, Duration.ofSeconds(10))
                .shouldHave(text("Неверно указан срок действия карты"));

    }

    public void notificationOverdueCardDate() {
        $(".input__sub").shouldBe(appear, Duration.ofSeconds(10))
                .shouldHave(text("Истёк срок действия карты"));
    }


    public void emptyFieldNotification() {
        $(".input__sub").shouldBe(appear, Duration.ofSeconds(10))
                .shouldHave(text("Поле обязательно для заполнения"));

    }

    public void emptyOwnerFieldNotification() {
        $(".input__sub").shouldBe(appear, Duration.ofSeconds(10))
                .shouldNotHave(text("Поле обязательно для заполнения"));
    }
}
