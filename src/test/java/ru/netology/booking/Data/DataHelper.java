package ru.netology.booking.Data;

import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;

public class DataHelper {

    private DataHelper() {
    }
    static final String DB_URL = "jdbc:mysql://localhost:3306/app";
    static final String USER = "app";
    static final String PASS = "pass";

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String date;
        private int cvc;
    }

    @SneakyThrows
    public static CardInfo getApprovedCardInfo() {
        return new CardInfo("1111222233334444", "20.12.24", 123);
    }

    @SneakyThrows
    public static CardInfo getDeclinedCardInfo() {
        return new CardInfo("5555666677778888", "20.12.24", 123);
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SuccessInfo {
        private String code;
    }

    @SneakyThrows
    public static SuccessInfo getVerificationCode(CardInfo cardInfo) {
        var runner = new QueryRunner();
        var vCode = "SELECT code FROM auth_codes INNER JOIN users ON auth_codes.user_id = users.id" +
                " WHERE users.login = ? ORDER BY auth_codes.created DESC LIMIT 1";
        try (var conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            var authCode = runner.query(conn, vCode, new BeanHandler<>(SuccessInfo.class), cardInfo.cardNumber);
            return authCode;
        }
    }
}

