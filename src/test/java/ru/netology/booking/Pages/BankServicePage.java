package ru.netology.booking.Pages;

import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import ru.netology.booking.Data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class BankServicePage {
    private SelenideElement cardNumber = $("[data-test-id=login] input");
    private SelenideElement cvc = $("[data-test-id=password] input");
    private SelenideElement date = $("[data-test-id=password] input");
    private SelenideElement orderButton = $("[data-test-id=action-login]");
    Faker faker = new Faker();


    public Dashboard validLogin(DataHelper.CardInfo info) {
        cardNumber.setValue(info.getCardNumber());
        cvc.setValue(String.valueOf(info.getCvc()));
        date.setValue(info.getDate());
        orderButton.click();
        return new Dashboard();
    }
    public void inValidData() {
        cardNumber.setValue("");
        cvc.setValue("");
        date.setValue("");
        orderButton.click();
        $("[data-test-id=error-notification]").shouldBe(appear, Duration.ofSeconds(7))
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }
}
