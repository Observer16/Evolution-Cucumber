package impl;

import config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;
import service.TokenManager;

import static context.RunContext.RUN_CONTEXT;
import static io.restassured.RestAssured.given;

@Log4j2
public class BaseTest {

    private String authToken;

    public void beforeMethod() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public void setup() {
        try {
            authToken = TokenManager.readTokenFromFile();
        } catch (Exception e) {
            e.printStackTrace();
            // Обработайте исключение более конкретным образом, если необходимо
            authToken = ""; // Установите значение по умолчанию
        }
    }
}
