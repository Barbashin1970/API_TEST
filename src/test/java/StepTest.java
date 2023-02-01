import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName; // импорт DisplayName
import io.qameta.allure.Description; // импорт Description
import io.qameta.allure.Step; // импорт Step

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class StepTest {

    String bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Mzk3ZmU1OTgwODNjMzAwNDIzM2Q4MzQiLCJpYXQiOjE2NzUwODU1MTgsImV4cCI6MTY3NTY5MDMxOH0.j5y4S171dcJj4_lCA0tM7WYn6qpaCON_w7Ad-xxcTQc";

    @Before
    public void setUp() {

        RestAssured.baseURI= "https://qa-mesto.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check user name and print response body") // имя теста
    @Description("This is a more complicated test with console output") // описание теста
    public void checkUserNameAndPrintResponseBody() {

        Response response = sendGetRequestUsersMe();
        // отправили запрос и сохранили ответ в переменную response - экземпляр класса Response

        compareUserNameToText(response, "Аристарх Сократович");
        // проверили, что в теле ответа ключу name соответствует нужное имя пользователя

        printResponseBodyToConsole(response); // вывели тело ответа на экран

    }

    // метод для шага "Отправить запрос":
    @Step("Send GET request to /api/users/me")
    public Response sendGetRequestUsersMe(){
        Response response =given().auth().oauth2(bearerToken).get("/api/users/me");
        return response;
    }

    // метод для шага "Сравнить имя пользователя с заданным":
    @Step("Compare user name to something")
    public void compareUserNameToText(Response response, String username){
        response.then().assertThat().body("data.name",equalTo(username));
    }

    // метод для шага "Вывести тело ответа в консоль":
    @Step("Print response body to console")
    public void printResponseBodyToConsole(Response response){
        System.out.println(response.body().asString());
    }

}