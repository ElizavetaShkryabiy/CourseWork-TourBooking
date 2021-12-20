package ru.netology.booking.Pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class Dashboard {
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    public BankServicePage depositClick() {
        loginButton.click();
        return new BankServicePage();
    }
}
