package ru.netology.booking.checkSQL;

import static io.restassured.RestAssured.given;

public class Check {
    public Check(){}
    public void checkAllOk() {
        given()
                .baseUri("http://185.119.57.197:9999")
                .when()
                .get("/payment")
                .then()
                .statusCode(200)
                .statusLine("APPROVED")
        ;
    }

    public void checkCardDeclined() {
        given()
                .baseUri("http://185.119.57.197:9999")
                .when()
                .get("/payment")
                .then()
                .statusCode(200)
                .statusLine("DECLINED")
        ;
    }

    public void checkInvalidCard() {
        given()
                .baseUri("http://185.119.57.197:9999")
                .when()
                .get("/payment")
                .then()
                .statusCode(500)
        ;
    }


}
