package ru.netology.booking.checkSQL;

import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.booking.Data.DataHelper;

import java.sql.DriverManager;

public class Check {
    static final String DB_URL = "jdbc:mysql://localhost:3306/app";
    static final String USER = "app";
    static final String PASS = "pass";

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class SuccessInfo {
        private String code;
    }

    @SneakyThrows
    public static SuccessInfo checkInfo(DataHelper.CardInfo cardInfo) {
        var runner = new QueryRunner();
        var vCode = "SELECT code FROM auth_codes INNER JOIN users ON auth_codes.user_id = users.id" +
                " WHERE users.login = ? ORDER BY auth_codes.created DESC LIMIT 1";
        try (var conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            var authCode = runner.query(conn, vCode, new BeanHandler<>(SuccessInfo.class), cardInfo.cardNumber);
            return authCode;
        }
    }
}
