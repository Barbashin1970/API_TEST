import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*; // дополнительный статический импорт нужен, чтобы использовать given(), get() и then()

public class ApiGetTest {
    // аннотация Before показывает, что метод будет выполняться перед каждым тестовым методом
    @Before
    public void setUp() {
        // повторяющуюся для разных ручек часть URL лучше записать в переменную в методе Before
        // если в классе будет несколько тестов, указывать её придётся только один раз
        RestAssured.baseURI = "https://qa-mesto.praktikum-services.ru";
    }

    // создаём метод автотеста
    // сначала выставим значения имени и вида деятельности для следующих далее тестов

    @Test
    public void updateProfileAndCheckStatusCodeWithoutFile() {
        String json = "{ \"name\": \"Аристарх Сократович\", \"about\": \"Тестировщик\"}";
        // запиши файл в файловую переменную
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Mzk3ZmU1OTgwODNjMzAwNDIzM2Q4MzQiLCJpYXQiOjE2NzUwODU1MTgsImV4cCI6MTY3NTY5MDMxOH0.j5y4S171dcJj4_lCA0tM7WYn6qpaCON_w7Ad-xxcTQc")
                        .and()
                        .body(json)// заполни body
                        .when()
                        .patch("/api/users/me"); // отправь запрос на ручку
        response.then().assertThat().statusCode(200).and()
                .body("data.name", equalTo("Аристарх Сократович"));
        // проверь статус ответа
        // проверь поле name
    }

    @Test
    public void getMyInfoStatusCode() {
        // метод given() помогает сформировать запрос
        given()
                // указываем протокол и данные авторизации
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Mzk3ZmU1OTgwODNjMzAwNDIzM2Q4MzQiLCJpYXQiOjE2NzUwODU1MTgsImV4cCI6MTY3NTY5MDMxOH0.j5y4S171dcJj4_lCA0tM7WYn6qpaCON_w7Ad-xxcTQc")
                // отправляем GET-запрос с помощью метода get, недостающую часть URL (ручку) передаём в него в качестве параметра
                .get("/api/users/me")
                // проверяем, что статус-код ответа равен 200
                .then().statusCode(200);
    }


    @Test
    public void checkUserName() {
        given()        // given() поможет создать запрос;
                        // auth().oauth2() нужен для авторизации в приложении
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Mzk3ZmU1OTgwODNjMzAwNDIzM2Q4MzQiLCJpYXQiOjE2NzUwODU1MTgsImV4cCI6MTY3NTY5MDMxOH0.j5y4S171dcJj4_lCA0tM7WYn6qpaCON_w7Ad-xxcTQc")
                         // get() указывает тип запроса, в качестве параметра нужно передать ручку.
                .get("/api/users/me")
                     // Работа с ответом начинается с метода then():
                .then().assertThat().body("data.name", equalTo("Аристарх Сократович"));
    }

    @Test
    public void checkUserNameAndPrintResponseBody() {

        // отправляет запрос и сохраняет ответ в переменную response, экзмепляр класса Response
        Response response = given().auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Mzk3ZmU1OTgwODNjMzAwNDIzM2Q4MzQiLCJpYXQiOjE2NzUwODQ0MzcsImV4cCI6MTY3NTY4OTIzN30.XPu7dLdd0jLxGgtjNGUCaHHKJBgHxdM4WTjvy5JfLrg").get("/api/users/me");
        // проверяет, что в теле ответа ключу name соответствует нужное имя пользователя
        response.then().assertThat().body("data.name",equalTo("Аристарх Сократович"));
        // выводит тело ответа на экран
        System.out.println(response.body().asString());

    }

    @Test
    public void checkCardsStatusCode() {
        // проверяем статус-код ответа на запрос «Получение всех карточек»
        given()
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Mzk3ZmU1OTgwODNjMzAwNDIzM2Q4MzQiLCJpYXQiOjE2NzUwODU1MTgsImV4cCI6MTY3NTY5MDMxOH0.j5y4S171dcJj4_lCA0tM7WYn6qpaCON_w7Ad-xxcTQc")
                .get("/api/cards")
                .then().statusCode(200);
    }

    @Test
    public void checkUserActivityAndPrintResponseBody() {

        // отправляет запрос и сохраняет ответ в переменную response, экзмепляр класса Response
        Response response = given().auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Mzk3ZmU1OTgwODNjMzAwNDIzM2Q4MzQiLCJpYXQiOjE2NzUwODQ0MzcsImV4cCI6MTY3NTY4OTIzN30.XPu7dLdd0jLxGgtjNGUCaHHKJBgHxdM4WTjvy5JfLrg").get("/api/users/me");
        response.then().assertThat().body("data.about",equalTo("Тестировщик"));
        // проверяет, что в теле ответа ключу about соответствует нужное занятие
        // выводит тело ответа на экран
        System.out.println(response.body().asString());

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