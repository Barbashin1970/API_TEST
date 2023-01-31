import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class ApiPostTest {

        // аннотация Before показывает, что метод будет выполняться перед каждым тестовым методом
        @Before
        public void setUp() {
            // повторяющуюся для разных ручек часть URL лучше записать в переменную в методе Before
            // если в классе будет несколько тестов, указывать её придётся только один раз
            RestAssured.baseURI = "https://qa-mesto.praktikum-services.ru";
        }

        // создаём метод автотеста
        @Test
        public void createNewPlaceAndCheckResponse(){
            File json = new File("src/test/resources/newCard.json");
            Response response =
                    given()
                            .header("Content-type", "application/json")
                            .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Mzk3ZmU1OTgwODNjMzAwNDIzM2Q4MzQiLCJpYXQiOjE2NzUwODU1MTgsImV4cCI6MTY3NTY5MDMxOH0.j5y4S171dcJj4_lCA0tM7WYn6qpaCON_w7Ad-xxcTQc")
                            .and()
                            .body(json)
                            .when()
                            .post("/api/cards");
            response.then().assertThat().body("data._id", notNullValue())
                    .and()
                    .statusCode(201);
        }

        // Если JSON небольшой, его можно передать в тело запроса через строковую переменную, а не в файле.
    @Test
    public void createNewPlaceAndCheckResponseWithoutJsonFile(){
        String json = "{\"name\": \"Очень интересное место\", \"link\": \"https://code.s3.yandex.net/qa-automation-engineer/java/files/paid-track/sprint1/photoSelenide.jpg\"}";
        // Если в строке есть кавычки, их нужно экранировать — добавить перед ними символ \
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Mzk3ZmU1OTgwODNjMzAwNDIzM2Q4MzQiLCJpYXQiOjE2NzUwODU1MTgsImV4cCI6MTY3NTY5MDMxOH0.j5y4S171dcJj4_lCA0tM7WYn6qpaCON_w7Ad-xxcTQc")
                        .and()
                        .body(json)
                        .when()
                        .post("/api/cards"); // .patch("/api/cards"); - как вариант
        response.then().assertThat().body("data._id", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    public void updateProfileAndCheckStatusCode() {
        File json = new File("src/test/resources/updateProfile.json"); // запиши файл в файловую переменную
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Mzk3ZmU1OTgwODNjMzAwNDIzM2Q4MzQiLCJpYXQiOjE2NzUwODU1MTgsImV4cCI6MTY3NTY5MDMxOH0.j5y4S171dcJj4_lCA0tM7WYn6qpaCON_w7Ad-xxcTQc")
                        .and()
                        .body(json)
                        .when()
                        .patch("/api/users/me"); // .patch("/api/cards"); - как вариант
        response.then().assertThat().body("data.name", equalTo("Василий Васильев"))
                .and()
                .statusCode(200);
    }

    @Test
    public void updateProfileAndCheckStatusCodeWithoutFile() {
        String json = "{ \"name\": \"Василий Васильев\", \"about\": \"Самый крутой исследователь\"}";
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
                .body("data.name", equalTo("Василий Васильев"));
        // проверь статус ответа
        // проверь поле name
    }

    @Test
    public void createNewPlaceAndCheckResponseWithCard(){
        Card card = new Card("Интересное место", "https://code.s3.yandex.net/qa-automation-engineer/java/files/paid-track/sprint1/photoSelenide.jpg");
        // используем класс карточки Card
        // преобразовать объект в JSON. Этот процесс называется сериализацией.
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Mzk3ZmU1OTgwODNjMzAwNDIzM2Q4MzQiLCJpYXQiOjE2NzUwODU1MTgsImV4cCI6MTY3NTY5MDMxOH0.j5y4S171dcJj4_lCA0tM7WYn6qpaCON_w7Ad-xxcTQc")
                        .and()
                        .body(card)
                        .when()
                        .post("/api/cards");
        response.then().assertThat().body("data._id", notNullValue())
                .and()
                .statusCode(201);
    }

    // передать в тело запроса JSON в виде массива данных.
    // Например, отправить не одну карточку Mesto, а несколько
    // создали пустой список
    //   List<Card> cards = new ArrayList<Card>();
    // добавили элементы
    //    cards.add(new Card("имя1", "линк1"));
    //    cards.add(new Card("имя2", "линк2"));
    //    cards.add(new Card("имя3", "линк3"));
    //
    //                  .body(cards) - обращаемся аналогично и к этому массиву-списку
    //
    @Test
    public void updateProfileAndCheckResponse() {
        Profile profile = new Profile("Василий Васильев", "Самый крутой исследователь");
        // создай объект класса, который соответствует JSON
        Response response =
        given()
                .header("Content-type", "application/json")
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2Mzk3ZmU1OTgwODNjMzAwNDIzM2Q4MzQiLCJpYXQiOjE2NzUwODU1MTgsImV4cCI6MTY3NTY5MDMxOH0.j5y4S171dcJj4_lCA0tM7WYn6qpaCON_w7Ad-xxcTQc")
                .and()
                .body(profile)   // заполни body
                .when()
                .patch("/api/users/me"); // отправь запрос на ручку
        response.then().assertThat().statusCode(200); // проверь статус ответа 200
    }

}