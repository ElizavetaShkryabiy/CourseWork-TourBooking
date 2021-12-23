package ru.netology.booking.Pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class Dashboard {
    private SelenideElement loginButton = $(".button",1);
    public BankServicePage depositClick() {
        loginButton.click();
        return new BankServicePage();
    }
}
