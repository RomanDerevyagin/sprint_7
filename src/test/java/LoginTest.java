import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Login;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.education-services.ru";
    }

    @Test
    @DisplayName("Проверка на успешный логин курьера")
    void loginCourier() {
        Response response = sendPostLogin();
        compareRequest(response);
    }

    @Step("Отправка POST-запроса на логин курьера")
    public Response sendPostLogin() {
        Login courier = new Login("lol", "12345");
        return given()
                .header("Content-Type", "application/json")
                .body(courier)
                .post("/api/v1/courier/login");
    }

    @Step("Проверка: статус 200 и id не пустое")
    public void compareRequest(Response response) {
        response.then()
                .body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Проверка авторизации с несуществующим курьером")
    void loginCourierWithWrongLogin() {
        Response response = sendPostLoginWithWrongLogin();
        compareRequestWithWrongLogin(response);
    }

    @Step("Отправка POST-запроса несуществующим логином")
    public Response sendPostLoginWithWrongLogin() {
        Login courier = new Login("kek", "12345");
        return given()
                .header("Content-Type", "application/json")
                .body(courier)
                .post("/api/v1/courier/login");
    }

    @Step("Проверка: статус 404")
    public void compareRequestWithWrongLogin(Response response) {
        response.then()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
    @Test
    @DisplayName("Проверка на авторизацию без пароля")
    void loginCourierWithoutLogin() {
        Response response = sendPostLoginWithoutLogin();
        compareRequestWithoutLogin(response);
    }

    @Step("Отправка POST-запроса без логина")
    public Response sendPostLoginWithoutLogin() {
        Login courier = new Login("12345");
        return given()
                .header("Content-Type", "application/json")
                .body(courier)
                .post("/api/v1/courier/login");
    }

    @Step("Проверка: статус 400")
    public void compareRequestWithoutLogin(Response response) {
        response.then()
                .body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }
}