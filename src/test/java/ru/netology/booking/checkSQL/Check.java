package ru.netology.booking.checkSQL;

import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;

public final class Check {
    public Check() {
    }

    static final String DB_URL = "jdbc:mysql://localhost:3306/booking";
    static final String USER = "user";
    static final String PASS = "pass";

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class StatusResponse {
        private String status;
    }

    @SneakyThrows
    public static String checkStatus() {
        var runner = new QueryRunner();
        var status = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (var conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
           var statusCheck=runner.query(conn, status, new BeanHandler<>(StatusResponse.class));
            return statusCheck.getStatus();
        }
    }

}
