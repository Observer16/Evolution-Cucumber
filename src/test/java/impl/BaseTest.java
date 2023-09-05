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

    TestConfig testConfig = new TestConfig();

    public void sendRequest(String url, String variableName, Method method) {
        String requestUrl = testConfig.getURL() + url;
        RequestSpecification request = given();
/*                .header("accept", "application/json")
                .header("Content-Type", "application/json");
*/

        try {
            authToken = TokenManager.readTokenFromFile();
        } catch (Exception e) {
            e.printStackTrace();
            // Обработайте исключение более конкретным образом, если необходимо
            authToken = ""; // Установите значение по умолчанию
        }

        // Проверяем authToken на null перед добавлением заголовка
        if (authToken != null) {
            request.header("X-Auth-Token", authToken);
        } else {
            // Если authToken равен null, возможно, нужно выполнить какие-то другие действия,
            // например, выбросить исключение или выполнить логгирование
            log.error("authToken is null. Taking appropriate action...");
        }

        Response response = switch (method) {
            case GET -> request.get(requestUrl);
            case POST -> request.post(requestUrl);
            case PUT -> request.put(requestUrl);
            default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        };
        ValidatableResponse validatableResponse = response.then();
        RUN_CONTEXT.put(variableName, validatableResponse);
    }

/*
    public void sendRequestWithBody(String url, String body, String variableName, Method method) {
        String requestUrl = testConfig.getURL() + url;
        RequestSpecification request = given()
                .header("Content-Type", "application/json")
                .body(body);
        Response response = sendRequest(request, method, requestUrl);
        ValidatableResponse validatableResponse = response.then();
        RUN_CONTEXT.put(variableName, validatableResponse);
    }

    private Response sendRequest(RequestSpecification request, Method method, String requestUrl) {
        return switch (method) {
            case GET -> request.get(requestUrl);
            case POST -> request.post(requestUrl);
            case PUT -> request.put(requestUrl);
            case PATCH -> request.patch(requestUrl);
            // Add other methods as needed
            default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        };
    }*/
}
