package ru.netology.booking.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Dashboard {
    private SelenideElement loginButton = $$(".button").findBy(Condition.exactText("Купить"));
    public BankServicePage depositClick() {
        loginButton.click();
        return new BankServicePage();
    }
}
