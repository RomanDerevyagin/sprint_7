import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Courier;
import org.example.CourierWithoutField;
import org.example.Login;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.education-services.ru";
    }

    @Test
    @DisplayName("Успешное создание курьера")
    void createCourier() {
        Response response = sendPostCourier();
        compareRequest(response);
    }

    @Step("Отправка POST-запроса на создание курьера")
    public Response sendPostCourier() {
        Courier courier = new Courier("testik", "12345", "kek");
        return given()
                .header("Content-Type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
    }

    @Step("Проверка: статус 201 и ok: true")
    public void compareRequest(Response response) {
        response.then()
                .body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }
    @Test
    @DisplayName("Создание дубликата курьера возвращает 409")
    void createDuplicateCourier() {
        Response response = sendDuplicatePostCourier();
        compareDuplicateRequest(response);
    }

    @Step("Повторная отправка POST-запроса с тем же логином")
    public Response sendDuplicatePostCourier() {
        Courier courier = new Courier("qwe", "12345", "kek");
        return given()
                .header("Content-Type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
    }

    @Step("Проверка: статус 409")
    public void compareDuplicateRequest(Response response) {
        response.then()
                .statusCode(409);
    }
    @Test
    @DisplayName("Создание курьера без обязательного поля")
    void createCourierWithoutField() {
        Response response = sendPostCourierWithoutField();
        compareRequestWithoutField(response);
    }

    @Step("Отправка POST-запроса без обязательного поля")
    public Response sendPostCourierWithoutField() {
        CourierWithoutField courier = new CourierWithoutField("lol", "kek");
        return given()
                .header("Content-Type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
    }

    @Step("Проверка: статус 400")
    public void compareRequestWithoutField(Response response) {
        response.then()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }
    @AfterEach
    void loginCourier() {
        Response response = sendPostLogin();
        if (response.statusCode() == 200) {
            deleteCourier(response);
        }
    }
    public Response sendPostLogin() {
        Login courier = new Login("testik", "12345");
        return given()
                .header("Content-Type", "application/json")
                .body(courier)
                .post("/api/v1/courier/login");
    }
    public void deleteCourier(Response response) {
        int id = response.jsonPath().getInt("id");
        given()
                .delete("/api/v1/courier/{id}", id);
    }
}