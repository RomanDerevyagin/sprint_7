import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.example.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.education-services.ru";
    }

    @ParameterizedTest
    @MethodSource("order")
    @DisplayName("Успешное создание заказа")
    void createOrder(Order order) {
        Response response = sendPostOrder(order);
        compareOrder(response);
    }

    @Step("Отправка POST-запроса на создание заказ")
    public Response sendPostOrder(Order order) {
        return given()
                .header("Content-Type", "application/json")
                .body(order)
                .post("/api/v1/orders");
    }

    @Step("Проверка: статус 201 и track не пустой")
    public void compareOrder(Response response) {
        response.then()
                .body("track", notNullValue())
                .and()
                .statusCode(201);
    }
    static Stream<Arguments> order() {
        return Stream.of(
                Arguments.of(new Order("Тест", "Тестиков", "Москва, Ленина 1", "4",
                        "+7 999 123 45 67", 22, "2026-06-06", "тестовый коммент",
                        new String[]{"BLACK","GREY"})),
                Arguments.of(new Order("Лок", "Кек", "Москва, Ленина 2", "4",
                        "+7 999 123 45 68", 10, "2026-06-07", "тестовый коммент",
                        new String[]{"BLACK"})),
                Arguments.of(new Order("Чебурек", "Тестиков", "Москва, Ленина 3", "4",
                        "+7 999 123 45 69", 5, "2026-06-08", "тестовый коммент",
                        new String[]{}))
        );
    }
}


