package steps;

import io.cucumber.java.DataTableType;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import service.*;
import config.TestConfig;
import impl.BaseTest;
import static context.RunContext.RUN_CONTEXT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiSteps extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(ApiSteps.class);
    private final SchemaMapping schemaMapping = new SchemaMapping();
    TestConfig testConfig = new TestConfig();
    HttpClient httpClient = new HttpClient();

     /**
     * Преобразует заданную запись в объект RequestParam.
     *
     * @param  entry  карта, представляющая запись, которую нужно преобразовать
     * @return        преобразованный объект RequestParam
     */
    @DataTableType
    public RequestParam requestParamEntry(Map<String, String> entry) {
        return new RequestParam(
                RequestParamType.valueOf(entry.get("type").toUpperCase()),
                entry.get("name"),
                entry.get("value"));
    }

    /**
     * Отправляет HTTP-запрос с указанным методом, URL, заголовками и параметрами из таблицы.
     * Полученный ответ сохраняется в указанной переменной.
     *
     * @param  method         метод HTTP (GET, POST, PUT или DELETE)
     * @param  url            URL для отправки запроса
     * @param  variableName   имя переменной, в которой нужно сохранить ответ
     * @param  paramsTable    таблица с заголовками и параметрами для запроса
     */
    @И("^выполнен (GET|POST|PUT|DELETE) запрос на URL \"([^\"]*)\" с headers и parameters из таблицы. Полученный ответ сохранен в переменную \"([^\"]*)\"$")
    public void sendHttpRequestSaveResponse(String method, String url, String variableName, List<RequestParam> paramsTable) {
        String productId = RUN_CONTEXT.get("productId", String.class);
        String address = testConfig.getURL() + url;
        if (productId != null) {
            address += productId;
        }
        log.info("Отправка {} запроса на URL: {}", method, address);
        Response response = httpClient.sendRequest(method, address, paramsTable);
        RUN_CONTEXT.put(variableName, response);
        // Удаление productId
        RUN_CONTEXT.deleteKey("productId");
    }

    /**
     * Проверяет, что код статуса ответа равен ожидаемому коду.
     *
     * @param  expectedStatusCode  ожидаемый код статуса для проверки
     */
    @И("ответ содержит статус код {int}")
    public void response_status_code_is(int expectedStatusCode) {
        Response response = RUN_CONTEXT.get("response", Response.class);
        assertEquals(expectedStatusCode, response.statusCode());
    }

    /**
     * Проверяет, что ответ соответствует ожидаемой схеме.
     *
     * @param  schemaFilePath    путь к файлу схемы
     */
    @Тогда("проверяем, что ответ {string} соответствует ожидаемой схеме")
    public void CheckingThatResponseMatchesExpectedPattern(String schemaFilePath) {
        Response response = RUN_CONTEXT.get("response", Response.class);
        JsonSchemaValidatorHelper.validateResponseAgainstSchema(response, schemaFilePath);
    }

    @Дано("продукт с идентификатором")
    public void ProductId(List<String> arg) {
        String productId = arg.get(0);
        RUN_CONTEXT.put("productId", productId);

    }
}
