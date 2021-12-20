package ru.netology.booking.Test;

import lombok.SneakyThrows;
import lombok.var;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.booking.Data.DataHelper;
import ru.netology.booking.Pages.Dashboard;
import ru.netology.booking.checkSQL.Check;

import static com.codeborne.selenide.Selenide.open;

public class BookingTest {
    //    @BeforeAll
//    static void setUpAll() {
//        SelenideLogger.addListener("allure", new AllureSelenide());
//    }
//
//    @AfterAll
//    static void tearDownAll() {
//        SelenideLogger.removeListener("allure");
//    }
    @SneakyThrows
    @Test
    void shouldOrderSuccessfully() {

        var choosePage = open("http://localhost:9999", Dashboard.class);
        var cardInfo = DataHelper.getApprovedCardInfo();
        var servicePage = choosePage.depositClick();
        servicePage.successfulOrder(cardInfo);
        Check.checkInfo(cardInfo);
    }

}
