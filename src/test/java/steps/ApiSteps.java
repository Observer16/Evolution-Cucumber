package steps;

import io.cucumber.java.DataTableType;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.io.File;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import service.*;
import config.TestConfig;
import impl.BaseTest;
import static context.RunContext.RUN_CONTEXT;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiSteps extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(ApiSteps.class);
    private final SchemaMapping schemaMapping = new SchemaMapping();
    TestConfig testConfig = new TestConfig();
    HttpClient httpClient = new HttpClient();

    @DataTableType
    public RequestParam requestParamEntry(Map<String, String> entry) {
        return new RequestParam(
                RequestParamType.valueOf(entry.get("type").toUpperCase()),
                entry.get("name"),
                entry.get("value"));
    }
    @И("^выполнен (GET|POST|PUT|DELETE) запрос на URL \"([^\"]*)\" с headers и parameters из таблицы. Полученный ответ сохранен в переменную \"([^\"]*)\"$")
    public void sendHttpRequestSaveResponse(String method, String url, String variableName, List<RequestParam> paramsTable) {
        String address = testConfig.getURL() + url;
        log.info("Отправка {} запроса на URL: {}", method, address);
        Response response = httpClient.sendRequest(method, address, paramsTable);
        RUN_CONTEXT.put(variableName, response);

/*        String schemaFilePath = schemaMapping.getSchemaPath(url);

        if (schemaFilePath == null) {
            log.warn("Для URL {} не найдена соответствующая схема JSON. Не выполняем валидацию.", url);
            return;
        }

        File schemaFile = new File(schemaFilePath);

        if (!schemaFile.exists()) {
            log.warn("Файл схемы JSON '{}' не существует. Не выполняем валидацию.", schemaFilePath);
            return;
        }

        ValidatableResponse validatableResponse = response.then()
                .body(JsonSchemaValidator.matchesJsonSchema(schemaFile));

        if (!validatableResponse.extract().body().asString().isEmpty()) {
            log.error("Схема JSON не соответствует ожидаемой схеме:");
            log.error(validatableResponse.extract().body().asString());
        } else {
            log.info("Схема JSON соответствует ожидаемой схеме.");
        }*/
    }

    @И("ответ содержит статус код {int}")
    public void response_status_code_is(int expectedStatusCode) {
        Response response = RUN_CONTEXT.get("response", Response.class);
        assertEquals(expectedStatusCode, response.statusCode());
    }

    @Тогда("проверяем, что ответ {string} соответствует ожидаемой схеме")
    public void CheckingThatResponseMatchesExpectedPattern(String schemaFilePath) {
        Response response = RUN_CONTEXT.get("response", Response.class);
        JsonSchemaValidatorHelper.validateResponseAgainstSchema(response, schemaFilePath);
    }
}
