import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class GetOrderTest {
    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.education-services.ru";
    }
    @Test
    @DisplayName("Получение списка заказов")
    void getOrder() {
        Response response = sendGetOrder();
        compareRequest(response);
    }

    @Step("Отправка GET-запроса на получение заказов")
    public Response sendGetOrder() {
        return given()
                .header("Content-Type", "application/json")
                .get("/api/v1/orders");
    }

    @Step("Проверка: статус 200")
    public void compareRequest(Response response) {
        response.then()
                .statusCode(200);
    }
}
