import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ApiClassTest {

    @Before
    public void setUp() {
        // повторяющуюся для разных ручек часть URL лучше записать в переменную в методе Before
        // если в классе будет несколько тестов, указывать её придётся только один раз
        RestAssured.baseURI = "https://qa-mesto.praktikum-services.ru";
        String jsonOld = "{ \"name\": \"Аристарх Сократович\", \"about\": \"Тестировщик\"}";
        // запиши файл в файловую переменную
        given()
                .header("Content-type", "application/json")
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Mzk3ZmU1OTgwODNjMzAwNDIzM2Q4MzQiLCJpYXQiOjE2NzUwODU1MTgsImV4cCI6MTY3NTY5MDMxOH0.j5y4S171dcJj4_lCA0tM7WYn6qpaCON_w7Ad-xxcTQc")
                .and()
                .body(jsonOld)// заполни body
                .when()
                .patch("/api/users/me");


    }

    @Test
    public void checkUserNameWithClassUser() {
        User user = given()
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Mzk3ZmU1OTgwODNjMzAwNDIzM2Q4MzQiLCJpYXQiOjE2NzUwODU1MTgsImV4cCI6MTY3NTY5MDMxOH0.j5y4S171dcJj4_lCA0tM7WYn6qpaCON_w7Ad-xxcTQc")
                .get("/api/users/me")
                .body().as(User.class); // напиши код для десериализации ответа в объект типа User
        // выбери подходящий assert
        MatcherAssert.assertThat(user, notNullValue());
    }
}
