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
        private String status;
    }
//
//    @SneakyThrows
//    public static SuccessInfo checkInfo() {
//
//    }
}
